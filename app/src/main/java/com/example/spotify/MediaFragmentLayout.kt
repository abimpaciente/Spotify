package com.example.spotify

import android.app.AlertDialog
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.spotify.model.SpotifyResponse
import com.example.spotify.model.TrackItem
import com.example.spotify.model.common.MediaApi
import com.example.spotify.view.MediaAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MediaRockFragmentLayout"

class MediaFragmentLayout(private val typeMedia: String) : Fragment() {

    private lateinit var mediaList: RecyclerView
    private lateinit var adapter: MediaAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var listData: SpotifyResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_media_layout, container, false)

        initViews(view)
        getMedia(typeMedia)

        swipe.setOnRefreshListener {
            getMedia(typeMedia)
        }

        return view
    }


    private fun initViews(view: View) {
        mediaList = view.findViewById(R.id.movie_list_rock)
        mediaList.layoutManager = LinearLayoutManager(requireContext())
        swipe = view.findViewById(R.id.srl_refresh)
    }

    private fun getMedia(type: String) {
        swipe.isRefreshing = true
        MediaApi.initRetrofit().findMedia(
            type,
            "music",
            "song",
            "50"
        ).enqueue(
            object : Callback<SpotifyResponse> {
                override fun onResponse(
                    call: Call<SpotifyResponse>,
                    response: Response<SpotifyResponse>
                ) {
                    swipe.isRefreshing = false
                    Log.d(TAG, "onResponse: $response")

                    if (response.isSuccessful) {
                        updateAdapter(response.body())
                    } else
                        showError(response.message())
                }

                override fun onFailure(call: Call<SpotifyResponse>, t: Throwable) {
                    swipe.isRefreshing = false
                    Log.d(TAG, "onFailure: $t")
                }
            }
        )

    }


    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
    }


    private fun updateAdapter(body: SpotifyResponse?) {

        body?.let {
            adapter = MediaAdapter(it.results) { movieDetail ->
                activity?.onItemSelected(movieDetail)
            }
            mediaList.adapter = adapter
        } ?: showError("No response from server")
    }

    private var mMediaPlayer: MediaPlayer? = null

    private fun FragmentActivity.onItemSelected(dataPlay: TrackItem) {

        val build = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.media_item_layout, null)
        build.setView(view)

        view.findViewById<TextView>(R.id.tvTrackName).text = dataPlay.trackName
        view.findViewById<TextView>(R.id.tvArtistName).text = dataPlay.artistName
        view.findViewById<TextView>(R.id.tvPrice).text = dataPlay.trackPrice + dataPlay.currency
        Picasso.get().load(dataPlay.artworkUrl60)
            .into(view.findViewById<ImageView>(R.id.ivImageMedia))
        build.setView(view)
        val dialog = build.create()
        dialog.show()

        val btnPlay = view.findViewById<ImageView>(R.id.ivPlay)
        val btnStop = view.findViewById<ImageView>(R.id.ivStop)
        btnPlay.setOnClickListener {
            playMedia(btnPlay, btnStop, dataPlay)
        }
        btnStop.setOnClickListener {

            mMediaPlayer?.stop();
            btnPlay.visibility = View.VISIBLE
            btnStop.visibility = View.GONE
        }

        dialog.setOnCancelListener {
            mMediaPlayer?.stop()
        }

        btnPlay.visibility = View.VISIBLE
        btnStop.visibility = View.GONE
        playMedia(btnPlay, btnStop, dataPlay)


    }

    private fun playMedia(btnPlay: ImageView, btnStop: ImageView, dataPlay: TrackItem) {
        if (btnPlay.visibility == View.VISIBLE) {
            val url = dataPlay.previewUrl
            mMediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                prepare()
                start()
            }
            btnPlay.visibility = View.GONE
            btnStop.visibility = View.VISIBLE
        } else {
            mMediaPlayer?.stop()
        }
    }

}