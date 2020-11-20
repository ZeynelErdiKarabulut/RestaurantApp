

package com.zeynelerdi.pastryshop.bin

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Bin to hold the page information.
 *
 * @param id Unique id of the page.
 * @param title Title of the page.
 * @param description Description of the page.
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Entity(tableName = Pages.PAGES_TABLE)
data class Pages(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = PAGE_ID)
        var id: Long,

        @ColumnInfo(name = PAGE_NAME)
        val title: String,

        @ColumnInfo(name = PAGE_DESCRIPTION)
        val description: String,

        val image: ArrayList<String>
) {

    /**
     * Database field that contains the time in millisecond when this page was last updated from server.
     * The value must be non-zero and positive.
     */
    @ColumnInfo(name = PAGE_UPDATE_TIME)
    var updateMills: Long = 0
        set(value) {
            if (value <= 0) throw IllegalArgumentException("Invalid update time.")
            field = value
        }

    companion object {

        // Database table name
        const val PAGES_TABLE = "pages_table"

        // Database column names.
        const val PAGE_ID = "page_id"
        const val PAGE_NAME = "page_name"
        const val PAGE_DESCRIPTION = "page_description"
        const val PAGE_UPDATE_TIME = "update_time"
    }
}