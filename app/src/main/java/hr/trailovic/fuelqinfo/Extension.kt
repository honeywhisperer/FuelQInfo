package hr.trailovic.fuelqinfo

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/** display quick toast message */
fun flashMessage(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/** display long message */
fun <T> displayMessage(context: Context, list: List<T>){
    val toast = Toast.makeText(context, list.joinToString("\n\n"), Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()

}

/** convert Long to Mon, 14/06/2021 format */
fun Long.toLongDateString(timezoneOffset: Int = 0): String {
    val format = SimpleDateFormat("EEE, dd/MM/yyyy", Locale.ROOT)
    format.timeZone = TimeZone.getTimeZone("UTC") // ***
    val date = Date(this + timezoneOffset)
    return format.format(date)
}

/** convert Long to 14/06/2021 format */
fun Long.toDateString(timezoneOffset: Int = 0): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    format.timeZone = TimeZone.getTimeZone("UTC") // ***
    val date = Date(this + timezoneOffset)
    return format.format(date)
}

/** present double value with one decimal */
fun Double.oneDecimal(): String {
    return String.format("%.1f", this)
}