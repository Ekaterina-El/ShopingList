package com.elka.shopinglist.di

import com.elka.shopinglist.presentation.MainActivity
import com.elka.shopinglist.presentation.ShopItemFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModel::class])
interface ActivityComponent {
  fun inject(mainActivity: MainActivity)
  fun inject(mainActivity: ShopItemFragment)

  @Subcomponent.Factory
  interface Factory {
    fun create(): ActivityComponent
  }
}