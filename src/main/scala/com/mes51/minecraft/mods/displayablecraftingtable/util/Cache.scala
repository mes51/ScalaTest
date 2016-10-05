package com.mes51.minecraft.mods.displayablecraftingtable.util

import scala.collection.mutable

object Cache {
  def cacheLast[A, R](f: A => R): Function1[A, R] = {
    val cache = mutable.Map.empty[A, R]
    (a: A) => {
      if (!cache.contains(a)) {
        cache.clear()
        cache += ((a, f(a)))
      }
      cache(a)
    }
  }
}
