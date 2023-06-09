package com.elka.shopinglist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elka.shopinglist.databinding.FragmentShopItemBinding
import com.elka.shopinglist.domain.ShopItem

class ShopItemFragment(
  private val screenMode: String = ADD_MODE,
  private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {
  private lateinit var binding: FragmentShopItemBinding
  private val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }


  private val shouldCloseActivityObserver = Observer<Boolean> {
    if (!it) return@Observer
    activity?.onBackPressed()
  }

  private val errorNameObserver = Observer<Boolean> {
    binding.tilName.error = if (it) "Обязательное поле" else ""
  }

  private val errorCountObserver = Observer<Boolean> {
    binding.tilCount.error = if (it) "Обязательное поле" else ""
  }


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentShopItemBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.apply {
      lifecycleOwner = this@ShopItemFragment
      viewModel = shopItemViewModel
      master = this@ShopItemFragment
    }

    if (shopItemId != ShopItem.UNDEFINED_ID) shopItemViewModel.getShopItem(shopItemId)
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
    private const val EDIT_MODE = "edit_item"
    private const val ADD_MODE = "add_item"

    fun newInstanceAddItem() = ShopItemFragment(ADD_MODE)
    fun newInstanceEditItem(id: Int) = ShopItemFragment(EDIT_MODE, id)
  }
}