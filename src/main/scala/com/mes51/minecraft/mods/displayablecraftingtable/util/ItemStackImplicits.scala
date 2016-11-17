package com.mes51.minecraft.mods.displayablecraftingtable.util

import net.minecraft.item.ItemStack

object ItemStackImplicits {
  implicit class ItemStackExtends(item: ItemStack) {
    def clampStackSize(max: Int): ItemStack = setStackSize(Math.max(Math.min(item.func_190916_E, max), 0))

    def setStackSize(stackSize: Int): ItemStack = {
      val result = item.copy()
      result.func_190920_e(stackSize)
      result
    }

    /** *
      * split item stack
      * @param size
      * @return (split result, split remainder)
      */
    def split(size: Int): (ItemStack, ItemStack) = {
      val src = item.copy()
      val splitResult = src.splitStack(Math.min(size, src.func_190916_E))
      (splitResult, src)
    }
  }
}
