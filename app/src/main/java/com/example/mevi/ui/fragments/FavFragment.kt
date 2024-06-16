package com.example.mevi.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.mevi.R
import com.example.mevi.core.Utilities
import com.example.mevi.databinding.FragmentFavBinding
import com.google.android.material.card.MaterialCardView
import com.example.mevi.ui.fragments.data.ProductsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(), ListProductsdapter.OnClickListener {

    private lateinit var binding: FragmentFavBinding
    private lateinit var listaProducts: ArrayList<ProductsResponse>

    companion object {
        lateinit var listArrayProductsFav: ArrayList<ProductsResponse>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater, container, false)
        loadSharedPreference()
        initButon()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
    }
    private fun initButon() {
        with(binding) {
            topAppBar.navigationIcon = null

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        val sharedPreferences =
                            requireContext().getSharedPreferences(
                                "productos_seleccionados",
                                Context.MODE_PRIVATE
                            )
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun loadSharedPreference() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val productosSeleccionadosJson = sharedPreferences.getString("productos_seleccionados", "")
        if (!productosSeleccionadosJson.isNullOrEmpty()) {
            val productosSeleccionados =
                productosSeleccionadosJson.let { Utilities().convertirDesdeJson(it) }
            val list = ArrayList<ProductsResponse>()

            listArrayProductsFav = list
            listaProducts = list
            setDataKardex(productosSeleccionados)

        }

    }

    private fun setDataKardex(listaUsuarios: ArrayList<ProductsResponse>) {
        binding.listProducts.adapter = ListProductsdapter(listaUsuarios, this)
    }

    override fun onClick(item: ProductsResponse, position: Int, cardviewlista: MaterialCardView) {
        Utilities().showBottomSheetDialog(
            item,
            requireActivity(),
            requireContext(),
        )
    }


}