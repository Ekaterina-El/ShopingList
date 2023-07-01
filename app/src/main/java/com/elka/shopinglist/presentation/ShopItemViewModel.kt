package com.elka.shopinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elka.shopinglist.domain.AddShopItemUseCase
import com.elka.shopinglist.domain.EditShopItemUseCase
import com.elka.shopinglist.domain.GetShopItemUseCase
import com.elka.shopinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
  private val getShopItemUseCase: GetShopItemUseCase,
  private val addShopItemUseCase: AddShopItemUseCase,
  private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {
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

  private var currentShopItem: ShopItem? = null

  fun getShopItem(shopItemId: Int) {
    viewModelScope.launch {
      val item = getShopItemUseCase.getShopItem(shopItemId)
      setItem(item)
    }
  }


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
    viewModelScope.launch {
      addShopItemUseCase.addShopItem(ShopItem(name, count, true))
    }
  }

  private fun editShopItem(name: String, count: Int) {
    viewModelScope.launch {
      val shopItem = currentShopItem!!.copy(name = name, count = count)
      editShopItemUseCase.editShopList(shopItem)
    }
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