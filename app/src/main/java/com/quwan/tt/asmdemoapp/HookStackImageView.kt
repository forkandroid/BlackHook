package com.quwan.tt.asmdemoapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

/**
 * 重写setImageDrawable
 * 监听图片是否合理
 */
class HookStackImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        checkDrawable(this, drawable)
    }

    private fun checkDrawable(imageView: ImageView, drawable: Drawable?) {
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            checkIllegalBitmap(imageView, bitmap)
        }
    }

    private fun checkIllegalBitmap(imageView: ImageView, bitmap: Bitmap) {
        val imageViewWidth = imageView.width
        val imageViewHeight = imageView.height
        if (imageViewWidth > 0 && imageViewHeight > 0) {
            if (bitmap.width >= imageViewWidth * MAX_PROPORTION
                && bitmap.height >= imageViewHeight * MAX_PROPORTION
            ) {
                val stackTrace: Throwable = RuntimeException("local icon size too large")
                printWarn(bitmap, imageView, stackTrace)
            } else {
                printNormal(bitmap, imageView)
            }
        } else {
            imageView.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val imageViewWidth = imageView.width
                    val imageViewHeight = imageView.height
                    if (imageViewWidth > 0 && imageViewHeight > 0) {
                        if (bitmap.width >= imageViewWidth * MAX_PROPORTION
                            && bitmap.height >= imageViewHeight * MAX_PROPORTION
                        ) {
                            val stackTrace: Throwable =
                                RuntimeException("local icon size too large")
                            printWarn(bitmap, imageView, stackTrace)
                        } else {
                            printNormal(bitmap, imageView)
                        }
                        imageView.viewTreeObserver.removeOnPreDrawListener(this)
                    }
                    return true
                }
            })
        }
    }

    private fun getBitmapByteCount(bitmap: Bitmap): Int {
        return bitmap.rowBytes * bitmap.height
    }

    @SuppressLint("LongLogTag")
    private fun printWarn(bitmap: Bitmap, imageView: ImageView, stackTrace: Throwable) {
        val warnInfo = StringBuilder()
            .append("\n bitmap widthAndHigh: (").append(bitmap.width).append("px").append('*')
            .append(bitmap.height).append("px").append(')')
            .append("\n imageView widthAndHigh: (")
            .append(pxToDp(imageView.context, imageView.width.toFloat())).append("dp")
            .append('*').append(pxToDp(imageView.context, imageView.height.toFloat()))
            .append("dp").append(')')
            .append("\n bitmap byteSize: (").append(getBitmapByteCount(bitmap)).append(')')
            .append("\n suggest bitmap byteSize: (").append(imageView.width * MAX_PROPORTION)
            .append('*').append(imageView.height * MAX_PROPORTION).append(')')
            .append("\n imageView: (").append(imageView.toString()).append(')')
            .append("\n call stack trace: \n")
            .append(Log.getStackTraceString(stackTrace)).append('\n')
            .toString()
        Log.i(tagStr, warnInfo)
    }

    @SuppressLint("LongLogTag")
    private fun printNormal(bitmap: Bitmap, imageView: ImageView) {
        val normalInfo = StringBuilder("local icon size is Normal: \n")
            .append("\n bitmap widthAndHigh: (").append(bitmap.width).append("px").append('*')
            .append(bitmap.height).append("px").append(')')
            .append("\n imageView widthAndHigh: (")
            .append(pxToDp(imageView.context, imageView.width.toFloat())).append("dp")
            .append('*').append(pxToDp(imageView.context, imageView.height.toFloat()))
            .append("dp").append(')')
            .append("\n bitmap byteSize: (").append(getBitmapByteCount(bitmap)).append(')')
            .append("\n imageView: (").append(imageView.toString()).append(')').append('\n')
            .toString()
        Log.i(tagStr, normalInfo)
    }

    companion object {
        const val tagStr = "====>LocalImageViewCheck:"
        const val MAX_PROPORTION = 1.2

    }

    private fun pxToDp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}