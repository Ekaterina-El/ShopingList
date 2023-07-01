package com.elka.shopinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elka.shopinglist.domain.DeleteShopItemUseCase
import com.elka.shopinglist.domain.EditShopItemUseCase
import com.elka.shopinglist.domain.GetShopListUseCase
import com.elka.shopinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val getShopListUseCase: GetShopListUseCase,
  private val deleteShopItemUseCase: DeleteShopItemUseCase,
  private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {
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