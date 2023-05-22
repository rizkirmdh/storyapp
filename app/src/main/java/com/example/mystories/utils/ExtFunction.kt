package com.example.mystories.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mystories.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun TextView.setDate(timestamp: String){
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val date = sdf.parse(timestamp) as Date

    val formatedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date)
    this.text = formatedDate
}

fun ImageView.setUrl(context: Context, url: String){
    Glide
        .with(context)
        .load(url)
        .into(this)
}