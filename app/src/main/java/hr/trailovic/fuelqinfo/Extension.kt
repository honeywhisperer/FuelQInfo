package hr.trailovic.fuelqinfo

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun flashMessage(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Long.toDateString(timezoneOffset: Int = 0): String {
    val format = SimpleDateFormat("EEE, dd/MM/yyyy", Locale.ROOT)
    format.timeZone = TimeZone.getTimeZone("UTC") // ***
    val date = Date(this + timezoneOffset)
    return format.format(date)
}