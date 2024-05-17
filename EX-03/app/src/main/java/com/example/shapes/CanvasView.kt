package com.example.shapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

        val paint:Paint=Paint()

        //line
        paint.setColor(Color.GREEN)
        paint.strokeWidth=8f
        canvas.drawLine(750f,800f,750f,1200f,paint)
        drawText(canvas, "Line", 690f, 750f)

        //circle
        paint.style=Paint.Style.FILL
        paint.setColor(Color.YELLOW)
        canvas.drawCircle(290f,350f,150f,paint)
        drawText(canvas, "Circle", 220f, 150f)

        //rectangle
        paint.style=Paint.Style.FILL
        paint.setColor(Color.RED)
        canvas.drawRect(850f,650f,600f,200f,paint)
        drawText(canvas, "Rectangle", 620f, 150f)


        //square
        paint.style=Paint.Style.FILL
        paint.setColor(Color.BLUE)
        canvas.drawRect(200f,1150f,500f,850f,paint)
        drawText(canvas, "Square", 250f, 750f)

    }

    private fun drawText(canvas: Canvas, s: String,x: Float, y: Float) {
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 50f
        }
        canvas.drawText(s, x, y, textPaint)
    }
}