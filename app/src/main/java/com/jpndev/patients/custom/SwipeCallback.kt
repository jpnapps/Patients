package com.jpndev.patients.custom;

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.patients.R

abstract class SwipeCallback(private val context: Context) :
    ItemTouchHelper.Callback() {
    private val deleteIcon =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_forever_24)

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe(viewHolder.bindingAdapterPosition, direction)
        /* when (direction) {
             ItemTouchHelper.LEFT -> onSwipe(viewHolder.absoluteAdapterPosition,direction)
             ItemTouchHelper.RIGHT -> onSwipeRight(viewHolder.bindingAdapterPosition)
         }*/
    }

    abstract fun onSwipe(position: Int, direction: Int)
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20 //so background is behind the rounded corners of itemView
        // Draw the background color
        val backgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(R.color.md_red_100)
        } else {
            ContextCompat.getColor(context, R.color.md_red_100)
        }
        Log.d("logs", " onChildDraw dX: " + dX)
        when {
            dX > 0 -> {
                Log.d("logs", " onChildDraw 111")
                // Swiping to the right
                val backgroundLeft = itemView.left
                val backgroundRight = itemView.left + dX.toInt() + backgroundCornerOffset
                val backgroundTop = itemView.top
                val backgroundBottom = itemView.bottom
                val background = ColorDrawable(Color.RED)
                background.setBounds(
                    backgroundLeft,
                    backgroundTop,
                    backgroundRight,
                    backgroundBottom
                )
                background.draw(c)
            }
            dX < 0 -> {
                Log.d("logs", " onChildDraw 111")
                // Swiping to the right
                val backgroundLeft = itemView.right + dX.toInt() - backgroundCornerOffset
                val backgroundRight = itemView.right
                val backgroundTop = itemView.top
                val backgroundBottom = itemView.bottom
                val background = ColorDrawable(Color.RED)
                background.setBounds(
                    backgroundLeft,
                    backgroundTop,
                    backgroundRight,
                    backgroundBottom
                )
                background.draw(c)
            }
            else -> {
                // view is unSwiped
                Log.d("logs", " onChildDraw 222")
                val background = ColorDrawable(Color.WHITE)
                background.setBounds(0, 0, 0, 0)
                background.draw(c)
            }
        }
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(
            left,
            top,
            right,
            bottom,
            Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) })
    }

    /*?      dX < 0 -> { // Swiping to the left
        val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
        val iconRight = itemView.right - iconMargin
        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        val backgroundLeft = itemView.right + dX.toInt() - backgroundCornerOffset
        val backgroundRight = itemView.right
        val backgroundTop = itemView.top
        val backgroundBottom = itemView.bottom
        val background = ColorDrawable(backgroundColor)
        background.setBounds(
            backgroundLeft,
            backgroundTop,
            backgroundRight,
            backgroundBottom
        )
        background.draw(c)
        deleteIcon.draw(c)
    }*/

    /*  val itemView = viewHolder.itemView
  val itemHeight = itemView.bottom - itemView.top
  val isCanceled = dX == 0f && !isCurrentlyActive

  if (isCanceled) {
      clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
      return
  }
  // Draw the background color
  val backgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      context.getColor(R.color.md_red_100)
  } else {
      ContextCompat.getColor(context, R.color.md_red_100)
  }

  val backgroundRect = RectF(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
  c.drawRect(backgroundRect,  Paint().apply { color = backgroundColor })

  // Draw the delete layout
  val deleteLayout = (viewHolder as ItemViewHolder).layoutDelete
  val deleteWidth = deleteLayout.width
  val deleteHeight = deleteLayout.height
  val deleteLeft = itemView.right + dX.toInt() - deleteWidth
  val deleteRight = itemView.right + dX.toInt()
  val deleteTop = itemView.top
  val deleteBottom = itemView.bottom
  deleteLayout.layout(deleteLeft, deleteTop, deleteRight, deleteBottom)
  deleteLayout.draw(c)

  super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,*/
}



