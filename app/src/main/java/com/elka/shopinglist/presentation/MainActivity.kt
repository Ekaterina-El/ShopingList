package com.elka.shopinglist.presentation

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elka.shopinglist.databinding.ActivityMainBinding
import com.elka.shopinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.Companion.OnEditingFinishedListener {
  private lateinit var binding: ActivityMainBinding

  private val component by lazy {
    (application as ShopListApplication).component.activityComponentFactory().create()
  }

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private val viewModel by lazy {
    ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
  }

  private val shopListAdapter: ShopListAdapter by lazy { ShopListAdapter() }
  private var shopItemContainer: FragmentContainerView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    component.inject(this)
    addListeners()
    setupRecyclerView()

    thread {
      val data: Cursor? = contentResolver.query(
        Uri.parse("content://com.elka.shopinglist/shop_items"), null, null, null, null, null
      )

      if (data != null) {
        Log.d("MainActivity", data.count.toString())

        if (data.moveToFirst()) {
          do {
            val id = data.getInt(data.getColumnIndexOrThrow("id"))
            val name = data.getString(data.getColumnIndexOrThrow("name"))
            val count = data.getInt(data.getColumnIndexOrThrow("count"))
            val enabled = data.getInt(data.getColumnIndexOrThrow("enabled")) > 0
            val shopItem = ShopItem(name, count, enabled, id = id)
            Log.d("MainActivityData", "$shopItem")
          } while (data.moveToNext())
        }

        data.close()
      }
    }
  }

  private fun addListeners() {
    viewModel.shopList.observe(this) {
      showShopItems(it)
    }

    binding.buttonAddShopItem.setOnClickListener {
      openAddShopItem()
    }
  }

  private fun openAddShopItem() {
    if (shopItemContainer == null) {
      val intent = ShopItemActivity.newIntentAddItem(this)
      startActivity(intent)
    } else {
      val fragment = ShopItemFragment.newInstanceAddItem()
      setFragmentToShopItemContainer(fragment)
    }
  }

  private fun openEditShopItem(id: Int) {
    if (shopItemContainer == null) {
      val intent = ShopItemActivity.newIntentEditItem(this, id)
      startActivity(intent)
    } else {
      val fragment = ShopItemFragment.newInstanceEditItem(id)
      setFragmentToShopItemContainer(fragment)
    }
  }

  private fun setFragmentToShopItemContainer(fragment: ShopItemFragment) {
    supportFragmentManager.popBackStack()

    supportFragmentManager.beginTransaction().replace(shopItemContainer!!.id, fragment)
      .addToBackStack(null).commit()
  }

  private fun setupRecyclerView() {
    shopItemContainer = binding.shopItemContainer
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
        val item = shopListAdapter.currentList[position]
        viewModel.deleteShopItem(item)
      }

    }).attachToRecyclerView(this)
  }

  private fun setupClickListener() {
    this@MainActivity.shopListAdapter.onShopItemClickListener = {
      openEditShopItem(it.id)
    }
  }

  private fun setupLongClickListener() {
    this@MainActivity.shopListAdapter.onShopItemLongClickListener = {
      viewModel.changeEnableStatus(it)
    }
  }

  private fun showShopItems(items: List<ShopItem>) {
    shopListAdapter.submitList(items)
  }

  override fun onEditingFinished() {
    supportFragmentManager.popBackStack()
  }
}