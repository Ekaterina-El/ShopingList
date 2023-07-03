package com.elka.shopinglist.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
  @Query("SELECT * FROM shop_items")
  fun getShopList(): LiveData<List<ShopItemDbModel>>

  @Query("SELECT * FROM shop_items")
  fun  getShopListCursor(): Cursor

  @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
  suspend fun getShopItemById(shopItemId: Int): ShopItemDbModel

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addShopItem(items: ShopItemDbModel)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addShopItemSync(items: ShopItemDbModel)

  @Query("DELETE FROM shop_items WHERE id=:shopItemId")
  suspend fun deleteShopItem(shopItemId: Int)

  @Query("DELETE FROM shop_items WHERE id=:shopItemId")
  fun deleteShopItemSync(shopItemId: Int)
}