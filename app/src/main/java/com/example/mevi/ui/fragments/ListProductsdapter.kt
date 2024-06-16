package com.example.mevi.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mevi.R
import com.example.mevi.core.Utilities
import com.example.mevi.core.ViewHolderGeneral
import com.example.mevi.databinding.ItemListProductsBinding
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.mevi.fakestore.ui.fragments.data.ProductsResponse

class ListProductsdapter
    (
    private val item: List<ProductsResponse>,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    companion object {
        val productsFav = ArrayList<ProductsResponse>()
    }

    interface OnClickListener {
        fun onClick(item: ProductsResponse, position: Int, cardviewlista: MaterialCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemListProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemholder = ViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener {
            val position =
                itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onClick(item[position], position, itemBinding.root)
        }
        return itemholder
    }

    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
        animation.duration = 500
        holder.itemView.startAnimation(animation)
        when (holder) {
            is ViewHolder -> holder.bind(item[position])
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = item.size
    private inner class ViewHolder(val binding: ItemListProductsBinding, val context: Context) :
        ViewHolderGeneral<ProductsResponse>(binding.root) {
        override fun bind(item: ProductsResponse) {
            binding.titleProduct.text = item.title
            binding.price.text = "$ ${item.price}"
            var isFilled = false
            Glide.with(context)
                .load(item.image?.replace("http://", "https://"))
                .placeholder(R.drawable.ic_generico)
                .error(R.drawable.ic_generico)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageProduct)

            binding.iconButton.setOnClickListener {

                if (isFilled) {
                    binding.iconButton.setIconResource(R.drawable.ic_favorite_border)

                    val gson = Gson()
                    val arrayListJson = gson.toJson(item)
                    val sharedPreferences = context.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.putString("miArrayListKey", arrayListJson)
                    editor.apply()
                } else {
                    binding.iconButton.setIconResource(R.drawable.ic_favorite_)
                    val sharedPreferences =
                        context.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()

                    productsFav.add(item)
                    val librosSeleccionados: ArrayList<ProductsResponse> = productsFav
                    val librosSeleccionadosJson = Utilities().convertirAJson(librosSeleccionados)
                    editor.putString("productos_seleccionados", librosSeleccionadosJson)
                    editor.apply()
                }
                isFilled = !isFilled
            }
        }
    }
}