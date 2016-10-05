package com.mes51.minecraft.mods.displayablecraftingtable

import com.mes51.minecraft.mods.displayablecraftingtable.block.BlockCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.handler.GuiHandler
import com.mes51.minecraft.mods.displayablecraftingtable.proxy.IProxy
import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.init.Blocks

object DisplayableCraftingTableProp {
  final val MOD_NAME = "DisplayableCraftingTable"
  final val MODID = "displayablecraftingtable"
  final val VERSION = "1.0.1"
}

@Mod(name = DisplayableCraftingTableProp.MOD_NAME, modid = DisplayableCraftingTableProp.MODID, version = DisplayableCraftingTableProp.VERSION, modLanguage = "scala")
object DisplayableCraftingTable {
  @SidedProxy(
    clientSide = "com.mes51.minecraft.mods.displayablecraftingtable.proxy.ClientProxy",
    serverSide = "com.mes51.minecraft.mods.displayablecraftingtable.proxy.ServerProxy"
  )
  var proxy: IProxy = _

  @EventHandler
  def preInit(e: FMLPreInitializationEvent) = {
    val itemBlock = new ItemBlock(BlockCraftingTable).setRegistryName(BlockCraftingTable.getRegistryName)

    GameRegistry.register(BlockCraftingTable)
    GameRegistry.register(itemBlock)
    GameRegistry.registerTileEntity(classOf[TileEntityCraftingTable], classOf[TileEntityCraftingTable].getCanonicalName)
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler)
    GameRegistry.addShapelessRecipe(new ItemStack(BlockCraftingTable), new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Blocks.CRAFTING_TABLE))

    proxy.registerModel(itemBlock)
  }

  @EventHandler
  def init(e: FMLInitializationEvent) = {
    proxy.registerRenderer()
  }
}