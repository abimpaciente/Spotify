package com.example.spotify.model.common

import com.example.spotify.model.SpotifyResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MediaApi {
    @GET(END_POINT)
    fun findMedia(
        @Query(PARAM_TERM) term: String,
        @Query(PARAM_MEDIA) media: String,
        @Query(PARAM_ENTITY) entity: String,
        @Query(PARAM_LIMIT) limit: String
    ):Call<SpotifyResponse>

    companion object {
        //https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50
        private const val BASE_URL = "https://itunes.apple.com/"
        private const val END_POINT = "search"
        private const val PARAM_TERM = "term"
        private const val PARAM_MEDIA = "amp;media"
        private const val PARAM_ENTITY = "amp;entity"
        private const val PARAM_LIMIT = "limit"

        fun initRetrofit(): MediaApi {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MediaApi::class.java)
        }

    }


}