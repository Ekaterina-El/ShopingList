package com.elka.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.elka.shopinglist.domain.ShopItem
import com.elka.shopinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
  private val shopListDao: ShopListDao, private val mapper: ShopListMapper
) : ShopListRepository {

  override fun getShopList(): LiveData<List<ShopItem>> {
    return shopListDao.getShopList().map { mapper.mapListDbModelsToEntity(it) }
  }

  override suspend fun getShopItem(id: Int): ShopItem {
    return mapper.mapDbModelToEntity(shopListDao.getShopItemById(id))
  }

  override suspend fun editShopItem(item: ShopItem) {
    addShopItem(item)
  }

  override suspend fun deleteShopItem(item: ShopItem) {
    shopListDao.deleteShopItem(item.id)
  }

  override suspend fun addShopItem(item: ShopItem) {
    shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
  }
}