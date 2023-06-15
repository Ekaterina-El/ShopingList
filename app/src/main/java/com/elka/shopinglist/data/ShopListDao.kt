package com.elka.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
  @Query("SELECT * FROM shop_items")
  fun getShopList(): LiveData<List<ShopItemDbModel>>

  @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
  fun getShopItemById(shopItemId: Int): ShopItemDbModel

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addShopItem(items: ShopItemDbModel)

  @Query("DELETE FROM shop_items WHERE id=:shopItemId")
  fun deleteShopItem(shopItemId: Int)
}