package com.sm.android.baseproject.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sm.android.baseproject.BuildConfig
import com.sm.android.baseproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

private fun Context.isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

fun Context.speakText(textToSpeech: String) {
    var tts: TextToSpeech? = null
    tts = TextToSpeech(this) { p0 ->
        Log.d("speakText", "speakText: $p0")
        if (p0 != TextToSpeech.ERROR) {
            Log.d("speakText", "no error: $p0")
            tts?.language = Locale.US
            tts?.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}

fun Context.rateUs(){
    val feedss =
        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
    val rate = Intent(Intent.ACTION_VIEW, feedss)
    startActivity(rate)
}

fun Context.composeEmail() {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:abcd@gmail.com") // only email apps should handle this
    intent.putExtra(Intent.EXTRA_EMAIL, "")
    intent.putExtra(Intent.EXTRA_SUBJECT, "App Name")
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent)
    }
}

fun Context.copyText(text: String?) {
    try {
        val clipboardManager =
            getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val newPlainText = ClipData.newPlainText("App name", text)
        clipboardManager.setPrimaryClip(newPlainText)
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
    }
}

fun Context.ShareText(text: String?) {
    try {
        if (text != null) {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(
                Intent.createChooser(
                    share,
                    "Share Text"
                )
            )
        }
    } catch (e: Exception) {
    }
}

fun Context.GotoSettings() {
    try {
        val intent = Intent()
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.fromParts("package", packageName, null))
        startActivity(intent)
    } catch (e: Exception) {
    }
}


fun Activity.dismissKeyboard() {
    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != currentFocus) imm.hideSoftInputFromWindow(
        currentFocus!!
            .applicationWindowToken, 0
    )
}

fun Context.setLocale(lang: String) {
    val local = if (lang == "") {
        Locale.getDefault()
    } else {
        Locale(lang)
    }
    val res = resources
    val displayMetrics = res.displayMetrics
    val config = res.configuration
    config.setLocale(local)
    @Suppress("DEPRECATION")
    res.updateConfiguration(config, displayMetrics)
}


val TAG: String = "tag"


/* ---------------------------------------------- Delay Runnable  ---------------------------------------------- */

fun withDelay(delay: Long = 300, callback: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(callback, delay)
}

inline fun View.postDelayed(delayInMillis: Long, crossinline action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    postDelayed(runnable, delayInMillis)
    return runnable
}

inline fun <reified T> Activity.startActivity(finish: Boolean = true) {
    Intent(this, T::class.java).apply {
        startActivity(this)
        if (finish) {
            finish()
        }
    }
}

/* ---------------------------------------------- Toast ---------------------------------------------- */
private var currentToast: Toast? = null

fun Context.showToastOnce(message: String) {
    Handler(Looper.getMainLooper()).post {
        currentToast?.cancel()
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }
}

fun Context.showToastNew(message: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.showToast(message: String) {
    (this as? Activity)?.runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showToastRes(@StringRes stringResId: Int) {
    showToast(getResString(stringResId))
}

fun Context?.debugToast(message: String) {
    if (BuildConfig.DEBUG) {
        showToast(message)
    }
}


/* ---------------------------------------------- SnackBar ---------------------------------------------- */

fun Context?.showSnackBar(message: String) {
    (this as? Activity)?.runOnUiThread {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

/* ---------------------------------------------- Intents ---------------------------------------------- */

fun Context?.openWebUrl(url: String) {
    this?.let {
        try {
            val uri = Uri.parse(url)
            it.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (ex: Exception) {
            Log.e(TAG, "openPlayStoreApp: ", ex)
        }
    }
}

fun Context?.openWebUrl(@StringRes stringId: Int) {
    val url = this?.getResString(stringId) ?: ""
    openWebUrl(url)
}

fun Context?.openPlayStoreApp(packageName: String? = this?.packageName) {
    val playStoreUrl = "https://play.google.com/store/apps/details?id=$packageName"
    openWebUrl(playStoreUrl)
}

fun Context?.openEmailApp(emailAddress: String) {
    this?.let {
        val appName = getResString(R.string.app_name)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback...")
        try {
            it.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Log.e(TAG, "openEmailApp: ", ex)
        }
    }
}

fun Context?.openEmailApp(@StringRes emailAddressId: Int) {
    val emailAddress = this?.getResString(emailAddressId) ?: ""
    openEmailApp(emailAddress)
}

fun Context?.shareApp() {
    this?.let {
        try {
            val playStoreUrl = "https://play.google.com/store/apps/details?id=$packageName"
            val appName = getResString(R.string.app_name)
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
            sendIntent.putExtra(Intent.EXTRA_TEXT, playStoreUrl)
            sendIntent.type = "text/plain"
            it.startActivity(sendIntent)
        } catch (ex: Exception) {
            Log.e(TAG, "shareApp: ", ex)
        }
    }
}

fun Context?.openSubscriptions() {
    this?.let {
        try {
            val playStoreUrl = "https://play.google.com/store/account/subscriptions?package=$packageName"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)));
        } catch (ex: ActivityNotFoundException) {
            Log.e(TAG, "openSubscriptions: ", ex)
        }
    }
}

fun Context?.copyClipboardData(text: String) {
    this?.let {
        try {
            val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", text)
            clipboard.setPrimaryClip(clip)
        } catch (ex: Exception) {
            Log.e(TAG, "copyClipboardData: ", ex)
        }
    }
}


fun Context?.searchData(text: String) {
    this?.let {
        try {
            val intentSearch = Intent(Intent.ACTION_WEB_SEARCH)
            intentSearch.putExtra(SearchManager.QUERY, text)
            it.startActivity(intentSearch)
        } catch (ex: Exception) {
            Log.e(TAG, "searchData: ", ex)
        }
    }
}


var clickCount = 0
var lastClickTime = 0L

/**
 * Invoke the [action] after click [count] times.
 * The interval between two clicks is less than [interval] mills
 */
fun View.clickN(count: Int = 1, interval: Long = 1000, action: () -> Unit) {

    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime > interval)) {
            clickCount = 1
            lastClickTime = currentTime
            return@setOnClickListener
        }

        ++clickCount
        lastClickTime = currentTime

        if (clickCount == count) {
            clickCount = 0
            lastClickTime = 0L
            action()
        }
    }
}

