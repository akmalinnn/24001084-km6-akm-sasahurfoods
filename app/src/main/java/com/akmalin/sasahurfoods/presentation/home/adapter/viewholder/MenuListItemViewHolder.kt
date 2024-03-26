package com.akmalin.sasahurfoods.presentation.home.adapter.viewholder

import coil.load
import androidx.recyclerview.widget.RecyclerView
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.base.ViewHolderBinder
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.databinding.ItemMenuBinding
import com.akmalin.sasahurfoods.presentation.home.adapter.OnItemClickedListener
import com.akmalin.sasahurfoods.utils.toIndonesianFormat


class MenuListItemViewHolder(

    private val binding: ItemMenuBinding,
    private val listener: OnItemClickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(it.imgUrl) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = item.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}