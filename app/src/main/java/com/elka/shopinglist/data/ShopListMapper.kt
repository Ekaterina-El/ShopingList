package com.elka.shopinglist.data

import com.elka.shopinglist.domain.ShopItem

class ShopListMapper {
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