package com.mes51.minecraft.mods.displayablecraftingtable.proxy
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ServerProxy extends IProxy {
  override def createClientGuiElement(id: Int, player: EntityPlayer, world: World, pos: BlockPos): AnyRef = null

  override def registerRenderer(): Unit = { }

  override def registerModel(item: Item): Unit = { }
}
