package org.effervescence.app18.models

import android.os.Parcel
import android.os.Parcelable

data class Event(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val location: String = "",
        val timestamp: Long = 0,
        val imageUrl: String = "",
        val categories: List<String> = emptyList(),
        val additionalInfo: List<String> = emptyList(),
        val facebookEventLink: String = "",
        val organizers: List<Organizer> = emptyList()
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.createTypedArrayList(Organizer)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(location)
        parcel.writeLong(timestamp)
        parcel.writeString(imageUrl)
        parcel.writeStringList(categories)
        parcel.writeStringList(additionalInfo)
        parcel.writeString(facebookEventLink)
        parcel.writeTypedList(organizers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}