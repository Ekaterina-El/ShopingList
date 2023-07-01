package com.elka.shopinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {
  suspend fun getShopItem(id: Int): ShopItem {
    return repository.getShopItem(id)
  }
}