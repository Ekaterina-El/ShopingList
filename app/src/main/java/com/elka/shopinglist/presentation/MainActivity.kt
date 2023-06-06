package com.elka.shopinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ActivityMainBinding
import com.elka.shopinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var viewModel: MainViewModel
  private val shopListAdapter: ShopListAdapter by lazy { ShopListAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    viewModel.shopList.observe(this) {
      showShopItems(it)
    }

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    with(binding.rvShopList) {
      adapter = this@MainActivity.shopListAdapter
      setupLongClickListener()
      setupClickListener()
      setupSwipeListener()
      setupMaxPool()
    }

  }

  private fun RecyclerView.setupMaxPool() {
    recycledViewPool.setMaxRecycledViews(
      ShopListAdapter.DISABLED_ITEM, ShopListAdapter.MAX_POOL_SIZE
    )
    recycledViewPool.setMaxRecycledViews(
      ShopListAdapter.ENABLED_ITEM, ShopListAdapter.MAX_POOL_SIZE
    )
  }

  private fun RecyclerView.setupSwipeListener() {
    ItemTouchHelper(object :
      ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = shopListAdapter.getItem(position)
        viewModel.deleteShopItem(item)
      }

    }).attachToRecyclerView(this)
  }

  private fun setupClickListener() {
    this@MainActivity.shopListAdapter.onShopItemClickListener = {
      Toast.makeText(this@MainActivity, "Edit ${it.name}", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setupLongClickListener() {
    this@MainActivity.shopListAdapter.onShopItemLongClickListener = {
      viewModel.changeEnableStatus(it)
    }
  }

  private fun showShopItems(items: List<ShopItem>) {
    shopListAdapter.updateItems(items)
  }
}