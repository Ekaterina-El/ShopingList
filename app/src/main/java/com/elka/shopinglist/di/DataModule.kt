package com.elka.shopinglist.di

import android.app.Application
import android.content.Context
import com.elka.shopinglist.data.AppDatabase
import com.elka.shopinglist.data.ShopListDao
import com.elka.shopinglist.data.ShopListRepositoryImpl
import com.elka.shopinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
  @Binds
  @ApplicationScope
  fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

  companion object {
    @Provides
    @ApplicationScope
    fun provideShopListDao(application: Application): ShopListDao {
      return AppDatabase.getInstance(application).shopListDao()
    }
  }
}