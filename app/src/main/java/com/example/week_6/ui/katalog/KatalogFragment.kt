package com.example.week_6.ui.katalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week_6.R
import com.example.week_6.data.CartItem
import com.example.week_6.data.CartRepository
import com.example.week_6.data.FlowerItem
import com.example.week_6.databinding.FragmentKatalogBinding

class KatalogFragment : Fragment() {

    private var _binding: FragmentKatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleCatalog = listOf(
            FlowerItem(
                id = "1",
                title = "Pastel Romance Peony",
                flowerNotes = "Peoni & Mawar Blush",
                category = "Buket Mawar",
                price = 485000.0,
                rating = 4.9,
                imageResId = R.drawable.ic_bouquet_peony
            ),
            FlowerItem(
                id = "2",
                title = "Emerald White Lily",
                flowerNotes = "Lily Putih & Eucalyptus",
                category = "Bunga Meja",
                price = 520000.0,
                rating = 4.9,
                imageResId = R.drawable.ic_bouquet_lily
            ),
            FlowerItem(
                id = "3",
                title = "Sunny Sunflower Joy",
                flowerNotes = "Bunga Matahari & Chamomile",
                category = "Wisuda & Ultah",
                price = 395000.0,
                rating = 4.9,
                imageResId = R.drawable.ic_bouquet_sunflower
            ),
            FlowerItem(
                id = "4",
                title = "Velvet Crimson Rose",
                flowerNotes = "Mawar Merah Beludru",
                category = "Buket Mawar",
                price = 650000.0,
                rating = 4.9,
                imageResId = R.drawable.ic_bouquet_rose
            )
        )

        val adapter = CatalogAdapter(
            items = sampleCatalog,
            onAddToCartClick = { flower ->
                val cartItem = CartItem(
                    id = System.currentTimeMillis().toString(),
                    title = flower.title,
                    tag = "Signature",
                    specs = flower.flowerNotes,
                    cardMessage = "Terima Kasih!",
                    unitPrice = flower.price,
                    quantity = 1,
                    imageResId = flower.imageResId
                )
                CartRepository.addItem(cartItem)
                Toast.makeText(
                    requireContext(),
                    "${flower.title} ditambahkan ke keranjang!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onFavoriteClick = { flower ->
                val status = if (flower.isFavorite) "ditambahkan ke" else "dihapus dari"
                Toast.makeText(
                    requireContext(),
                    "${flower.title} $status favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.rvCatalog.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalog.adapter = adapter

        binding.btnClaimDiscount.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Kode diskon BLOOM20 berhasil diklaim!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnChatFlorist.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Menghubungkan dengan Artisan Florist Petale...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
