package com.sokoldev.griefresort.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.max

class BounceLoadingView : View {
    private var mShadowColor = SHADOW_COLOR

    private var mDuration = DEFAULT_DURATION

    private var mBitmapPaint: Paint? = null
    private var mShadowPaint: Paint? = null


    private var mBitmapList: ArrayList<Bitmap>? = null
    private var mCurrentBitmap: Bitmap? = null
    private var mCurrentIndex = 0

    private var mPercent = 0f

    private var mHalfShadowHeight = 0f
    private var mHalfShadowWidth = 0f
    private var mTopMargin = 0f

    private var matrix: Matrix? = null
    private var mShadowRectF: RectF? = null
    private var animator: ValueAnimator? = null

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private fun setup() {
        mPercent = 0f
        mCurrentIndex = 0
        matrix = Matrix()
        mShadowRectF = RectF()
        mBitmapList = ArrayList()

        mShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mShadowPaint!!.style = Paint.Style.FILL
        mShadowPaint!!.color = mShadowColor

        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }


    fun addBitmap(bitMapId: Int) {
        try {
            val bitmap = BitmapFactory.decodeResource(resources, bitMapId)
            addBitmap(bitmap)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
    }

    fun addBitmap(bitmap: Bitmap?) {
        if (bitmap != null) {
            mBitmapList!!.add(bitmap)
        }
    }

    fun addBitmaps(bitmaps: ArrayList<Bitmap>?) {
        if (bitmaps != null) {
            mBitmapList!!.addAll(bitmaps)
        }
    }


    fun setDuration(duration: Int) {
        this.mDuration = duration
    }

    fun start() {
        stop()

        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f, 0f)
            animator?.interpolator = AccelerateDecelerateInterpolator()
            animator?.repeatCount = ValueAnimator.INFINITE
            animator?.addUpdateListener(AnimatorUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                if (value != mPercent) {
                    mPercent = value
                    postInvalidate()
                }
            })

            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    //重置index
                    mCurrentIndex = 0
                    mCurrentBitmap = mBitmapList!![mCurrentIndex]
                }

                override fun onAnimationRepeat(animation: Animator) {
                    if (mBitmapList != null && mBitmapList!!.size > 0) {
                        mCurrentIndex++
                        if (mCurrentIndex >= mBitmapList!!.size) {
                            mCurrentIndex = 0
                        }
                        mCurrentBitmap = mBitmapList!![mCurrentIndex]
                    }
                }
            })
        }


        animator!!.setDuration(mDuration.toLong())
        animator!!.start()
    }

    fun stop() {
        if (animator != null) {
            animator!!.cancel()
            animator = null
        }
    }


    fun setShadowColor(shadowColor: Int) {
        this.mShadowColor = shadowColor
        if (mShadowPaint != null) {
            mShadowPaint!!.color = mShadowColor
            postInvalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, 2 * width)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mHalfShadowWidth = max(16.0, (width / 2f * mPercent).toDouble()).toFloat()
        mHalfShadowHeight = max(8.0, (height / 8f * mPercent).toDouble()).toFloat()

        mShadowRectF!![width / 2f - mHalfShadowWidth, height * 7 / 8f - mHalfShadowHeight, width / 2f + mHalfShadowWidth] =
            height * 7 / 8f + mHalfShadowHeight
        canvas.drawOval(mShadowRectF!!, mShadowPaint!!)

        if (mCurrentBitmap != null) {
            canvas.save()
            mTopMargin = (height * 0.9f - mCurrentBitmap!!.height) * mPercent
            canvas.translate(width / 2f - mCurrentBitmap!!.width / 2f, mTopMargin)
            canvas.drawBitmap(mCurrentBitmap!!, matrix!!, mBitmapPaint)
            canvas.restore()
        }
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    companion object {
        private val TAG: String = BounceLoadingView::class.java.simpleName

        private const val SHADOW_COLOR = Color.LTGRAY


        private const val DEFAULT_DURATION = 800
    }
}