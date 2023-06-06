package com.elka.shopinglist.presentation

import android.os.Bundle
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
    with(binding.rvShopList) {
      adapter = this@MainActivity.adapter
      recycledViewPool.setMaxRecycledViews(
        ShopListAdapter.DISABLED_ITEM,
        ShopListAdapter.MAX_POOL_SIZE
      )
      recycledViewPool.setMaxRecycledViews(
          ShopListAdapter.ENABLED_ITEM,
          ShopListAdapter.MAX_POOL_SIZE
        )
    }
  }

  private fun showShopItems(items: List<ShopItem>) {
    adapter.updateItems(items)
  }
}