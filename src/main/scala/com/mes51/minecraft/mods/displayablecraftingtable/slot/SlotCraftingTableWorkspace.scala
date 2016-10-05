package com.mes51.minecraft.mods.displayablecraftingtable.slot

import net.minecraft.inventory.{Container, IInventory, Slot}
import net.minecraft.item.ItemStack

class SlotCraftingTableWorkspace(eventHandler: Container, _inventory: IInventory, index: Int, x: Int, y: Int) extends Slot(_inventory, index, x, y) {
  override def onSlotChanged(): Unit = {
    super.onSlotChanged()
    eventHandler.onCraftMatrixChanged(inventory)
  }

  override def decrStackSize(amount: Int): ItemStack = {
    val result = super.decrStackSize(amount)
    eventHandler.onCraftMatrixChanged(inventory)
    result
  }
}
