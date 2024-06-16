package com.mevi.fakestore.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.example.mevi.core.ApiResponceStatus
import com.example.mevi.core.Constants
import com.example.mevi.core.Utilities
import com.example.mevi.databinding.FragmentProductsBinding
import com.example.mevi.ui.activity.MainInterface
import com.example.mevi.ui.fragments.ListProductsdapter
import com.google.android.material.card.MaterialCardView
import com.mevi.fakestore.ui.fragments.data.ProductsResponse
import com.example.mevi.ui.fragments.vm.CategoriesViewModel

class ProductsFragment : Fragment(), ListProductsdapter.OnClickListener {

    private lateinit var binding: FragmentProductsBinding
    private val vm: CategoriesViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var item: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let { bundle ->
            item = bundle.getString(Constants.ITEM, "")
            productsService()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        initView()
        addObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainInterface = null
    }

    private fun initView() {
        binding.title.text = item
    }

    private fun productsService() {
        vm.data.value = null
        println("item $item")
        vm.getProducts(item)
    }

    private fun addObservers() {
        vm.dataProducts.value = null
        statusObserve()
        vm.dataProducts.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                setDataKardex(response as ArrayList<ProductsResponse>)
            }

        }
    }

    private fun setDataKardex(listCategories: ArrayList<ProductsResponse>) {
        binding.listCategories.adapter = ListProductsdapter(listCategories, this)
    }

    private fun statusObserve() {
        vm.statusProducts.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        clearService()
                    }

                    is ApiResponceStatus.Error -> {
                        clearService()
                    }
                }
            }
        }
    }

    private fun clearService() {
        vm.statusProducts.removeObservers(viewLifecycleOwner)
        vm.statusProducts.value = null
        mainInterface?.showLoading(false)
    }

    override fun onClick(item: ProductsResponse, position: Int, cardviewlista: MaterialCardView) {
        Utilities().showBottomSheetDialog(
            item, requireActivity(),
            requireContext()
        )
    }
}