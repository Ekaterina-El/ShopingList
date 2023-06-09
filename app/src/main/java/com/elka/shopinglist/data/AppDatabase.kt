package com.elka.shopinglist.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  abstract fun shopListDao(): ShopListDao

  companion object {
    private var INSTANCE: AppDatabase? = null
    private val LOCK = Any()
    private const val DB_NAME = "shopingList"

    fun getInstance(application: Application): AppDatabase {
      INSTANCE?.let { return it }

      // double check
      synchronized(LOCK) {
        INSTANCE?.let { return it }

        val db = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
        INSTANCE = db
        return db
      }
    }
  }
}