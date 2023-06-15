package com.elka.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elka.shopinglist.data.ShopListRepositoryImpl
import com.elka.shopinglist.domain.AddShopItemUseCase
import com.elka.shopinglist.domain.EditShopItemUseCase
import com.elka.shopinglist.domain.GetShopItemUseCase
import com.elka.shopinglist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
  private val rep = ShopListRepositoryImpl(application)

  private val getShopItemUseCase = GetShopItemUseCase(rep)
  private val addShopItemUseCase = AddShopItemUseCase(rep)
  private val editShopItemUseCase = EditShopItemUseCase(rep)

  val name = MutableLiveData<String>()
  val count = MutableLiveData<String>()

  private val _errorInputName = MutableLiveData<Boolean>()
  val errorInputName: LiveData<Boolean>
    get() = _errorInputName

  private val _errorInputCount = MutableLiveData<Boolean>()
  val errorInputCount: LiveData<Boolean>
    get() = _errorInputCount

  private val _shouldCloseActivity = MutableLiveData<Boolean>(false)
  val shouldCloseActivity: LiveData<Boolean>
    get() = _shouldCloseActivity

  fun getShopItem(shopItemId: Int) {
    val item = getShopItemUseCase.getShopItem(shopItemId)
    setItem(item)
  }

  private var currentShopItem: ShopItem? = null

  private fun setItem(item: ShopItem?) {
    currentShopItem = item
    name.value = item?.name ?: ""
    count.value = item?.count.toString()
  }

  fun saveShopItem() {
    val n = parseName(name.value)
    val c = parseCount(count.value)
    val fieldValid = validateInput(n, c)
    if (!fieldValid) return

    if (currentShopItem != null) editShopItem(n, c) else addShopItem(n, c)
    finishWork()
  }

  private fun addShopItem(name: String, count: Int) {
    addShopItemUseCase.addShopItem(ShopItem(name, count, true))
  }

  private fun editShopItem(name: String, count: Int) {
    val shopItem = currentShopItem!!.copy(name = name, count = count)
    editShopItemUseCase.editShopList(shopItem)
  }

  private fun finishWork() {
    setItem(null)
    _shouldCloseActivity.value = true
  }

  private fun parseName(inputName: String?): String {
    return inputName?.trim() ?: ""
  }

  private fun parseCount(inputCount: String?): Int {
    return try {
      inputCount?.toInt() ?: 0
    } catch (_: java.lang.Exception) {
      0
    }
  }

  private fun validateInput(name: String, count: Int): Boolean {
    _errorInputName.value = false
    _errorInputCount.value = false

    var result = 0
    if (name.isBlank()) {
      _errorInputName.value = true
      result += 1
    }
    if (count <= 0) {
      _errorInputCount.value = true
      result += 1
    }
    return result == 0
  }
}