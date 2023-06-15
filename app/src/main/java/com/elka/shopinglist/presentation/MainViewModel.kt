package com.elka.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elka.shopinglist.data.ShopListRepositoryImpl
import com.elka.shopinglist.domain.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = ShopListRepositoryImpl(application)
  private val getShopListUseCase = GetShopListUseCase(repository)
  private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)

  val shopList = getShopListUseCase.getShopList()

  fun deleteShopItem(item: ShopItem) {
    deleteShopItemUseCase.deleteShopItem(item)
  }


  fun changeEnableStatus(item: ShopItem) {
    val newItem = item.copy(enabled = !item.enabled)
    editShopItemUseCase.editShopList(newItem)
  }
}