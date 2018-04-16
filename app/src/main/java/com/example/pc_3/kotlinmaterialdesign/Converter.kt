package com.example.pc_3.kotlinmaterialdesign

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Alberto Carrillo on 2/16/18.
 */
fun convertLayoutToImage(mContext: Context, count: Int, drawableId: Int): Drawable {
    val inflater = LayoutInflater.from(mContext)
    val view = inflater.inflate(R.layout.layout_notification, null)
    val textView = view.findViewById(R.id.tvBadgeCount) as TextView
    val badge = view.findViewById(R.id.icon_badge) as ImageView
    badge.setImageResource(drawableId)

    if (count == 0) textView.visibility = View.GONE
    if (count in 1..9) textView.text = "$count"
    if (count > 9) textView.text = "9+"

    view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    view.isDrawingCacheEnabled = true
    view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    val bitmap = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false

    return BitmapDrawable(mContext.resources, bitmap)
}