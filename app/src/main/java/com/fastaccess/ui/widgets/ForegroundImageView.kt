package com.fastaccess.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.jvm.JvmOverloads
import androidx.appcompat.widget.TooltipCompat
import androidx.annotation.ColorInt
import com.fastaccess.helper.ViewHelper

class ForegroundImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0) {
        if (contentDescription != null) {
            TooltipCompat.setTooltipText(this, contentDescription)
        }
    }

    fun tintDrawableColor(@ColorInt color: Int) {
        ViewHelper.tintDrawable(drawable, color)
    }
}