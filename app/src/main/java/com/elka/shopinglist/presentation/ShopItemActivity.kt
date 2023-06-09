package com.elka.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.R
import com.elka.shopinglist.databinding.ActivityShopItemBinding
import com.elka.shopinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShopItemBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShopItemBinding.inflate(layoutInflater)

    binding.apply {
      lifecycleOwner = this@ShopItemActivity
    }
    setContentView(binding.root)
    parseIntent()
  }

  private fun parseIntent() {
    val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)!!
    val id = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)

    val fragment = when (intent.getStringExtra(EXTRA_SCREEN_MODE)) {
      ADD_MODE -> ShopItemFragment.newInstanceAddItem()
      EDIT_MODE -> ShopItemFragment.newInstanceEditItem(id)
      else -> throw java.lang.Exception("Unknown mode")
    }

    supportFragmentManager.beginTransaction()
      .add(R.id.shop_item_container, fragment)
      .commit()
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
