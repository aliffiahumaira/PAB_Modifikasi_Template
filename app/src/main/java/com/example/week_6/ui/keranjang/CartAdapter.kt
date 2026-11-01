package com.example.week_6.ui.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week_6.data.CartItem
import com.example.week_6.databinding.ItemCartProductBinding
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit,
    private val onDeleteItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        holder.binding.apply {
            imgCartItem.setImageResource(item.imageResId)
            tvTag.text = item.tag
            tvCartItemTitle.text = item.title
            tvCartItemSpecs.text = item.specs

            if (item.cardMessage.isNullOrEmpty()) {
                tvCartItemMessage.visibility = View.GONE
            } else {
                tvCartItemMessage.visibility = View.VISIBLE
                tvCartItemMessage.text = "Kartu: “${item.cardMessage}”"
            }

            tvCartItemPrice.text = formatRupiah.format(item.totalPrice)
            tvQty.text = item.quantity.toString()

            btnMinusQty.setOnClickListener {
                onQuantityChanged(item, -1)
            }
            btnPlusQty.setOnClickListener {
                onQuantityChanged(item, 1)
            }
            btnDeleteCartItem.setOnClickListener {
                onDeleteItem(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<CartItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
