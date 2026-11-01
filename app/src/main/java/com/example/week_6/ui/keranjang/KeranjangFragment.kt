package com.example.week_6.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week_6.data.CartRepository
import com.example.week_6.databinding.FragmentKeranjangBinding
import java.text.NumberFormat
import java.util.Locale

class KeranjangFragment : Fragment() {

    private var _binding: FragmentKeranjangBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(
            items = mutableListOf(),
            onQuantityChanged = { item, delta ->
                CartRepository.updateQuantity(item, delta)
            },
            onDeleteItem = { item ->
                CartRepository.removeItem(item)
                Toast.makeText(
                    requireContext(),
                    "${item.title} dihapus dari keranjang",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter

        CartRepository.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateData(items)
            updatePaymentSummary()
        }

        binding.btnCheckout.setOnClickListener {
            val total = binding.tvFinalTotal.text.toString()
            Toast.makeText(
                requireContext(),
                "Pesanan Berhasil Dibuat! Total: $total",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updatePaymentSummary() {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val subtotal = CartRepository.getSubtotal()
        val deliveryFee = if (subtotal > 0) 25000.0 else 0.0
        val discount = if (subtotal > 0) 50000.0 else 0.0
        val finalTotal = (subtotal + deliveryFee - discount).coerceAtLeast(0.0)

        binding.tvSubtotal.text = formatRupiah.format(subtotal)
        binding.tvDeliveryFee.text = formatRupiah.format(deliveryFee)
        binding.tvDiscount.text = "-${formatRupiah.format(discount)}"
        binding.tvFinalTotal.text = formatRupiah.format(finalTotal)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
