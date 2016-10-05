package com.mes51.minecraft.mods.displayablecraftingtable.proxy

import com.mes51.minecraft.mods.displayablecraftingtable.container.ContainerCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.gui.GuiCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.renderer.RenderCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.util.InstanceOf._
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.client.registry.ClientRegistry

class ClientProxy extends IProxy {
  override def createClientGuiElement(id: Int, player: EntityPlayer, world: World, pos: BlockPos): AnyRef = {
    id match {
      case GuiIds.CRAFTING_TABLE => new GuiCraftingTable(new ContainerCraftingTable(player.inventory, world.getTileEntity(pos).as[TileEntityCraftingTable]))
    }
  }

  override def registerRenderer(): Unit = {
    val renderer = new RenderCraftingTable
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityCraftingTable], renderer)
  }

  override def registerModel(item: Item): Unit = {
    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName, "inventory"))
  }
}
