package com.mes51.minecraft.mods.displayablecraftingtable.gui

import net.minecraft.client.gui.inventory.GuiContainer
import com.mes51.minecraft.mods.displayablecraftingtable.container.ContainerCraftingTable
import net.minecraft.util.ResourceLocation
import net.minecraft.client.resources.I18n
import org.lwjgl.opengl.GL11

class GuiCraftingTable(container: ContainerCraftingTable) extends GuiContainer(container) {
  val texture = new ResourceLocation("textures/gui/container/crafting_table.png")

  override def drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int): Unit = {
    fontRendererObj.drawString(I18n.format("container.crafting", Array[Object]()), 28, 6, 4210752)
    fontRendererObj.drawString(I18n.format("container.inventory", Array[Object]()), 8, this.ySize - 96 + 2, 4210752)
  }

  override def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int): Unit = {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    mc.getTextureManager.bindTexture(texture)
    val centerX = (this.width - this.xSize) / 2
    val centerY = (this.height - this.ySize) / 2
    drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize)
  }
}
