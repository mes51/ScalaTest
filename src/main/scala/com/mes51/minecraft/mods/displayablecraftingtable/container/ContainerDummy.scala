package com.mes51.minecraft.mods.displayablecraftingtable.container

import net.minecraft.inventory.Container
import net.minecraft.entity.player.EntityPlayer

class ContainerDummy extends Container {
  override def canInteractWith(playerIn: EntityPlayer): Boolean = false
}
