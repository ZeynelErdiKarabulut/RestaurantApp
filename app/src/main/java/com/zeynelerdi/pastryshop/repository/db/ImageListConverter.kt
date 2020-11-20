

package com.zeynelerdi.pastryshop.repository.db

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils


/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * [TypeConverter] for room database.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class ImageListConverter {

    /**
     * Convert the comma separated string [value], into the [ArrayList] of string.
     */
    @TypeConverter
    fun toImageList(value: String?): ArrayList<String> {
        return if (value == null) {
            ArrayList()
        } else {
            ArrayList(value.split(",").toList())
        }
    }

    /**
     * Convert the [arrayList] into the comma separated string.
     */
    @TypeConverter
    fun fromImageList(arrayList: ArrayList<String>): String {
        return TextUtils.join(",", arrayList)
    }
}