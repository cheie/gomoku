package com.chrnie.gomoku

sealed class CoordMapping {

  abstract fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray)

  protected fun ensureOutArraySize(out: IntArray) {
    if (out.size < 2) {
      throw RuntimeException("out array size must >= 2")
    }
  }

  object Rotate0 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      ensureOutArraySize(out)

      out[0] = centerX
      out[1] = centerY

      out[0] += (dX + 0)
      out[1] += (0 + dY)
    }

  }

  object Rotate45 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      ensureOutArraySize(out)

      out[0] = centerX
      out[1] = centerY

      out[0] += (dX - dY)
      out[1] += (dX + dY)
    }

  }

  object Rotate90 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      ensureOutArraySize(out)

      out[0] = centerX
      out[1] = centerY

      out[0] += (-dY)
      out[1] += (dX)
    }

  }

  object Rotate135 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      ensureOutArraySize(out)

      out[0] = centerX
      out[1] = centerY

      out[0] += (-dX - dY)
      out[1] += (dX - dY)
    }

  }

  object Rotate180 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      return Rotate0.map(centerX, centerY, -dX, -dY, out)
    }

  }

  object Rotate225 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      return Rotate45.map(centerX, centerY, -dX, -dY, out)
    }

  }

  object Rotate270 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      return Rotate90.map(centerX, centerY, -dX, -dY, out)
    }

  }

  object Rotate315 : CoordMapping() {

    override fun map(centerX: Int, centerY: Int, dX: Int, dY: Int, out: IntArray) {
      return Rotate135.map(centerX, centerY, -dX, -dY, out)
    }

  }
}