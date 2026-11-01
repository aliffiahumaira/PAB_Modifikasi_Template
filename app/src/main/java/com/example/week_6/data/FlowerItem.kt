package com.example.week_6.data

data class FlowerItem(
    val id: String,
    val title: String,
    val flowerNotes: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val imageResId: Int,
    var isFavorite: Boolean = false
)
