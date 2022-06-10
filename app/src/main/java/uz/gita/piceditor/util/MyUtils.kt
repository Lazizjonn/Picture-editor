package uz.gita.piceditor.util

import android.content.res.Resources
import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.sqrt

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

private fun sqr(a: Float) = a * a
infix fun PointF.distance(other: PointF): Float = sqrt(sqr(x - other.x) + sqr(y - other.y))

