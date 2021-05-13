package com.quwan.tt.asmdemoapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

class ImageViewFactory2 : LayoutInflater.Factory2 {
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        if (name == "ImageView") {
            return HookStackImageView(context, attrs)
        }
        return null
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }
}