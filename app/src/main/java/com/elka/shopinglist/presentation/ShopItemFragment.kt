package com.elka.shopinglist.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.databinding.FragmentShopItemBinding
import com.elka.shopinglist.domain.ShopItem

class ShopItemFragment : Fragment() {
  private lateinit var binding: FragmentShopItemBinding
  private val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }

  private lateinit var onEditingFinishedListener: OnEditingFinishedListener

  private var screenMode: String = ADD_MODE
  private var shopItemId: Int = ShopItem.UNDEFINED_ID

  private val shouldCloseActivityObserver = Observer<Boolean> {
    if (!it) return@Observer
    onEditingFinishedListener.onEditingFinished()
  }

  private val errorNameObserver = Observer<Boolean> {
    binding.tilName.error = if (it) "Обязательное поле" else ""
  }

  private val errorCountObserver = Observer<Boolean> {
    binding.tilCount.error = if (it) "Обязательное поле" else ""
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnEditingFinishedListener) onEditingFinishedListener = context
    else throw RuntimeException("Activity is not implemented OnEditingFinishedListener")
  }


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentShopItemBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.apply {
      lifecycleOwner = this@ShopItemFragment
      viewModel = shopItemViewModel
      master = this@ShopItemFragment
    }

    parseArguments()
    if (shopItemId != ShopItem.UNDEFINED_ID) shopItemViewModel.getShopItem(shopItemId)
  }

  private fun parseArguments() {
    requireArguments().apply {
      screenMode = getString(SCREEN_MODE) ?: ADD_MODE
      shopItemId = getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
    }
  }

  override fun onResume() {
    super.onResume()
    shopItemViewModel.shouldCloseActivity.observe(viewLifecycleOwner, shouldCloseActivityObserver)
    shopItemViewModel.errorInputName.observe(viewLifecycleOwner, errorNameObserver)
    shopItemViewModel.errorInputCount.observe(viewLifecycleOwner, errorCountObserver)
  }

  override fun onPause() {
    super.onPause()
    shopItemViewModel.shouldCloseActivity.removeObserver(shouldCloseActivityObserver)
    shopItemViewModel.errorInputName.removeObserver(errorNameObserver)
    shopItemViewModel.errorInputCount.removeObserver(errorCountObserver)
  }

  fun save() {
    shopItemViewModel.saveShopItem()
  }

  companion object {
    const val SCREEN_MODE = "extra_screen_mode"
    const val SHOP_ITEM_ID = "extra_shop_item_id"
    private const val EDIT_MODE = "edit_item"
    private const val ADD_MODE = "add_item"

    interface OnEditingFinishedListener {
      fun onEditingFinished()
    }

    fun newInstanceAddItem(): ShopItemFragment {
      return ShopItemFragment().apply {
        arguments = Bundle().apply {
          putString(SCREEN_MODE, ADD_MODE)
        }
      }
    }

    fun newInstanceEditItem(id: Int): ShopItemFragment {
      return ShopItemFragment().apply {
        arguments = Bundle().apply {
          putString(SCREEN_MODE, EDIT_MODE)
          putInt(SHOP_ITEM_ID, id)
        }
      }
    }
  }
}