package com.example.spotify.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spotify.R
import com.example.spotify.model.TrackItem
import com.squareup.picasso.Picasso

class MediaAdapter(
    private val dataSet: List<TrackItem>, private val OnClickListener: (TrackItem) -> Unit
) :
    RecyclerView.Adapter<MediaAdapter.MediaHolder>() {
    class MediaHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val imageMedia: ImageView = view.findViewById(R.id.ivImageMedia)
        private val trackItem: TextView = view.findViewById(R.id.tvTrackName)
        private val artistName: TextView = view.findViewById(R.id.tvArtistName)
        private val priceMedia: TextView = view.findViewById(R.id.tvPrice)

        fun onBind(
            dataItem: TrackItem, OnClickListener: (TrackItem) -> Unit
        ) {
            Picasso.get().load(dataItem.artworkUrl60).into(imageMedia)
            trackItem.text = dataItem.trackName
            artistName.text = dataItem.artistName
            priceMedia.text = dataItem.trackPrice + dataItem.currency
            view.setOnClickListener { OnClickListener(dataItem) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        return MediaHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.media_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.onBind(dataSet[position], OnClickListener)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}