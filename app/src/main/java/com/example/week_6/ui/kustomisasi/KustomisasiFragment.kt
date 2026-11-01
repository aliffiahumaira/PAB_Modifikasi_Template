package com.example.week_6.ui.kustomisasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.week_6.R
import com.example.week_6.data.CartItem
import com.example.week_6.data.CartRepository
import com.example.week_6.data.CoreFlowerOption
import com.example.week_6.data.CustomBouquetState
import com.example.week_6.data.RibbonOption
import com.example.week_6.data.WrappingOption
import com.example.week_6.databinding.FragmentKustomisasiBinding
import java.text.NumberFormat
import java.util.Locale

class KustomisasiFragment : Fragment() {

    private var _binding: FragmentKustomisasiBinding? = null
    private val binding get() = _binding!!

    private val flowers = listOf(
        CoreFlowerOption("f1", "Peony Pastel", 285000.0, "Buket Peony Pastel Harmoni", R.drawable.ic_bouquet_peony),
        CoreFlowerOption("f2", "Mawar Merah", 260000.0, "Buket Mawar Merah Abadi", R.drawable.ic_bouquet_rose),
        CoreFlowerOption("f3", "Bunga Matahari", 240000.0, "Buket Cahaya Matahari", R.drawable.ic_bouquet_sunflower),
        CoreFlowerOption("f4", "Baby Breath", 210000.0, "Awan Murni Baby Breath", R.drawable.ic_bouquet_babybreath),
        CoreFlowerOption("f5", "Lily Casablanca", 320000.0, "Kemewahan Lily Casablanca", R.drawable.ic_bouquet_lily)
    )

    private val wrappings = listOf(
        WrappingOption("w1", "Kraft Earthy", 0.0, "#727972"),
        WrappingOption("w2", "Soft Blush Pink", 15000.0, "#FFAEBC"),
        WrappingOption("w3", "Dark Olive", 15000.0, "#5A7C65"),
        WrappingOption("w4", "Pure White Satin", 20000.0, "#FFFFFF")
    )

    private val ribbons = listOf(
        RibbonOption("r1", "Pita Sage Green", 0.0),
        RibbonOption("r2", "Pita Soft Pink Silk", 10000.0),
        RibbonOption("r3", "Pita Gold Metallic", 15000.0),
        RibbonOption("r4", "Pita Natural Jute", 5000.0)
    )

    private val state = CustomBouquetState(
        selectedFlower = flowers[0],
        selectedWrapping = wrappings[0],
        selectedRibbon = ribbons[0]
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKustomisasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi()

        binding.rgFlowers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPeony -> state.selectedFlower = flowers[0]
                R.id.rbRose -> state.selectedFlower = flowers[1]
                R.id.rbSunflower -> state.selectedFlower = flowers[2]
                R.id.rbBabyBreath -> state.selectedFlower = flowers[3]
                R.id.rbLily -> state.selectedFlower = flowers[4]
            }
            updateUi()
        }

        binding.rgWrapping.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbKraft -> state.selectedWrapping = wrappings[0]
                R.id.rbSoftBlush -> state.selectedWrapping = wrappings[1]
                R.id.rbDarkOlive -> state.selectedWrapping = wrappings[2]
                R.id.rbWhiteSatin -> state.selectedWrapping = wrappings[3]
            }
            updateUi()
        }

        binding.rgRibbon.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRibbonSage -> state.selectedRibbon = ribbons[0]
                R.id.rbRibbonPink -> state.selectedRibbon = ribbons[1]
                R.id.rbRibbonGold -> state.selectedRibbon = ribbons[2]
                R.id.rbRibbonJute -> state.selectedRibbon = ribbons[3]
            }
            updateUi()
        }

        binding.btnSaveDraft.setOnClickListener {
            Toast.makeText(requireContext(), "Draft kustomisasi buket berhasil disimpan!", Toast.LENGTH_SHORT).show()
        }

        binding.btnAddToCartCustom.setOnClickListener {
            val cardMsg = binding.etCardMessage.text.toString().ifEmpty { "Semoga Bahagia Always!" }
            val cartItem = CartItem(
                id = System.currentTimeMillis().toString(),
                title = "Kustom: ${state.selectedFlower.name}",
                tag = "Kustom",
                specs = "Kertas ${state.selectedWrapping.name} • ${state.selectedRibbon.name}",
                cardMessage = cardMsg,
                unitPrice = state.totalPrice,
                quantity = 1,
                imageResId = state.selectedFlower.imageResId
            )
            CartRepository.addItem(cartItem)
            Toast.makeText(
                requireContext(),
                "Buket kustom ${state.selectedFlower.name} berhasil ditambahkan ke keranjang!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUi() {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        binding.imgPreview.setImageResource(state.selectedFlower.imageResId)
        binding.tvPreviewTitle.text = state.selectedFlower.description
        binding.tvPreviewDescription.text =
            "Kombinasi ${state.selectedFlower.name} dengan wrapping ${state.selectedWrapping.name} dan ${state.selectedRibbon.name}."

        binding.badgeFlower.text = "🌸 ${state.selectedFlower.name}"
        binding.badgeWrap.text = state.selectedWrapping.name
        binding.badgeRibbon.text = state.selectedRibbon.name

        binding.tvTotalPrice.text = formatRupiah.format(state.totalPrice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
