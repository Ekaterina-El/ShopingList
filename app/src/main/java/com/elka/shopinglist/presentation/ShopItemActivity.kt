package com.elka.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elka.shopinglist.R
import com.elka.shopinglist.databinding.ActivityShopItemBinding
import com.elka.shopinglist.domain.ShopItem
import com.elka.shopinglist.presentation.ShopItemFragment.Companion.SCREEN_MODE
import com.elka.shopinglist.presentation.ShopItemFragment.Companion.SHOP_ITEM_ID

class ShopItemActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShopItemBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShopItemBinding.inflate(layoutInflater)

    binding.apply {
      lifecycleOwner = this@ShopItemActivity
    }
    setContentView(binding.root)
    if (savedInstanceState == null) parseIntent()
  }

  private fun parseIntent() {
    val mode = intent.getStringExtra(SCREEN_MODE)!!
    val id = intent.getIntExtra(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)

    val fragment = when (intent.getStringExtra(SCREEN_MODE)) {
      ADD_MODE -> ShopItemFragment.newInstanceAddItem()
      EDIT_MODE -> ShopItemFragment.newInstanceEditItem(id)
      else -> throw java.lang.Exception("Unknown mode")
    }

    supportFragmentManager.beginTransaction()
      .replace(R.id.shop_item_container, fragment)
      .commit()
  }

  companion object {
    const val EDIT_MODE = "edit_item"
    const val ADD_MODE = "add_item"

    fun newIntentAddItem(context: Context): Intent {
      val intent = Intent(context, ShopItemActivity::class.java)
      intent.putExtra(SCREEN_MODE, ADD_MODE)
      return intent
    }

    fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
      val intent = Intent(context, ShopItemActivity::class.java)
      intent.putExtra(SCREEN_MODE, EDIT_MODE)
      intent.putExtra(SHOP_ITEM_ID, shopItemId)
      return intent
    }
  }
}
