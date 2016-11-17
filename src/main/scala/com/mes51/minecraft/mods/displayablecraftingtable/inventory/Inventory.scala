package com.mes51.minecraft.mods.displayablecraftingtable.inventory

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}

import com.mes51.minecraft.mods.displayablecraftingtable.util.InstanceOf._

class Inventory(items: Seq[Option[ItemStack]]) extends TraversableOnce[Option[ItemStack]] with Iterable[Option[ItemStack]] {
  def apply(index: Int): Option[ItemStack] = items(index)

  def updated(index: Int, item: Option[ItemStack]): Inventory = new Inventory(items.updated(index, item.filter(_.func_190916_E > 0)))

  override def toTraversable: Traversable[Option[ItemStack]] = items.toTraversable

  override def copyToArray[B >: Option[ItemStack]](xs: Array[B], start: Int, len: Int): Unit = items.copyToArray(xs, start, len)

  override def find(p: (Option[ItemStack]) => Boolean): Option[Option[ItemStack]] = items.find(p)

  override def exists(p: (Option[ItemStack]) => Boolean): Boolean = items.exists(p)

  override def forall(p: (Option[ItemStack]) => Boolean): Boolean = items.forall(p)

  override def hasDefiniteSize: Boolean = items.hasDefiniteSize

  override def isEmpty: Boolean = items.isEmpty

  override def foreach[U](f: (Option[ItemStack]) => U): Unit = items.foreach(f)

  override def toIterator: Iterator[Option[ItemStack]] = items.toIterator

  override def toStream: Stream[Option[ItemStack]] = items.toStream

  override def iterator: Iterator[Option[ItemStack]] = items.iterator

  override def equals(obj: Any): Boolean = obj.asOpt[Inventory].exists(
    p => zip(p).forall({
      case (Some(a), Some(b)) => ItemStack.areItemStackTagsEqual(a, b)
      case (None, None) => true
      case _ => false
    })
  )
}

object Inventory {
  private val ITEM_DATA_TAG = "itemData"
  private val INVENTORY_SIZE_TAG = "inventorySize"
  private val EMPTY_ITEM = Some(new NBTTagCompound()).map { n => n.setString("emptyItem", "emptyItem"); n }.get

  def apply(size: Int): Inventory = new Inventory(List.fill(size)(None).toIndexedSeq)

  def writeToNBT(inventory: Inventory): NBTTagCompound = {
    val itemData = new NBTTagList()
    inventory.foreach(i => itemData.appendTag(i.map(_.writeToNBT(new NBTTagCompound())).getOrElse(EMPTY_ITEM)))

    val result = new NBTTagCompound()
    result.setTag(ITEM_DATA_TAG, itemData)
    result.setInteger(INVENTORY_SIZE_TAG, inventory.size)
    result
  }

  def readFromNBT(tagCompound: NBTTagCompound): Inventory = {
    val itemData = tagCompound.getTag(ITEM_DATA_TAG).as[NBTTagList]
    val size = tagCompound.getInteger(INVENTORY_SIZE_TAG)

    new Inventory((0 until size).map(itemData.getCompoundTagAt).map(n => if (n.equals(EMPTY_ITEM)) None else Some(n)).map(_.map(new ItemStack(_))))
  }
}