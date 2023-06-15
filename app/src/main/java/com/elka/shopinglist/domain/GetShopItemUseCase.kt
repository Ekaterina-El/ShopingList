package com.elka.shopinglist.domain

class GetShopItemUseCase(private val repository: ShopListRepository) {
  suspend fun getShopItem(id: Int): ShopItem {
    return repository.getShopItem(id)
  }
}