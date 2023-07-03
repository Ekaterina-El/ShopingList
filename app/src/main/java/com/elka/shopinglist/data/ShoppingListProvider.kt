package com.elka.shopinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ShoppingListProvider: ContentProvider() {
  private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI("com.elka.shopinglist", "shop_items/*", GET_SHOP_ITEM_QUERY_CODE)
  }

  override fun onCreate(): Boolean {
    return true  // запуск прошел успешно - true, иначе - false
  }

  override fun query(
    uri: Uri,
    p1: Array<out String>?,
    p2: String?,
    p3: Array<out String>?,
    p4: String?
  ): Cursor? {
    val code = uriMatcher.match(uri)
    when(code) {
      GET_SHOP_ITEM_QUERY_CODE -> {

      }
    }

    Log.d("ShoppingListProvider", "query: $uri $code")
    return null
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
    const val GET_SHOP_ITEM_QUERY_CODE = 10
  }
}