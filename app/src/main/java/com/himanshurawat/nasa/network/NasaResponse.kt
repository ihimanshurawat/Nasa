package com.himanshurawat.nasa.network

import com.google.gson.annotations.SerializedName

data class NasaResponse(
    private val date: String?,
    private val explanation: String?,
    private val hdurl: String?,
    @SerializedName("media_type")
    private val mediaType: String?,
    @SerializedName("service_version")
    private val serviceVersion: String?,
    private val title: String?,
    private val url: String?
)