package com.elka.shopinglist.domain

interface ShopListRepository {
  fun getShopList(): List<ShopItem>
  fun getShopItem(id: Int): ShopItem
  fun editShopItem(item: ShopItem)
  fun deleteShopItem(item: ShopItem)
  fun addShopItem(item: ShopItem)
}