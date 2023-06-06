package com.elka.shopinglist.presentation

import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ItemShopEnabledBinding
import com.elka.shopinglist.domain.ShopItem

class EnabledViewHolder(
  private val binding: ItemShopEnabledBinding,
  private val onShopItemClickListener: (ShopItem) -> Unit,
  private val onShopItemLongClickListener: (ShopItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
  fun bind(item: ShopItem) {
    binding.item = item

    binding.root.setOnClickListener {
      onShopItemClickListener(item)
    }

    binding.root.setOnLongClickListener {
      onShopItemLongClickListener(item)
      return@setOnLongClickListener true
    }
  }
}
