package com.example.pc_3.kotlinmaterialdesign.recycler

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.pc_3.kotlinmaterialdesign.recycler.models.Place
import com.example.pc_3.kotlinmaterialdesign.R
import kotlinx.android.synthetic.main.item_place.view.*

/**
 * Created by Alberto Carrillo on 2/13/18.
 */
class TravelListAdapter(private val places: List<Place>, private val function: (Place, View) -> Unit) :
        RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position], function)
    }

    override fun getItemCount() = places.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(place: Place, function: (Place, View) -> Unit) = with(itemView) {
            placeName.text = place.name
            loadImage(context, place.resourceId, itemView.placeImage)
            setPaletteColors(context, place.resourceId, itemView.placeNameHolder)

            setOnClickListener { function(place, it) }
        }

        private fun setPaletteColors(context: Context, res: Int, placeNameHolder: LinearLayout) {
            val photo = BitmapFactory.decodeResource(context.resources, res)
            Palette.from(photo).generate { palette ->
                val bgColor = palette.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
                placeNameHolder.setBackgroundColor(bgColor)
            }
        }

        private fun loadImage(context: Context, res: Int, placeImage: ImageView) {
            Glide.with(context)
                    .load(res)
                    .override(500, 300)
                    .fitCenter()
                    .into(placeImage)
        }
    }
}