package org.effervescence.app18.models

import android.os.Parcel
import android.os.Parcelable

data class Organizer(
        val name: String = "",
        val phoneNumber: Long = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Organizer> {
        override fun createFromParcel(parcel: Parcel): Organizer {
            return Organizer(parcel)
        }

        override fun newArray(size: Int): Array<Organizer?> {
            return arrayOfNulls(size)
        }
    }
}