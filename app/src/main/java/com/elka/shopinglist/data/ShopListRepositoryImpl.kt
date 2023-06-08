package com.elka.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elka.shopinglist.domain.ShopItem
import com.elka.shopinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
  private val shopListLD = MutableLiveData<List<ShopItem>>(listOf())
  private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id)})
  private var autoIncrementId = 0

  init {
    for(i in 0 until 3) {
      val item = ShopItem("Name #$i", i, Random.nextBoolean())
      addShopItem(item)
    }
  }

  private fun updateList() {
    shopListLD.value = shopList.toList()
  }

  override fun getShopList(): LiveData<List<ShopItem>> {
    return shopListLD
  }

  override fun getShopItem(id: Int): ShopItem {
    return shopList.firstOrNull { it.id == id }
      ?: throw java.lang.RuntimeException("Element with id $id not found")
  }

  override fun editShopItem(item: ShopItem) {
    val old = getShopItem(item.id)
    shopList.remove(old)
    addShopItem(item)
  }

  override fun deleteShopItem(item: ShopItem) {
    shopList.remove(item)
    updateList()
  }

  override fun addShopItem(item: ShopItem) {
    if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId++

    shopList.add(item)
    updateList()
  }
}