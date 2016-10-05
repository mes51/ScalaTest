package com.mes51.minecraft.mods.displayablecraftingtable.proxy

import com.mes51.minecraft.mods.displayablecraftingtable.container.ContainerCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.util.InstanceOf._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

trait IProxy {
  def createServerGuiElement(id: Int, player: EntityPlayer, world: World, pos: BlockPos): AnyRef = {
    id match {
      case GuiIds.CRAFTING_TABLE => new ContainerCraftingTable(player.inventory, world.getTileEntity(pos).as[TileEntityCraftingTable])
    }
  }

  def createClientGuiElement(id: Int, player: EntityPlayer, world: World, pos: BlockPos): AnyRef

  def registerRenderer(): Unit

  def registerModel(item: Item): Unit
}

object GuiIds {
  val CRAFTING_TABLE = 1
}