package com.sokoldev.griefresort.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Global {
    const val RC_CAMERA = 101
    const val RC_GALLERY = 102

    fun showMessage(rootView: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        val sb = Snackbar.make(rootView, message, length)
        sb.setBackgroundTint(Color.BLACK)
        sb.show()
    }

    fun emptyField(fields: Array<EditText>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            if (currentField.text.toString().isEmpty()) {
                return false
            }
        }
        return true
    }
    fun getTempFileUri(context: Context): Uri? {

        val tempFile = File.createTempFile("temp_image_file", "png", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            context,
            "com.beba.fileprovider",
            tempFile
        )

    }

    fun calculatePercentage(count:Int, totalCount:Int): Int{
        return  ((count.toDouble()/totalCount.toDouble()) * 100).toInt()
    }
    fun getAddress(
        context: Context,
        latitude: String,
        longitude: String
    ): Address? {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1) as List<Address>
            if (addresses.isEmpty()) return null
            return addresses[0]
        } catch (e: IOException) {
            return null

        } catch (e: NumberFormatException) {
            return null
        }


    }
//    fun checkLocationProvider(context: Context) : Boolean {
//        var gpsEnabled = false
//        var networkEnabled = false
//
//        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//
//        if (!gpsEnabled && !networkEnabled) {
//
//            MaterialAlertDialogBuilder(context)
//                .setTitle("Allow Location")
//                .setMessage(context.getString(R.string.messageAllowLocation))
//                .setPositiveButton(context.getString(R.string.enable)) { dialog, which ->
//                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                }
//                .setNegativeButton(context.getString(R.string.cancel)) { dialog, which ->
//                    dialog.cancel()
//
//
//                }
//                .show()
//            return false
//        } else {
//            return true
//        }
//    }

//    fun getProgressDialogFullScreen(context: Context): Dialog {
//        val progressDialog = Dialog(context, R.style.customDialogAnimation)
//        progressDialog.setCancelable(false)
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        progressDialog.setContentView(R.layout.layout_progress_full_screen)
//        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        progressDialog.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT
//        )
//        return progressDialog
//
//    }

//    fun getImageUrl(source: String?): String {
//        if (source == null) return ""
//        return if (source.startsWith("http")) {
//            source
//        } else {
//            ApiFactory.IMAGE_BASE_URL + source
//        }
//    }

    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

//    fun hidePassword(etPassword: EditText, ivPassword: ImageView) {
//        etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
//        ivPassword.setImageResource(R.drawable.ic_hide)
//    }
//    fun showPassword(etPassword: EditText, ivPassword: ImageView){
//        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//        ivPassword.setImageResource(R.drawable.show)
//    }

    fun getTimeDifference(starSource: String?, endSource: String?): String {




        if (starSource.isNullOrEmpty() || endSource.isNullOrEmpty()) return "No Time Available"
        val startDate = starSource
        val sdfStart = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val sdfEnd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val dateStart = sdfStart.parse(starSource)
        val dateEnd = sdfEnd.parse(endSource)

        val timeDifference = (dateEnd?.time ?: 0) - (dateStart?.time ?: 0)
        val numDays = (timeDifference / (1000 * 60 * 60 * 24)).toInt()
        val hours = (timeDifference / (1000 * 60 * 60)).toInt()
        val minute = (timeDifference / (1000 * 60)).toInt()
        val seconds = (timeDifference / 1000).toInt()



        return when {
            hours > 73 -> "${formatItemListingDate(starSource)}"
            hours > 23 -> "Yesterday"
            minute > 59 -> "$hours hours ago"
            seconds > 59 -> "$minute minutes ago"
            else -> {
                "Posted $ ago"
            }
        }
        return "No Time Available"
    }

    fun formatItemListingDate(source: String): String {
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'", Locale.getDefault())
        return try {
            val sourceDate = sdf.parse(source)
            sdf = SimpleDateFormat("MMM dd,yyy", Locale.getDefault())
            sdf.format(sourceDate ?: Date())
        } catch (e: ParseException) {
            ""

        }
    }

//    fun getProgressDialog(context: Context): Dialog {
//        val dialog = Dialog(context)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.layout_global_progress)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val params = dialog.window?.attributes
//        params?.gravity = Gravity.CENTER
//        return dialog
//    }

    @Throws(IOException::class)
    fun createImgFile(context: Context): File {
        val timeStemp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imgFileName = "JPEG_" + timeStemp + "_"
        val storageDir = context.getExternalFilesDir(DIRECTORY_PICTURES)

        return File.createTempFile(
            imgFileName,  //prefix
            ".jpg",  //suffix
            storageDir //directory
        )
    }

//    fun prepareFilePart(mContext: Context, partName: String, bitmap: Bitmap): MultipartBody.Part {
//
//        val file = persistImage(mContext,bitmap,bitmap.toString())
//
//        val requestBody = file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//        return MultipartBody.Part.createFormData(partName,file?.name, requestBody!!)
//
//    }

    fun persistImage(context: Context, bitmap: Bitmap, name: String): File? {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, "$name.jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e("context", "Error writing bitmap", e)
        }
        return imageFile
    }



    fun requiredRational(activity: Activity?, permissions: String?): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permissions!!)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
        if (contentUri == null) return ""
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val column_index: Int = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                path = it.getString(column_index)
            }
            cursor.close()
        }

        return path
    }


    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworks =
            connectivityManager.activeNetwork ?: return false
        val activeNetworkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetworks) ?: return false
        return when {
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false

        }

    }

    fun hasFeatureCamera(context: Context): Boolean {
        val pm = context.packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        var allGranted = false

        for (permission in permissions) {
            allGranted = (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED)

        }

        return allGranted
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            )
                return true

        }
        return false
    }


}