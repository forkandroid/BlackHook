package com.quwan.tt.asmdemoapp

import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.Log
import android.widget.ImageView

/**
 * 创建人 lpb
 * 创建日期 2021/3/25
 */
object ImageHelper {
    const val MAX_ALARM_IMAGE_SIZE = 6 * 1024 * 1024
    const val TAG = "ImageHelper"
    const val MAX_ALARM_MULTIPLE = 1.2

    /**
     * 检查是否合法
     */
    fun checkIsLegal(target: ImageView) {
        target.measure(0,0)
        val drawable: Drawable =
            (if (target.drawable != null) target.drawable else target.background) ?: return
        Handler().post {

            val viewWidth: Int = target.measuredWidth
            val viewHeight: Int = target.measuredHeight
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight
            // 大小告警判断
            val imageSize = calculateImageSize(drawable)
            if (imageSize > MAX_ALARM_IMAGE_SIZE) {
                Log.e(TAG, "图片加载不合法， 大小$imageSize")
                dealWarning(drawableWidth, drawableHeight, imageSize, drawable)
            }
            // 宽高告警判断
            if (MAX_ALARM_MULTIPLE * viewWidth < drawableWidth) {
                Log.e(TAG, "图片加载不合法, 控件宽度 -> $viewWidth  drawableWidth: ->$drawableWidth")
                dealWarning(drawableWidth, drawableHeight, imageSize, drawable)
            }
            if (MAX_ALARM_MULTIPLE * viewHeight < drawableHeight) {
                Log.e(TAG, "图片加载不合法, 控件高度 -> $viewHeight drawableHeight ->")
                dealWarning(drawableWidth, drawableHeight, imageSize, drawable)
            }
        }

    }

    /**
     * 处理警告
     */
    private fun dealWarning(
        drawableWidth: Int,
        drawableHeight: Int,
        imageSize: Int,
        drawable: Drawable
    ) {
        // 线上线下处理方式需要不一致，伪代码
        // 线上弹出提示窗口把信息输出，同时提供一个关闭打开开关
        // ......
        // 线下需要搜集代码信息，代码具体在哪里，把信息上报到服务器
        // ......
    }

    /**
     * 计算 drawable 的大小
     */
    private fun calculateImageSize(drawable: Drawable): Int {
        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap
            return bitmap.byteCount
        }
        val pixelSize = if (drawable.opacity != PixelFormat.OPAQUE) 4 else 2
        return pixelSize * drawable.intrinsicWidth * drawable.intrinsicHeight
    }
}