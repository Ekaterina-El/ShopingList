package com.elka.shopinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {
  suspend fun editShopList(item: ShopItem) {
    repository.editShopItem(item)
  }
}