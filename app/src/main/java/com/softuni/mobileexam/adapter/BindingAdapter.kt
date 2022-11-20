package com.softuni.mobileexam.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.softuni.mobileexam.R

@BindingAdapter("setFirstCharToUpperCase")
fun TextView.setFirstCharToUpperCase(string: String) {
    text = String.format(string.substring(0, 1).uppercase() + string.substring(1).lowercase())
}

@BindingAdapter("setColor")
fun TextView.setColor(price: Double) {
    if (price < 0) setTextColor(resources.getColor(R.color.red))
    else setTextColor(resources.getColor(R.color.green))
}