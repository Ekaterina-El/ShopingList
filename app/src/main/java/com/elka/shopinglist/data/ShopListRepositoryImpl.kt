package com.elka.shopinglist.data

import com.elka.shopinglist.domain.ShopItem
import com.elka.shopinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
  private val items = mutableListOf<ShopItem>()
  private var autoIncrementId = 0

  init {
    for(i in 0 until 10) {
      val item = ShopItem("Name #$i", i, true)
      addShopItem(item)
    }
  }

  override fun getShopList(): List<ShopItem> {
    return items.toList()
  }

  override fun getShopItem(id: Int): ShopItem {
    return items.firstOrNull { it.id == id }
      ?: throw java.lang.RuntimeException("Element with id $id not found")
  }

  override fun editShopItem(item: ShopItem) {
    val old = getShopItem(item.id)
    deleteShopItem(old)
    addShopItem(item)
  }

  override fun deleteShopItem(item: ShopItem) {
    items.remove(item)
  }

  override fun addShopItem(item: ShopItem) {
    if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId++
    items.add(item)
  }
}