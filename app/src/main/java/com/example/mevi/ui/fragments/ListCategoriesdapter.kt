package com.example.mevi.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mevi.R
import com.example.mevi.core.ViewHolderGeneral
import com.example.mevi.databinding.ItemListCategoriesBinding
import com.google.android.material.card.MaterialCardView


class ListCategoriesdapter
    (
    private val item: List<String>,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    interface OnClickListener {
        fun onClick(item: String, position: Int, cardviewlista: MaterialCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemListCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    private inner class ViewHolder(val binding: ItemListCategoriesBinding, val context: Context) :
        ViewHolderGeneral<String>(binding.root) {
        override fun bind(item: String) {

            when(item){
                "electronics"->{
                    with(binding){
                        titleCategorie.text = context.getString(R.string.electronics)
                        imageCategorie.setImageResource(R.drawable.electr)
                    }
                }
                "jewelery"->{
                    with(binding){
                        titleCategorie.text = context.getString(R.string.jewelery)
                        imageCategorie.setImageResource(R.drawable.jewelery)
                    }

                }
                "men's clothing"->{
                    with(binding){
                        titleCategorie.text = context.getString(R.string.men_clothing)
                        imageCategorie.setImageResource(R.drawable.men_clothing)
                    }
                }
                "women's clothing"->{
                    with(binding){
                        titleCategorie.text = context.getString(R.string.woemn_clothing)
                        imageCategorie.setImageResource(R.drawable.women_clothe)
                    }
                }
            }
        }
    }
}