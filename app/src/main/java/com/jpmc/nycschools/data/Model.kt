package com.jpmc.nycschools.data

import android.os.Parcel
import android.os.Parcelable

data class SchoolModel(
    val dbn: String?,
    val school_name: String?,
    val overview_paragraph: String?,
    val phone_number: String?,
    val school_email: String?,
    val website: String?,
    val location: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dbn)
        parcel.writeString(school_name)
        parcel.writeString(overview_paragraph)
        parcel.writeString(phone_number)
        parcel.writeString(school_email)
        parcel.writeString(website)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolModel> {
        override fun createFromParcel(parcel: Parcel): SchoolModel {
            return SchoolModel(parcel)
        }

        override fun newArray(size: Int): Array<SchoolModel?> {
            return arrayOfNulls(size)
        }
    }

}

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

data class SchoolDetails(
    val dbn: String? = "",
    val school_name: String? = "",
    val num_of_sat_test_takers: String? = "",
    val sat_critical_reading_avg_score: String? = "",
    val sat_math_avg_score: String? = "",
    val sat_writing_avg_score: String? = ""
)

