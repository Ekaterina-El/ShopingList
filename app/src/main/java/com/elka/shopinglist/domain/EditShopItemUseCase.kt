package com.elka.shopinglist.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {
  suspend fun editShopList(item: ShopItem) {
    repository.editShopItem(item)
  }
}