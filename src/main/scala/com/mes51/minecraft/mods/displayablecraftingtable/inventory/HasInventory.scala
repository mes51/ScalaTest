package com.mes51.minecraft.mods.displayablecraftingtable.inventory

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import com.mes51.minecraft.mods.displayablecraftingtable.util.ItemStackImplicits._
import net.minecraft.entity.player.EntityPlayer

trait HasInventory extends IInventory {
  val inventoryName: String

  var inventory: Inventory

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = true

  override def closeInventory(player: EntityPlayer): Unit = { }

  override def openInventory(player: EntityPlayer): Unit = { }

  override def getInventoryStackLimit: Int = 64

  override def hasCustomName: Boolean = false

  override def getName: String = inventoryName

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
    inventory = inventory.updated(index, Option(stack).map(_.clampStackSize(getInventoryStackLimit())))
    markDirty()
  }

  override def removeStackFromSlot(index: Int): ItemStack = {
    val item = inventory(index)
    inventory = inventory.updated(index, None)
    item.getOrElse(ItemStack.field_190927_a)
  }

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val splitResult = inventory(index).map(_.split(count)).map(x => (Some(x._1), Some(x._2))).getOrElse((None, None))
    inventory = inventory.updated(index, splitResult._2)
    markDirty()
    splitResult._1.getOrElse(ItemStack.field_190927_a)
  }

  override def getStackInSlot(index: Int): ItemStack = inventory(index).getOrElse(ItemStack.field_190927_a)

  override def getSizeInventory: Int = inventory.size

  override def clear(): Unit = {
    inventory = Inventory(inventory.size)
  }

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def setField(id: Int, value: Int): Unit = { }

  override def func_191420_l: Boolean = inventory.forall(_.forall(_.func_190926_b))
}
