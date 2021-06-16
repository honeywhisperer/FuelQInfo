package hr.trailovic.fuelqinfo.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class FuelRecord(
    @ColumnInfo val odometer: Int,
    @ColumnInfo val liters: Double,
    @ColumnInfo val date: Long,
    @ColumnInfo val comment: String?,
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
) : Parcelable