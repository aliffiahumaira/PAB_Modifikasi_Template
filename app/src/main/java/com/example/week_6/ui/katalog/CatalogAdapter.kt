package com.example.week_6.ui.katalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week_6.data.FlowerItem
import com.example.week_6.databinding.ItemFlowerCardBinding
import java.text.NumberFormat
import java.util.Locale

class CatalogAdapter(
    private val items: List<FlowerItem>,
    private val onAddToCartClick: (FlowerItem) -> Unit,
    private val onFavoriteClick: (FlowerItem) -> Unit
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    class CatalogViewHolder(val binding: ItemFlowerCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = ItemFlowerCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = items[position]
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        holder.binding.apply {
            imgFlower.setImageResource(item.imageResId)
            tvFlowerNotes.text = item.flowerNotes
            tvFlowerTitle.text = item.title
            tvRating.text = "★ ${item.rating}"
            tvFlowerPrice.text = formatRupiah.format(item.price)

            btnAddToCart.setOnClickListener {
                onAddToCartClick(item)
            }
            btnFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                onFavoriteClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
