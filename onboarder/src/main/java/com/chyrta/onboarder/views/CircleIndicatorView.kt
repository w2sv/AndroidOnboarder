package com.chyrta.onboarder.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.chyrta.onboarder.R
import kotlin.math.min

class CircleIndicatorView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private var activeIndicatorPaint: Paint? = null
    private var inactiveIndicatorPaint: Paint? = null
    private var radius = 0
    private var size = 0
    private var position = 0
    private var indicatorsCount = 0

    init {
        activeIndicatorPaint = Paint()
        activeIndicatorPaint!!.color = ContextCompat.getColor(context, R.color.active_indicator)
        activeIndicatorPaint!!.isAntiAlias = true
        inactiveIndicatorPaint = Paint()
        inactiveIndicatorPaint!!.color = ContextCompat.getColor(context, R.color.inactive_indicator)
        inactiveIndicatorPaint!!.isAntiAlias = true
        radius = resources.getDimensionPixelSize(R.dimen.indicator_size)
        size = radius * 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until indicatorsCount) {
            canvas.drawCircle(
                (radius + size * i).toFloat(),
                radius.toFloat(),
                radius.toFloat() / 2,
                inactiveIndicatorPaint!!
            )
        }
        canvas.drawCircle(
            (radius + size * position).toFloat(),
            radius.toFloat(),
            radius.toFloat() / 2,
            activeIndicatorPaint!!
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    fun setCurrentPage(position: Int) {
        this.position = position
        invalidate()
    }

    fun setPageIndicators(size: Int) {
        indicatorsCount = size
        invalidate()
    }

    private fun measureWidth(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            var result = size * indicatorsCount
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
            result
        }
    }

    private fun measureHeight(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            var result = 2 * radius + paddingTop + paddingBottom
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
            result
        }
    }

    fun setInactiveIndicatorColor(@ColorRes color: Int) {
        inactiveIndicatorPaint!!.color = ContextCompat.getColor(context!!, color)
        invalidate()
    }

    fun setActiveIndicatorColor(@ColorRes color: Int) {
        activeIndicatorPaint!!.color = ContextCompat.getColor(context!!, color)
        invalidate()
    }
}