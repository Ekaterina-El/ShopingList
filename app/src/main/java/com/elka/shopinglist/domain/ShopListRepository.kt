package com.elka.shopinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
  fun getShopList(): LiveData<List<ShopItem>>
  fun getShopItem(id: Int): ShopItem
  fun editShopItem(item: ShopItem)
  fun deleteShopItem(item: ShopItem)
  fun addShopItem(item: ShopItem)
}