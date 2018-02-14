package com.example.pc_3.kotlinmaterialdesign.adapters

import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pc_3.kotlinmaterialdesign.Place
import com.example.pc_3.kotlinmaterialdesign.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by Alberto Carrillo on 2/13/18.
 */
class TravelListAdapter(private val places: List<Place>, private val function: (Place) -> Unit) :
        RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

    override fun getItemCount() = places.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(places[position], function)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(place: Place, function: (Place) -> Unit) = with(itemView) {
            val photo = BitmapFactory.decodeResource(context.resources, place.resourceId)
            placeName.text = place.name
            Glide.with(context)
                    .load(place.resourceId)
                    .override(500, 300)
                    .fitCenter()
                    .into(placeImage)

            setOnClickListener { function(place) }
            Palette.from(photo).generate { palette ->
                val bgColor = palette.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
                placeNameHolder.setBackgroundColor(bgColor)
            }
        }
    }
}