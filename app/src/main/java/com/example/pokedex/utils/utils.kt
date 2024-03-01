package com.example.pokedex.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import java.io.File

lateinit var progressDialog: ProgressDialog
fun setProgressDialog(context: Context) {
    progressDialog = ProgressDialog(context)
    progressDialog.setMessage(context.getString(R.string.server_connection))
    progressDialog.setCancelable(false)
}

fun showProgressDialog(show: Boolean) {
    when(show){
        true -> progressDialog.show()
        else -> progressDialog.dismiss()
    }
}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun ImageView.setImageUrl(url: String?) {
    if (url != null) {
        if (url.startsWith("http")) {
            Glide.with(context).load(url).into(this)
        } else {
            val file = File(url)
            if (file.exists()) {
                val imageUri = Uri.fromFile(file)
                Glide.with(context).load(imageUri).into(this)
            }
        }
    }
}