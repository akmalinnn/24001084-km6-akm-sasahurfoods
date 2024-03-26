package com.akmalin.sasahurfoods.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.base.ViewHolderBinder
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.databinding.ItemMenuGridBinding
import com.akmalin.sasahurfoods.presentation.home.adapter.OnItemClickedListener
import com.akmalin.sasahurfoods.utils.toIndonesianFormat

class MenuGridItemViewHolder (
    private val binding: ItemMenuGridBinding,
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