package com.example.pc_3.kotlinmaterialdesign

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.pc_3.kotlinmaterialdesign.messages.snack
import kotlinx.android.synthetic.main.fragment_palette.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.sdk15.listeners.onItemSelectedListener


class PaletteFragment : Fragment() {
    var paletteImageView: ImageView? = null
    private lateinit var v: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_palette, container, false)
        paletteImageView = v.find(R.id.paletteImageView)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setPaletteColors()
        spinnerOptions.onItemSelectedListener {
            onItemSelected { _, _, position, _ ->
                when (position) {
                    1 -> paletteImageView?.scaleType = ImageView.ScaleType.CENTER
                    2 -> paletteImageView?.scaleType = ImageView.ScaleType.CENTER_CROP
                    3 -> paletteImageView?.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    4 -> paletteImageView?.scaleType = ImageView.ScaleType.FIT_CENTER
                    5 -> paletteImageView?.scaleType = ImageView.ScaleType.FIT_END
                    6 -> paletteImageView?.scaleType = ImageView.ScaleType.FIT_START
                    7 -> paletteImageView?.scaleType = ImageView.ScaleType.FIT_XY
                    8 -> paletteImageView?.scaleType = ImageView.ScaleType.MATRIX
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPaletteColors() {
        val drawable = paletteImageView?.drawable as BitmapDrawable
        val bitmap2 = drawable.bitmap
        Palette.from(bitmap2).generate { palette ->
            val default = ContextCompat.getColor(context!!, android.R.color.black)
            val darkMuted = palette.getDarkMutedColor(default)
            val muted = palette.getMutedColor(default)
            val lightMuted = palette.getLightMutedColor(default)
            val darkVibrant = palette.getDarkVibrantColor(default)
            val vibrant = palette.getVibrantColor(default)
            val dominant = palette.getVibrantColor(default)

            darkMutedImageView.setColorFilter(darkMuted)
            tvDarkMutedHex.text = getPaletteHex(palette.darkMutedSwatch)
            darkMutedImageView.onClick { v.snack(createSnackMessage(palette.mutedSwatch)) }

            mutedImageView.setColorFilter(muted)
            tvMutedHex.text = getPaletteHex(palette.mutedSwatch)
            mutedImageView.onClick { v.snack(createSnackMessage(palette.mutedSwatch)) }

            lightMutedImageView.setColorFilter(lightMuted)
            tvLightMutedHex.text = getPaletteHex(palette.lightMutedSwatch)
            lightMutedImageView.onClick { v.snack(createSnackMessage(palette.lightMutedSwatch)) }

            darkVibrantImageView.setColorFilter(darkVibrant)
            tvDarkVibrantHex.text = getPaletteHex(palette.darkVibrantSwatch)
            darkVibrantImageView.onClick { v.snack(createSnackMessage(palette.darkVibrantSwatch)) }

            vibrantImageView.setColorFilter(vibrant)
            tvVibrantHex.text = getPaletteHex(palette.vibrantSwatch)
            vibrantImageView.onClick { v.snack(createSnackMessage(palette.vibrantSwatch)) }

            dominantImageView.setColorFilter(dominant)
            tvDominantHex.text = getPaletteHex(palette.dominantSwatch)
            dominantImageView.onClick { v.snack("Dominant ${createSnackMessage(palette.dominantSwatch)}") }

            paletteGroup.visibility = View.VISIBLE
        }
    }

    private fun getPaletteHex(swatch: Palette.Swatch?) = "#${swatch?.rgb?.toString(16)?.toUpperCase()}"

    private fun createSnackMessage(swatch: Palette.Swatch?) =
            "Color: ${swatch?.titleTextColor}  Population: ${swatch?.population}"
}
