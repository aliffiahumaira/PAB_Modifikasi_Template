package com.example.week_6.data

data class CoreFlowerOption(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageResId: Int
)

data class WrappingOption(
    val id: String,
    val name: String,
    val extraPrice: Double,
    val colorHex: String
)

data class RibbonOption(
    val id: String,
    val name: String,
    val extraPrice: Double
)

data class CustomBouquetState(
    var selectedFlower: CoreFlowerOption,
    var selectedWrapping: WrappingOption,
    var selectedRibbon: RibbonOption,
    var greetingCardMessage: String = ""
) {
    val totalPrice: Double
        get() = selectedFlower.price + selectedWrapping.extraPrice + selectedRibbon.extraPrice
}
