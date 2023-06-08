package com.elka.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShopItemBinding
  private val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }

  private val shouldCloseActivityObserver = Observer<Boolean> {
    if (!it) return@Observer
    finish()
  }

  private val errorNameObserver = Observer<Boolean> {
    binding.tilName.error = if (it) "Обязательное поле" else ""
  }

  private val errorCountObserver = Observer<Boolean> {
    binding.tilCount.error = if (it) "Обязательное поле" else ""
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShopItemBinding.inflate(layoutInflater)

    binding.apply {
      lifecycleOwner = this@ShopItemActivity
      viewModel = shopItemViewModel
      master = this@ShopItemActivity
    }
    setContentView(binding.root)

    when (intent.getStringExtra(EXTRA_SCREEN_MODE)) {
      ADD_MODE -> Unit
      EDIT_MODE -> {
        val id = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, 0)
        shopItemViewModel.getShopItem(id)
      }
    }

    shopItemViewModel.shouldCloseActivity.observe(this, shouldCloseActivityObserver)
    shopItemViewModel.errorInputName.observe(this, errorNameObserver)
    shopItemViewModel.errorInputCount.observe(this, errorCountObserver)
  }

  fun save() {
    shopItemViewModel.saveShopItem()
  }

  companion object {
    const val EXTRA_SCREEN_MODE = "extra_screen_mode"
    const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
    const val EDIT_MODE = "edit_item"
    const val ADD_MODE = "add_item"

    fun newIntentAddItem(context: Context): Intent {
      val intent = Intent(context, ShopItemActivity::class.java)
      intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
      return intent
    }

    fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
      val intent = Intent(context, ShopItemActivity::class.java)
      intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
      intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
      return intent
    }
  }
}
