package com.example.week_6.data

data class CartItem(
    val id: String,
    val title: String,
    val tag: String,
    val specs: String,
    val cardMessage: String? = null,
    val unitPrice: Double,
    var quantity: Int = 1,
    val imageResId: Int
) {
    val totalPrice: Double
        get() = unitPrice * quantity
}
