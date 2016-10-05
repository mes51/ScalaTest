package com.mes51.minecraft.mods.displayablecraftingtable.util

import scala.reflect.ClassTag

trait InstanceOf[A] {
  val obj: A

  def as[B : Manifest]: B = obj.asInstanceOf[B]

  def asOpt[B : Manifest]: Option[B] = obj match {
    case x: B =>
      Some(x)
    case _ =>
      None
  }

  def is[B : ClassTag]: Boolean = implicitly[ClassTag[B]].runtimeClass.isInstance(obj)
}

object InstanceOf {
  def apply[A](a: A): InstanceOf[A] = new InstanceOf[A] {
    override val obj: A = a
  }

  implicit def any2instanceof[A](a: A): InstanceOf[A] = apply(a)
}

