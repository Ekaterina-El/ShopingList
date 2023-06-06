package com.elka.shopinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.databinding.ActivityMainBinding
import com.elka.shopinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var viewModel: MainViewModel
  private val adapter: ShopListAdapter by lazy { ShopListAdapter() }

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
      adapter = this@MainActivity.adapter
      this@MainActivity.adapter.onShopItemLongClickListener = {
        viewModel.changeEnableStatus(it)
      }

      this@MainActivity.adapter.onShopItemClickListener = {
        Toast.makeText(this@MainActivity, "Edit ${it.name}", Toast.LENGTH_SHORT).show()
      }
      recycledViewPool.setMaxRecycledViews(
        ShopListAdapter.DISABLED_ITEM, ShopListAdapter.MAX_POOL_SIZE
      )
      recycledViewPool.setMaxRecycledViews(
        ShopListAdapter.ENABLED_ITEM, ShopListAdapter.MAX_POOL_SIZE
      )
    }
  }

  private fun showShopItems(items: List<ShopItem>) {
    adapter.updateItems(items)
  }
}