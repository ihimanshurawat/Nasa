package com.himanshurawat.nasa.network

import com.google.gson.annotations.SerializedName

data class NasaResponse(
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("service_version")
    val serviceVersion: String?,
    val title: String?,
    val url: String?
)