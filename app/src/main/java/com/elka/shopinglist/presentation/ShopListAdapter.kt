package com.elka.shopinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ItemShopDisabledBinding
import com.elka.shopinglist.databinding.ItemShopEnabledBinding
import com.elka.shopinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val items = mutableListOf<ShopItem>()

  inner class EnabledViewHolder(private val binding: ItemShopEnabledBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ShopItem) {
      binding.item = item
    }
  }

  inner class DisabledViewHolder(private val binding: ItemShopDisabledBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ShopItem) {
      binding.item = item
    }
  }

  private var i = 0
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    Log.d("Shop_list", i.toString())
    i++
    return when(viewType) {
      ENABLED_ITEM -> {
        val binding = ItemShopEnabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        EnabledViewHolder(binding)
      }

      DISABLED_ITEM -> {
        val binding = ItemShopDisabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        DisabledViewHolder(binding)
      }

      else -> throw RuntimeException("Unknown type of view")
    }
  }

  override fun getItemViewType(position: Int): Int {
    val item = items[position]
    return when(item.enabled) {
      true -> ENABLED_ITEM
      else -> DISABLED_ITEM
    }
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = items[position]
    when (holder) {
      is DisabledViewHolder -> holder.bind(item)
      is EnabledViewHolder -> holder.bind(item)
      else -> throw RuntimeException("Unknown view type")
    }
  }

  fun updateItems(newItems: List<ShopItem>) {
    items.clear()
    items.addAll(newItems)
    notifyDataSetChanged()
  }

  companion object {
    const val ENABLED_ITEM = 1
    const val DISABLED_ITEM = 2

    const val MAX_POOL_SIZE = 15
  }
}