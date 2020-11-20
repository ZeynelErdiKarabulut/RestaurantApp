

package com.zeynelerdi.pastryshop.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.zeynelerdi.pastryshop.bin.Pages

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Application database. This database contains one table - [Pages].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Database(entities = [Pages::class], version = PSDatabase.DB_VERSION, exportSchema = true)
@TypeConverters(ImageListConverter::class)
abstract class PSDatabase : RoomDatabase() {

    companion object {
        //Database configs
        /**
         * Database name.
         */
        const val DB_NAME = "ps_db"

        /**
         * Database version.
         */
        const val DB_VERSION = 1
    }

    abstract fun pagesDao(): PagesDao
}