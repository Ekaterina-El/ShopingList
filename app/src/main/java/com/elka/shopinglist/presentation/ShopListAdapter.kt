package com.elka.shopinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ItemShopDisabledBinding
import com.elka.shopinglist.databinding.ItemShopEnabledBinding
import com.elka.shopinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, RecyclerView.ViewHolder>(ShopItemDiffCallback()) {
  var onShopItemLongClickListener: (ShopItem) -> Unit = {}
  var onShopItemClickListener: (ShopItem) -> Unit = {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ENABLED_ITEM -> {
        val binding =
          ItemShopEnabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        EnabledViewHolder(binding, onShopItemClickListener, onShopItemLongClickListener)
      }

      DISABLED_ITEM -> {
        val binding =
          ItemShopDisabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        DisabledViewHolder(binding, onShopItemClickListener, onShopItemLongClickListener)
      }

      else -> throw RuntimeException("Unknown type of view")
    }
  }

  override fun getItemViewType(position: Int): Int {
    val item = getItem(position)
    return when (item.enabled) {
      true -> ENABLED_ITEM
      else -> DISABLED_ITEM
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = getItem(position)
    when (holder) {
      is DisabledViewHolder -> holder.bind(item)
      is EnabledViewHolder -> holder.bind(item)
      else -> throw RuntimeException("Unknown view type")
    }
  }

  companion object {
    const val ENABLED_ITEM = 1
    const val DISABLED_ITEM = 2

    const val MAX_POOL_SIZE = 15
  }
}