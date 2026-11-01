package com.example.week_6.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week_6.R

object CartRepository {
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(
        mutableListOf(
            CartItem(
                id = "1",
                title = "Pastel Romance Peony Bouquet",
                tag = "Signature",
                specs = "Kertas Soft Blush • Pita Champagne",
                cardMessage = "Happy Anniversary!",
                unitPrice = 350000.0,
                quantity = 1,
                imageResId = R.drawable.ic_bouquet_peony
            ),
            CartItem(
                id = "2",
                title = "Kustom: Sunburst Joy & Lily",
                tag = "Kustom",
                specs = "Kertas Kraft • Pita Sage Green",
                cardMessage = "Selamat & Sukses Selalu!",
                unitPrice = 280000.0,
                quantity = 1,
                imageResId = R.drawable.ic_bouquet_sunflower
            )
        )
    )
    val cartItems: LiveData<MutableList<CartItem>> = _cartItems

    fun addItem(item: CartItem) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.add(item)
        _cartItems.value = currentList
    }

    fun removeItem(item: CartItem) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.remove(item)
        _cartItems.value = currentList
    }

    fun updateQuantity(item: CartItem, delta: Int) {
        val currentList = _cartItems.value ?: return
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            val updatedItem = currentList[index]
            val newQty = updatedItem.quantity + delta
            if (newQty > 0) {
                updatedItem.quantity = newQty
                _cartItems.value = currentList
            }
        }
    }

    fun getSubtotal(): Double {
        return _cartItems.value?.sumOf { it.totalPrice } ?: 0.0
    }
}