fun Context.drawableToBitmap(drawableId: Int): Bitmap {
    val drawable = getDrawable(drawableId) ?: throw IllegalArgumentException("Drawable not found")
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Activity?.isSupportFullScreen(): Boolean {
    try {
        val outMetrics = DisplayMetrics()
        this?.windowManager?.defaultDisplay?.getMetrics(outMetrics)
        if (outMetrics.heightPixels > 1280) {
            return true
        }
    } catch (ignored: Exception) {}
    return false
}

// Function to check if an array of permissions is granted
fun Context.arePermissionsGranted(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}



fun View.collapseAndExpand() {
    // Collapse animation (scale down)
    val collapse = ObjectAnimator.ofPropertyValuesHolder(this,
        PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),  // Scale X (width)
        PropertyValuesHolder.ofFloat("scaleY", 1f, 0f)   // Scale Y (height)
    )
    collapse.duration = 300L

    // Expand animation (scale up)
    val expand = ObjectAnimator.ofPropertyValuesHolder(this,
        PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),  // Scale X (width)
        PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)   // Scale Y (height)
    )
    expand.duration = 300L

    // Start collapse, then expand after collapse ends
    collapse.start()
    collapse.addListener(object : android.animation.AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            expand.start()
        }
    })
}

fun View.collapseAndExpandInvisible(duration: Long = 300) {
    // Collapse animation (scale down to invisible)
    val collapse = ObjectAnimator.ofPropertyValuesHolder(this,
        PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),  // Scale X (width)
        PropertyValuesHolder.ofFloat("scaleY", 1f, 0f),  // Scale Y (height)
        PropertyValuesHolder.ofFloat("alpha", 1f, 0f)    // Fade out
    )
    collapse.duration = duration

    // Expand animation (scale back to visible)
    val expand = ObjectAnimator.ofPropertyValuesHolder(this,
        PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),  // Scale X (width)
        PropertyValuesHolder.ofFloat("scaleY", 0f, 1f),  // Scale Y (height)
        PropertyValuesHolder.ofFloat("alpha", 0f, 1f)    // Fade in
    )
    expand.duration = duration

    // Start collapse, then expand after collapse ends
    collapse.start()
    collapse.addListener(object : android.animation.AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            expand.start()
        }
    })
}

fun View.animExpandCollapse(onAnimationEnd: (() -> Unit)? = null) {
    animate().apply {
        scaleX(1.1f)
        scaleY(1.1f)
        duration = 50
    }.withEndAction {
        animate().apply {
            scaleX(1.0f)
            scaleY(1.0f)
            duration = 40
        }.withEndAction {
            onAnimationEnd?.invoke() // Invoke the callback after the second animation ends
        }.start()
    }.start()
}


fun View.testAlphaAnimation() {
    animate().alpha(0.5f).setDuration(100).withEndAction {
        animate().alpha(1.0f).setDuration(100).start()
    }.start()
}


fun View.addPressEffect(scaleFactor: Float = 0.9f, duration: Long = 150) {
    // Set an OnTouchListener to handle press and release
    this.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Collapse (scale down)
                ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", 1f, scaleFactor),
                    PropertyValuesHolder.ofFloat("scaleY", 1f, scaleFactor)
                ).apply {
                    this.duration = duration
                    start()
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Expand (scale up)
                ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", scaleFactor, 1f),
                    PropertyValuesHolder.ofFloat("scaleY", scaleFactor, 1f)
                ).apply {
                    this.duration = duration
                    start()
                }
            }
        }
        // Return true to consume the touch event
        true
    }
}

fun View.addPressEffectWithClick(
    scaleFactor: Float = 0.9f,
    duration: Long = 150,
    onClick: (() -> Unit)? = null
) {
    // Set an OnTouchListener to handle press and release
    this.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Collapse (scale down)
                ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", 1f, scaleFactor),
                    PropertyValuesHolder.ofFloat("scaleY", 1f, scaleFactor)
                ).apply {
                    this.duration = duration
                    start()
                }
            }

            MotionEvent.ACTION_UP -> {
                // Expand (scale up)
                ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", scaleFactor, 1f),
                    PropertyValuesHolder.ofFloat("scaleY", scaleFactor, 1f)
                ).apply {
                    this.duration = duration
                    start()
                }

                // Trigger click listener if defined
                onClick?.invoke()
            }

            MotionEvent.ACTION_CANCEL -> {
                // Expand back if touch is canceled
                ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", scaleFactor, 1f),
                    PropertyValuesHolder.ofFloat("scaleY", scaleFactor, 1f)
                ).apply {
                    this.duration = duration
                    start()
                }
            }
        }
        true // Consume the touch event
    }
}


fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    withContext(Dispatchers.Main) {
        onPreExecute()
    }
    val result = withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
        doInBackground()
    }
    withContext(Dispatchers.Main) {
        onPostExecute(result)
    }
}


