package com.elka.shopinglist.presentation

import android.app.Application
import com.elka.shopinglist.di.DaggerAppComponent

class ShopListApplication: Application() {
  val component by lazy {
    DaggerAppComponent.factory().create(this)
  }
}