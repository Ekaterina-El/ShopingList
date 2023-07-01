package com.elka.shopinglist.di

import androidx.lifecycle.ViewModel
import com.elka.shopinglist.presentation.MainViewModel
import com.elka.shopinglist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModel {
  @Binds
  @ViewModelKey(MainViewModel::class)
  @IntoMap
  fun mainViewModel(impl: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(ShopItemViewModel::class)
  fun shopItemViewModel(impl: ShopItemViewModel): ViewModel
}