package com.elka.shopinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elka.shopinglist.data.ShopListRepositoryImpl
import com.elka.shopinglist.domain.DeleteShopItemUseCase
import com.elka.shopinglist.domain.EditShopItemUseCase
import com.elka.shopinglist.domain.GetShopListUseCase
import com.elka.shopinglist.domain.ShopItem

class MainViewModel: ViewModel() {
  private val repository = ShopListRepositoryImpl
  private val getShopListUseCase = GetShopListUseCase(repository)
  private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)

  val shopList = MutableLiveData<List<ShopItem>>()
  fun getShopList() {
    shopList.value = getShopListUseCase.getShopList()
  }

  fun deleteShopItem(item: ShopItem) {
    deleteShopItemUseCase.deleteShopItem(item)
    getShopList()
  }


  fun changeEnableStatus(item: ShopItem) {
    val newItem = item.copy(enabled = !item.enabled)
    editShopItemUseCase.editShopList(newItem)
    getShopList()
  }
}