package com.elka.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elka.shopinglist.domain.ShopItem
import com.elka.shopinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
  private val items = MutableLiveData<List<ShopItem>>(listOf())
  private var autoIncrementId = 0

  init {
    for(i in 0 until 10) {
      val item = ShopItem("Name #$i", i, true)
      addShopItem(item)
    }
  }

  override fun getShopList(): LiveData<List<ShopItem>> {
    return items
  }

  override fun getShopItem(id: Int): ShopItem {
    return items.value!!.firstOrNull { it.id == id }
      ?: throw java.lang.RuntimeException("Element with id $id not found")
  }

  override fun editShopItem(item: ShopItem) {
    val old = getShopItem(item.id)
    deleteShopItem(old)
    addShopItem(item)
  }

  override fun deleteShopItem(item: ShopItem) {
    val showItems = items.value!!.toMutableList()
    showItems.remove(item)
    items.value = showItems
  }

  override fun addShopItem(item: ShopItem) {
    if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId++

    val showItems = items.value!!.toMutableList()
    showItems.add(item)
    items.value = showItems
  }
}