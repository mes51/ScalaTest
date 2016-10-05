package com.mes51.minecraft.mods.displayablecraftingtable.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, IInventory, InventoryCrafting}
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

object InventoryCraftingMatrix {
  val INVENTORY_MATRIX_SIZE = 3
}

class InventoryCraftingMatrix(eventHandler: Container, inventory: IInventory) extends InventoryCrafting(eventHandler, InventoryCraftingMatrix.INVENTORY_MATRIX_SIZE, InventoryCraftingMatrix.INVENTORY_MATRIX_SIZE) {
  override def decrStackSize(index: Int, count: Int): ItemStack = inventory.decrStackSize(index, count)

  override def closeInventory(player: EntityPlayer): Unit = inventory.closeInventory(player)

  override def getSizeInventory: Int = inventory.getSizeInventory

  override def getInventoryStackLimit: Int = inventory.getInventoryStackLimit

  override def clear(): Unit = inventory.clear()

  override def markDirty(): Unit = inventory.markDirty()

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = inventory.isItemValidForSlot(index, stack)

  override def openInventory(player: EntityPlayer): Unit = inventory.openInventory(player)

  override def getFieldCount: Int = inventory.getFieldCount

  override def getField(id: Int): Int = inventory.getField(id)

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = inventory.setInventorySlotContents(index, stack)

  override def isUseableByPlayer(player: EntityPlayer): Boolean = inventory.isUseableByPlayer(player)

  override def removeStackFromSlot(index: Int): ItemStack = inventory.removeStackFromSlot(index)

  override def getStackInSlot(index: Int): ItemStack = inventory.getStackInSlot(index)

  override def setField(id: Int, value: Int): Unit = inventory.setField(id, value)

  override def getDisplayName: ITextComponent = inventory.getDisplayName

  override def getName: String = inventory.getName

  override def hasCustomName: Boolean = inventory.hasCustomName
}
