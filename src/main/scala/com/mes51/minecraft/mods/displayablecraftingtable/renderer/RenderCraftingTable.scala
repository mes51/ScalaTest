package com.mes51.minecraft.mods.displayablecraftingtable.renderer

import com.mes51.minecraft.mods.displayablecraftingtable.tileentity.TileEntityCraftingTable
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.entity.item.EntityItem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{GlStateManager, OpenGlHelper}
import net.minecraft.client.renderer.entity.Render
import net.minecraft.item.{Item, ItemStack}
import org.lwjgl.opengl.GL11

import scala.util.Random

class RenderCraftingTable extends TileEntitySpecialRenderer[TileEntityCraftingTable] {
  private val loopTime = 4000

  private val dummyItem = new EntityItem(null)
  private val itemRenderer: Render[EntityItem] = Minecraft.getMinecraft.getRenderManager.getEntityClassRenderObject(classOf[EntityItem])

  override def renderTileEntityAt(te: TileEntityCraftingTable, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit = {
      te.inventory.grouped(3)
        .map(n => n.zipWithIndex)
        .zipWithIndex
        .foreach({
          case (xs, row) => xs.foreach({
            case (Some(i), col) =>
              renderItem(
                i,
                x + 0.5 + -0.185 + 0.185 * col,
                y + 1.075,
                z + 0.5 + -0.185 + 0.185 * row,
                0.4
              )
            case _ =>
          })
        })

      te.craftingItem.foreach(renderItem(_, x + 0.5, y + 1.2, z + 0.5, 1.0))
  }

  private def randomCurrentTime(seed: Int) = (System.currentTimeMillis() + new Random(seed).nextInt(loopTime)) % loopTime

  private def renderItem(item: ItemStack, x: Double, y: Double, z: Double, scale: Double) = {
    val time = randomCurrentTime(Item.getIdFromItem(item.getItem))
    GlStateManager.pushMatrix()
    GlStateManager.translate(x, y, z)
    GlStateManager.translate(0.0, Math.sin(Math.PI / loopTime * 2.0 * time) * 0.025, 0.0)
    GlStateManager.scale(scale, scale, scale)
    GlStateManager.rotate(time * (360.0F / loopTime), 0.0F, 1.0F, 0.0F)

    dummyItem.setEntityItemStack(item)
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0.0F, 240.0F)
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F)
    itemRenderer.doRender(dummyItem, 0.0, 0.0, 0.0, 0.0F, 0.0F)

    GL11.glPopMatrix()
  }
}

object RenderCraftingTable {
  var renderId = -1
}