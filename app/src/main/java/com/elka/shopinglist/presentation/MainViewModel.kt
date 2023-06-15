package com.elka.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elka.shopinglist.data.ShopListRepositoryImpl
import com.elka.shopinglist.domain.DeleteShopItemUseCase
import com.elka.shopinglist.domain.EditShopItemUseCase
import com.elka.shopinglist.domain.GetShopListUseCase
import com.elka.shopinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = ShopListRepositoryImpl(application)
  private val getShopListUseCase = GetShopListUseCase(repository)
  private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)

  val shopList = getShopListUseCase.getShopList()

  fun deleteShopItem(item: ShopItem) {
    viewModelScope.launch {
      deleteShopItemUseCase.deleteShopItem(item)
    }
  }

  fun changeEnableStatus(item: ShopItem) {
    viewModelScope.launch {
      val newItem = item.copy(enabled = !item.enabled)
      editShopItemUseCase.editShopList(newItem)
    }
  }
}