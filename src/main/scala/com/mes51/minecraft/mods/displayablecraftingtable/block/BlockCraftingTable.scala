package com.mes51.minecraft.mods.displayablecraftingtable.block

import com.mes51.minecraft.mods.displayablecraftingtable.{DisplayableCraftingTable, DisplayableCraftingTableProp}
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import com.mes51.minecraft.mods.displayablecraftingtable.inventory.Inventory
import com.mes51.minecraft.mods.displayablecraftingtable.proxy.GuiIds
import com.mes51.minecraft.mods.displayablecraftingtable.util.InstanceOf._
import com.mes51.minecraft.mods.displayablecraftingtable.util.ItemStackImplicits.ItemStackExtends
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.{EnumBlockRenderType, EnumFacing, EnumHand, ResourceLocation}
import net.minecraft.util.math.BlockPos

object BlockCraftingTable extends BlockContainer(Material.WOOD) {
  setCreativeTab(CreativeTabs.DECORATIONS)
  setRegistryName(new ResourceLocation(DisplayableCraftingTableProp.MODID, "craftingTable"))
  setUnlocalizedName(DisplayableCraftingTableProp.MODID + ".craftingTable")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileEntityCraftingTable

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, heldItem: EnumFacing, side: Float, hitX: Float, hitY: Float): Boolean = {
    if (!worldIn.isRemote) {
      playerIn.openGui(DisplayableCraftingTable, GuiIds.CRAFTING_TABLE, worldIn, pos.getX, pos.getY, pos.getZ)
    }
    true
  }

  override def breakBlock(worldIn: World, pos: BlockPos, state: IBlockState): Unit = {
    val inventory = Option(worldIn.getTileEntity(pos)).filter(_.isInstanceOf[TileEntityCraftingTable]).map(_.as[TileEntityCraftingTable].inventory).getOrElse(Inventory(0))
    inventory.filter(_.isDefined).map(_.get).foreach(i => dropItem(worldIn, i, pos))
    super.breakBlock(worldIn, pos, state)
  }

  override def getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL

  override def isBlockNormalCube(state: IBlockState): Boolean = false

  private def dropItem(world: World, item: ItemStack, posIn: BlockPos) = {
    val pos = posIn.add(
      world.rand.nextDouble() * 0.8 - 0.4,
      world.rand.nextDouble() * 0.8 - 0.1,
      world.rand.nextDouble() * 0.8 - 0.4
    )

    val count = world.rand.nextInt(Math.max(item.func_190916_E / 10, 1)) + 1
    val stackSize = item.func_190916_E / count
    0.to(count).foldLeft(item)({
      case (i, _) =>
        val (drop, memo) = i.split(stackSize)
        val entity = new EntityItem(world, pos.getX, pos.getY, pos.getZ, drop)
        entity.motionX = world.rand.nextDouble() * 0.2 - 0.1
        entity.motionY = world.rand.nextDouble() * 0.05 + 0.2
        entity.motionZ = world.rand.nextDouble() * 0.2 - 0.1
        world.spawnEntityInWorld(entity)
        memo
    })
  }
}