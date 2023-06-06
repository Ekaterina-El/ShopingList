package com.elka.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.R

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var i = 0
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    viewModel.shopList.observe(this) {
      Log.d("SHOP_LIST", it.toString())

      if (i == 3) return@observe
      viewModel.changeEnableStatus(it[0])
      i++
    }
  }
}