package com.himanshurawat.nasa.ui

import android.graphics.Bitmap
import com.himanshurawat.nasa.network.NasaResponse
import java.util.*

interface MainActivityContracts {

    interface Model{
        fun getData(date: String)
    }

    interface View{
        fun setZoom(value: Float)
        fun showTitle(visibility: Boolean)
        fun showDescription(visibility: Boolean)
        fun showMediaButton(visibility: Boolean)
        fun showDatePickerIcon(visibility: Boolean)
        fun setTitle(title: String)
        fun setDescription(description: String)
        fun setMediaButton(type: Int)
        fun setMediaLayout(url: String)
        fun displayDatePicker()
        fun hideSystemUI()
        fun showSystemUI()
        fun showProgressBar(visibility: Boolean)
        fun showSnackBar(value: String)
        fun youtubeIntent(url: String)
    }

    interface Presenter{
        fun clickMediaButton(state: Int)
        fun clickDatePicker()
        fun requestData(cal: Calendar)
        fun populateData(response: NasaResponse)
        fun populateDataFailed()
        fun showImageSuccess(status: Boolean)
    }
}
