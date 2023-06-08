package com.elka.shopinglist.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShopItemBinding
  private val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShopItemBinding.inflate(layoutInflater)

    binding.apply {
      lifecycleOwner = this@ShopItemActivity
      viewModel = shopItemViewModel
    }
    setContentView(binding.root)

    binding.tilName.error = "Обязательное поле"
    binding.tilCount.error = "Обязательное поле"
  }


  fun save() {
    shopItemViewModel.addShopItem()
  }
}
