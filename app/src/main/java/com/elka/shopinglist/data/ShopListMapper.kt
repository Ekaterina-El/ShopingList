package com.elka.shopinglist.data

import com.elka.shopinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {
  fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
    id = shopItem.id,
    name = shopItem.name,
    count = shopItem.count,
    enabled = shopItem.enabled,
  )

  fun mapDbModelToEntity(shopItem: ShopItemDbModel) = ShopItem(
    id = shopItem.id,
    name = shopItem.name,
    count = shopItem.count,
    enabled = shopItem.enabled,
  )

  fun mapListDbModelsToEntity(list: List<ShopItemDbModel>) = list.map { mapDbModelToEntity(it) }
}