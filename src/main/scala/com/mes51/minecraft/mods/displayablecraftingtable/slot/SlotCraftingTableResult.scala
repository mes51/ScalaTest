package com.mes51.minecraft.mods.displayablecraftingtable.slot

import com.mes51.minecraft.mods.displayablecraftingtable.inventory.InventoryCraftingMatrix
import net.minecraft.inventory.{Container, IInventory, SlotCrafting}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class SlotCraftingTableResult(player: EntityPlayer, eventHandler: Container, _inventory: IInventory, craftingResult: IInventory, index: Int, x: Int, y: Int) extends SlotCrafting(player, new InventoryCraftingMatrix(eventHandler, _inventory), craftingResult, index, x, y) {
  override def func_190901_a(playerIn: EntityPlayer, stack: ItemStack): ItemStack = {
    super.func_190901_a(playerIn, stack)
    eventHandler.onCraftMatrixChanged(inventory)
    stack
  }

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
