package com.mes51.minecraft.mods.displayablecraftingtable.handler

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.mes51.minecraft.mods.displayablecraftingtable.DisplayableCraftingTable
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiHandler extends IGuiHandler {
  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef =
    DisplayableCraftingTable.proxy.createServerGuiElement(ID, player, world, new BlockPos(x, y, z))

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef =
    DisplayableCraftingTable.proxy.createClientGuiElement(ID, player, world, new BlockPos(x, y, z))
}
