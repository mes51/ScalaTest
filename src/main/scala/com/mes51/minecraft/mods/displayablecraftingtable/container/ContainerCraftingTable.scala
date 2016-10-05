package com.mes51.minecraft.mods.displayablecraftingtable.container

import net.minecraft.inventory._
import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.ItemStack
import com.mes51.minecraft.mods.displayablecraftingtable.slot.{SlotCraftingTableResult, SlotCraftingTableWorkspace}

class ContainerCraftingTable(playerInventory: InventoryPlayer, tileEntity: TileEntityCraftingTable) extends Container {
  val craftingResult = new InventoryCraftResult()

  initializeSlot()
  onCraftMatrixChanged(null)

  private def initializeSlot() = {
    addSlotToContainer(new SlotCraftingTableResult(playerInventory.player, this, tileEntity, craftingResult, 0, 124, 35))

    0.until(tileEntity.inventory.size).grouped(3).zipWithIndex.foreach({
      case (x, ri) => x.zipWithIndex.foreach {
        case (i, ci) => addSlotToContainer(new SlotCraftingTableWorkspace(this, tileEntity, i, 30 + ci * 18, 17 + ri * 18))
      }
    })

    9.until(36).grouped(9).zipWithIndex.foreach({
      case (x, ri) => x.zipWithIndex.foreach {
        case (i, ci) => addSlotToContainer(new Slot(playerInventory, i, 8 + ci * 18, 84 + ri * 18))
      }
    })

    0.until(9).foreach(i => addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142)))
  }

  private def transferStack(slot: Slot, slotIndex: Int, player: EntityPlayer): Option[ItemStack] = {
    val source = slot.getStack
    val itemStack = source.copy()

    slotIndex match {
      case 0 => if (!mergeItemStack(source, 10, 46, true)) return None else slot.onSlotChange(source, itemStack)
      case i if i >= 10 && i < 37 => if (!mergeItemStack(source, 1, 9, false)) return None
      case i if i >= 37 && i < 46 => if (!mergeItemStack(source, 10, 37, false)) return None
      case _ => if (!mergeItemStack(source, 10, 46, false)) return None
    }

    if (source.stackSize == 0) {
      slot.putStack(null)
    } else {
      slot.onSlotChanged()
    }

    if (source.stackSize == itemStack.stackSize) {
      None
    } else {
      slot.onPickupFromSlot(player, source)
      Some(itemStack)
    }
  }

  override def onCraftMatrixChanged(inventoryIn: IInventory): Unit = {
    val craftingMatrix = new InventoryCrafting(new ContainerDummy(), 3, 3)
    tileEntity.inventory.zipWithIndex.foreach(x => craftingMatrix.setInventorySlotContents(x._2, x._1.orNull))
    craftingResult.setInventorySlotContents(0, CraftingManager.getInstance.findMatchingRecipe(craftingMatrix, playerInventory.player.worldObj))
  }

  override def canInteractWith(playerIn: EntityPlayer): Boolean =
    playerIn.getDistanceSq(tileEntity.getPos.getX + 0.5, tileEntity.getPos.getY + 0.5, tileEntity.getPos.getZ + 0.5) <= 64.0

  override def transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack = {
    Option(inventorySlots.get(index))
      .filter(_.getHasStack)
      .flatMap(s => transferStack(s, index, playerIn))
      .orNull
  }

  override def canMergeSlot(stack: ItemStack, slotIn: Slot): Boolean =
    slotIn.inventory != craftingResult && super.canMergeSlot(stack, slotIn)
}
