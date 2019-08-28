package com.parshwahotel.parshwahotelapp.extentionfunctions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.app.Application
import android.net.ConnectivityManager
import com.parshwahotel.parshwahotelapp.repository.Repository
import com.parshwahotel.parshwahotelapp.utility.NetworkConstants
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.*
import java.nio.channels.ClosedChannelException
import javax.net.ssl.SSLException
import android.R.attr.thumbnail
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squats.moviesapp.R
import kotlin.math.roundToInt


fun Application.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.log(tag: String, message: String) {
    Log.i(tag, message)
}

fun Repository.handleNetworkError(error: Throwable, application: Application) {
    when (error) {
        is HttpException -> when ((error as HttpException).code()) {
            in 100 downTo 1 -> application.log(
                NetworkConstants.networkErrorLogTag,
                "HttpExceptionCodeIs:- ${error.code()} AND messageIs :- ${error.message}"
            ) //It means the request has been received and the process is continuing.
            in 200 downTo 101 -> application.log(
                NetworkConstants.networkErrorLogTag,
                "HttpExceptionCodeIs:- ${error.code()} AND messageIs :- ${error.message}"
            )//It means the action was successfully received, understood, and accepted.
            in 300 downTo 201 -> application.log(
                NetworkConstants.networkErrorLogTag,
                "HttpExceptionCodeIs:- ${error.code()} AND messageIs :- ${error.message}"
            )//It means further action must be taken in order to complete the request.
            in 400 downTo 301 -> application.log(
                NetworkConstants.networkErrorLogTag,
                "HttpExceptionCodeIs:- ${error.code()} AND messageIs :- ${error.message}"
            )//It means the request contains incorrect syntax or cannot be fulfilled.
            in 500 downTo 401 -> application.log(
                NetworkConstants.networkErrorLogTag,
                "HttpExceptionCodeIs:- ${error.code()} AND messageIs :- ${error.message}"
            )//It means the server failed to fulfill an apparently valid request.
            else -> application.log(NetworkConstants.networkErrorLogTag, "UnknownHttpException")
        }
        is SocketTimeoutException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "SocketTimeoutExceptionMessageIs:- ${error.message}"
        )
        is UnknownHostException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "UnknownHostExceptionMessageIs:- ${error.message}"
        )
        is IOException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "IOExceptionMessageIs:- ${error.message}"
        )
        is UnknownServiceException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "UnknownServiceExceptionMessageIs:- ${error.message}"
        )
        is SSLException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "SSLExceptionMessageIs:- ${error.message}"
        )
        is SocketException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "SocketExceptionMessageIs:- ${error.message}"
        )
        is ProtocolException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "ProtocolExceptionMessageIs:- ${error.message}"
        )
        is PortUnreachableException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "PortUnreachableExceptionMessageIs:- ${error.message}"
        )
        is NoRouteToHostException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "NoRouteToHostExceptionMessageIs:- ${error.message}"
        )
        is InterruptedIOException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "InterruptedIOExceptionMessageIs:- ${error.message}"
        )
        is ConnectException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "ConnectExceptionMessageIs:- ${error.message}"
        )
        is ClosedChannelException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "ClosedChannelExceptionMessageIs:- ${error.message}"
        )
        is BindException -> application.log(
            NetworkConstants.networkErrorLogTag,
            "BindExceptionMessageIs:- ${error.message}"
        )
        is JsonDataException -> application.log(
            NetworkConstants.jsonErrorLogTag,
            "JsonDataExceptionMessageIs:- ${error.message}"
        )
        is NullPointerException -> application.log(
            NetworkConstants.jsonErrorLogTag,
            "NullPointerExceptionMessageIs:- ${error.message}"
        )
        else -> application.log(
            NetworkConstants.networkErrorLogTag,
            "UnknownExceptionMessageIs:- ${error.message}"
        )
    }
}


fun Application.isConnectedToInternet(): Boolean =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as
            ConnectivityManager).activeNetworkInfo?.isConnected == true


fun Context.log(tag: String, message: String) {
    Log.e(tag, message)
}

fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

@BindingAdapter("android:src")
fun loadImage(imageView: ImageView, string_url: String) {
    Glide.with(imageView.context).load(string_url)
        .thumbnail(0.5f)
        .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_launcher_foreground))
        .into(imageView)
}
