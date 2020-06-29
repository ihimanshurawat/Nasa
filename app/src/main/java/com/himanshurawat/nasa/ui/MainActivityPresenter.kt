package com.himanshurawat.nasa.ui

import android.media.MediaMetadataRetriever
import com.himanshurawat.nasa.network.NasaResponse
import com.himanshurawat.nasa.utils.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class MainActivityPresenter(private val view: MainActivityContracts.View): MainActivityContracts.Presenter {

    private var model: MainActivityContracts.Model = MainActivityModel(this)

    private var url: String? = null

    /*
    the media button with it's states
    IMAGE - Zoom is Clicked
    IMAGE_EXPANDED - Zoom Out is Clicked
    VIDEO - For media_type (currently only opens YouTube videos using Intent)
     */
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
            VIDEO ->{
                if(url != null){
                    view.youtubeIntent(url as String)
                }
            }
        }
    }

    /*
    display date picker when interacted with the ui
     */
    override fun clickDatePicker() {
        view.displayDatePicker()
    }

    /*
    function to request data from the model
     */
    override fun requestData(cal: Calendar) {
        val date = cal.time
        val dateFormat = SimpleDateFormat("YYYY-MM-dd")
        val dateString = dateFormat.format(date)
        model.getData(dateString)
        view.showProgressBar(true)
    }

    /*
    invoked from the the model to display the data when available
     */
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
                    if(response.hdurl != null) view.setMediaLayout(response.hdurl)
                    view.setMediaButton(IMAGE)
                }
                MEDIA_TYPE_VIDEO ->{
                    if(response.url != null){
                        if(response.url.contains("youtube",ignoreCase = true)){
                            /*
                            since the API response is not known I am parsing the url to get the YouTube video id.
                             */
                            url = response.url
                            val pattern = Pattern.compile(
                                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                                Pattern.CASE_INSENSITIVE);
                            val matcher = pattern.matcher(response.url)
                            if (matcher.matches()){
                                val id = matcher.group(1)
                                val thumbnailUrl = "https://img.youtube.com/vi/$id/hqdefault.jpg"
                                view.setMediaLayout(thumbnailUrl)
                            }
                        }
                    }
                    view.setMediaButton(VIDEO)
                }
            }
        }
    }


    /*
    when retrofit is unable to fetch data for the response - Internet/API Failure/Throttle
     */
    override fun populateDataFailed() {
        view.setTitle("An error has occurred!")
        view.setDescription("We are unable to process the your request at the moment. Please check your internet connectivity and try again after sometime!")
    }

    /*
    triggered to deal the failures in image loading
     */
    override fun showImageSuccess(status: Boolean) {
        if(status){
            view.showProgressBar(false)
        }else{
            view.showProgressBar(false)
            view.showSnackBar("Failed to Load the Image!")
        }
    }
}