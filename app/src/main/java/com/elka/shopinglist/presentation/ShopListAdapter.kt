package com.elka.shopinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ItemShopDisabledBinding
import com.elka.shopinglist.databinding.ItemShopEnabledBinding
import com.elka.shopinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val items = mutableListOf<ShopItem>()
  var onShopItemLongClickListener: (ShopItem) -> Unit = {}
  var onShopItemClickListener: (ShopItem) -> Unit = {}

  inner class EnabledViewHolder(
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

  inner class DisabledViewHolder(
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

  private var i = 0
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    Log.d("Shop_list", i.toString())
    i++
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
    val item = items[position]
    return when (item.enabled) {
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