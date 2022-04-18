package com.example.spotify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class SpotifyResponse(
    val results: List<TrackItem>
)

@Parcelize
data class TrackItem(
    val trackName: String,
    val artistName: String,
    val artworkUrl60: String,
    val collectionName: String,
    val currency: String,
    val trackPrice: String,
    val previewUrl: String
) : Parcelable