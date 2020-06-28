package com.himanshurawat.nasa

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.himanshurawat.nasa.utils.IMAGE
import kotlin.properties.Delegates

class MediaImageView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    private var mediaImageState by Delegates.notNull<Int>()

    fun setMediaState(state: Int){
        mediaImageState = state
    }
    fun getMediaState(): Int{
        return mediaImageState
    }
}