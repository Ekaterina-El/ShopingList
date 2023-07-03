package com.elka.shopinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.elka.shopinglist.presentation.ShopListApplication
import javax.inject.Inject

class ShoppingListProvider: ContentProvider() {
  @Inject
  lateinit var shopListDao: ShopListDao
  private val component by lazy {
    (context as ShopListApplication).component
  }

  private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI("com.elka.shopinglist", "shop_items", GET_SHOP_ITEMS_QUERY_CODE)
  }

  override fun onCreate(): Boolean {
    component.inject(this)
    return true  // запуск прошел успешно - true, иначе - false
  }

  override fun query(
    uri: Uri,
    p1: Array<out String>?,
    p2: String?,
    p3: Array<out String>?,
    p4: String?
  ): Cursor? {
    return when(uriMatcher.match(uri)) {
      GET_SHOP_ITEMS_QUERY_CODE -> {
        shopListDao.getShopListCursor()
      }
      else -> null
    }
  }

  override fun getType(p0: Uri): String? {
    TODO("Not yet implemented")
  }

  override fun insert(p0: Uri, p1: ContentValues?): Uri? {
    TODO("Not yet implemented")
  }

  override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

  override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

  companion object {
    const val GET_SHOP_ITEMS_QUERY_CODE = 10
  }
}