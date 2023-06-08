package com.elka.shopinglist.presentation

import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ItemShopDisabledBinding
import com.elka.shopinglist.domain.ShopItem

class DisabledViewHolder(
  private val binding: ItemShopDisabledBinding,
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