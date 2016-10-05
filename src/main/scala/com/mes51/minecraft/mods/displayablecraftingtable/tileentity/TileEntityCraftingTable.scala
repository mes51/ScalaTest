package com.mes51.minecraft.mods.displayablecraftingtable.tileentity

import com.mes51.minecraft.mods.displayablecraftingtable.container.ContainerDummy
import net.minecraft.tileentity.TileEntity
import com.mes51.minecraft.mods.displayablecraftingtable.inventory.{Inventory, TileEntityInventory}
import net.minecraft.nbt.NBTTagCompound

import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity

import com.mes51.minecraft.mods.displayablecraftingtable.util.Cache

class TileEntityCraftingTable extends TileEntity with TileEntityInventory {
  val inventoryName = "CraftingTable"

  var inventory = Inventory(9)

  private val craftingItemStack: (Inventory => Option[ItemStack]) = Cache.cacheLast((inventory: Inventory) => {
    val craftingMatrix = new InventoryCrafting(new ContainerDummy(), 3, 3)
    inventory.zipWithIndex.foreach(x => craftingMatrix.setInventorySlotContents(x._2, x._1.orNull))
    Option(CraftingManager.getInstance.findMatchingRecipe(craftingMatrix, worldObj))
  })

  def craftingItem: Option[ItemStack] = craftingItemStack(inventory)

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    inventory = Inventory.readFromNBT(compound.getCompoundTag("inventory"))
  }

  override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
    compound.setTag("inventory", Inventory.writeToNBT(inventory))
    super.writeToNBT(compound)
  }

  override def getUpdateTag: NBTTagCompound = writeToNBT(new NBTTagCompound)

  override def getUpdatePacket: SPacketUpdateTileEntity = new SPacketUpdateTileEntity(this.getPos, -1, getUpdateTag)

  override def onDataPacket(net: NetworkManager, packet: SPacketUpdateTileEntity): Unit = {
    super.onDataPacket(net, packet)

    readFromNBT(packet.getNbtCompound)
  }
}
