package com.himanshurawat.nasa.ui

import com.himanshurawat.nasa.network.NasaResponse
import com.himanshurawat.nasa.utils.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivityPresenter(private val view: MainActivityContracts.View): MainActivityContracts.Presenter {
    private var model: MainActivityContracts.Model = MainActivityModel(this)

    override fun clickMediaButton(state: Int) {

        when(state){
            IMAGE ->{
                view.hideSystemUI()
                view.showDescription(false)
                view.showTitle(false)
                view.showDatePickerIcon(false)
                view.setMediaButton(IMAGE_EXPANDED)
                view.setZoom(MAX_SCALE)

            }
            IMAGE_EXPANDED ->{
                view.showSystemUI()
                view.showDescription(true)
                view.showTitle(true)
                view.showDatePickerIcon(true)
                view.setMediaButton(IMAGE)
                view.setZoom(MIN_SCALE)

            }
        }
    }

    override fun clickDatePicker() {
        view.displayDatePicker()
    }

    override fun requestData(cal: Calendar) {
        val date = cal.time
        val dateFormat = SimpleDateFormat("YYYY-MM-dd")
        val dateString = dateFormat.format(date)
        model.getData(dateString)
    }

    override fun populateData(response: NasaResponse) {
        /*
        protecting responses in a null check since API documentation -> https://api.nasa.gov/ is not very clear
         */
        if(response.title != null) view.setTitle(response.title)
        if(response.explanation != null) view.setDescription(response.explanation)
        if(response.hdurl != null) view.setMediaLayout(response.hdurl)
        if(response.mediaType != null){
            when(response.mediaType){
                MEDIA_TYPE_IMAGE ->{
                    view.setMediaButton(IMAGE)
                }
                MEDIA_TYPE_VIDEO ->{
                    view.setMediaButton(VIDEO)
                }
            }
        }


    }

    override fun populateDataFailed() {

    }
}