package com.mes51.minecraft.mods.displayablecraftingtable.inventory

import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

trait TileEntityInventory extends HasInventory with ISidedInventory {
  self: TileEntity =>

  override def isUseableByPlayer(p_70300_1_ : EntityPlayer): Boolean =
    self.getWorld.getTileEntity(self.getPos) == this &&
    p_70300_1_.getDistanceSq(self.getPos.add(0.5, 0.5, 0.5)) <= 64.0

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = rangeInInventorySize(index)

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean =
    rangeInInventorySize(index) && inventory(index).forall(_.isItemEqual(itemStackIn))

  override def getSlotsForFace(side: EnumFacing): Array[Int] = Array.range(0, inventory.size)

  private def rangeInInventorySize(index: Int) = index >= 0 && index < inventory.size

  private def getDistanceSq(pos: BlockPos): Double = getDistanceSq(pos.getX, pos.getY, pos.getZ)
}
