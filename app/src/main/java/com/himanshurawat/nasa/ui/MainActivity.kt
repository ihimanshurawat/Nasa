package com.himanshurawat.nasa.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.himanshurawat.nasa.R
import com.himanshurawat.nasa.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), MainActivityContracts.View,
    DatePickerDialog.OnDateSetListener {

    private lateinit var presenter: MainActivityContracts.Presenter
    private lateinit var previousCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this)

        /*
        requesting API data for today's date
         */
        presenter.requestData(Calendar.getInstance())

        /*
        setting up listeners
         */
        datePickerImageView.setOnClickListener {
            presenter.clickDatePicker()
        }

        mediaButtonImageView.setOnClickListener {
            presenter.clickMediaButton(mediaButtonImageView.getMediaState())
        }

        /*
        implementing double tap gesture to avoid zoom-in and zoom-out using tapping
         */
        photoImageView.setOnDoubleTapListener(object: GestureDetector.OnDoubleTapListener{
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                return true
            }

            override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                return true
            }
        })
    }


    override fun setZoom(value: Float) {
        when(value){
            MIN_SCALE ->{
                photoImageView.setScale(MIN_SCALE,true)
            }
            MAX_SCALE ->{
                photoImageView.setScale(MAX_SCALE,true)
            }
        }
    }

    /*
    the fun to show/hide title
     */
    override fun showTitle(visibility: Boolean) {
        if(visibility){
            titleTextView.visibility = View.VISIBLE
        }else{
            titleTextView.visibility = View.GONE
        }
    }

    /*
    the fun to show/hide description
     */
    override fun showDescription(visibility: Boolean) {
        if(visibility){
            descriptionTextView.visibility = View.VISIBLE
        }else{
            descriptionTextView.visibility = View.GONE
        }
    }

    /*
    the fun to show/hide media button
     */
    override fun showMediaButton(visibility: Boolean) {
        if(visibility){
            mediaButtonImageView.visibility = View.VISIBLE
        }else{
            mediaButtonImageView.visibility = View.GONE
        }
    }

    /*
    the fun to show/hide data picker button
     */
    override fun showDatePickerIcon(visibility: Boolean) {
        if(visibility){
            datePickerImageView.visibility = View.VISIBLE
        }else{
            datePickerImageView.visibility = View.GONE
        }
    }

    /*
    the fun to set title
     */
    override fun setTitle(title: String) {
        titleTextView.text = title
    }

    /*
   the fun to set description
    */
    override fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    /*
   the fun to set media button
    */
    override fun setMediaButton(type: Int) {
        when(type){
            IMAGE -> {
                mediaButtonImageView.setMediaState(IMAGE)
                mediaButtonImageView.setImageResource(R.drawable.ic_zoom_in)
            }
            IMAGE_EXPANDED -> {
                mediaButtonImageView.setMediaState(IMAGE_EXPANDED)
                mediaButtonImageView.setImageResource(R.drawable.ic_zoom_out)
            }
            VIDEO -> {
                mediaButtonImageView.setMediaState(VIDEO)
                mediaButtonImageView.setImageResource(R.drawable.ic_play)
            }
        }
    }

    /*
   the fun to set Media
    */
    override fun setMediaLayout(url: String) {
        photoImageView.maximumScale = MAX_SCALE
        photoImageView.minimumScale = MIN_SCALE
        Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                presenter.showImageSuccess(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                presenter.showImageSuccess(true)
                return false
            }
        }).into(photoImageView)
    }


    /*
   the fun to display date picker dialogue
    */
    override fun displayDatePicker() {
        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()
        if(!::previousCalendar.isInitialized){
            previousCalendar = maxDate
        }
        minDate.set(1995,5,16)
        /*
        setting the last selected date
         */
        val datePickerDialog = DatePickerDialog(this
            ,this
            ,previousCalendar.get(Calendar.YEAR)
            ,previousCalendar.get(Calendar.MONTH)
            ,previousCalendar.get(Calendar.DATE))

        datePickerDialog.datePicker.minDate = minDate.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
        datePickerDialog.show()
    }

    /*
    enters fullscreen UI
     */
    override fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    /*
    return the UI back to normal
     */
    override fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    /*
    Show or Hide Circular Progress Bar
     */
    override fun showProgressBar(visibility: Boolean) {
        if(visibility){
            photoProgressBar.visibility = View.VISIBLE
        }else{
            photoProgressBar.visibility = View.GONE
        }
    }

    /*
    Display the SnackBar
     */
    override fun showSnackBar(value: String) {
        Snackbar.make(rootView,value,Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }

    override fun youtubeIntent(url: String) {
        val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(youtubeIntent)
    }

    /*
    invoked when a data is selected from the DataPicker
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(year,month,dayOfMonth)
        previousCalendar = cal
        presenter.requestData(cal)
    }
}
