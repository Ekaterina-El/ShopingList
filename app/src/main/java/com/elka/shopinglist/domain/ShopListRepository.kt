package com.elka.shopinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
  fun getShopList(): LiveData<List<ShopItem>>
  suspend fun getShopItem(id: Int): ShopItem
  suspend fun editShopItem(item: ShopItem)
  suspend fun deleteShopItem(item: ShopItem)
  suspend fun addShopItem(item: ShopItem)
}