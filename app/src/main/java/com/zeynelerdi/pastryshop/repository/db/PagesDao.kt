

package com.zeynelerdi.pastryshop.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.zeynelerdi.pastryshop.bin.Pages
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * [Dao] that contains database access methods to deal with [Pages] table.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Dao
interface PagesDao {

    /**
     * Insert new [Pages] to the table.
     */
    @Insert
    fun insert(pages: Pages): Long

    /**
     * Update existing [Pages].
     */
    @Update
    fun update(pages: Pages): Int

    /**
     * Get the total number of entries in the [Pages] table.
     */
    @Query("SELECT COUNT(*) FROM ${Pages.PAGES_TABLE}")
    fun getCount(): Single<Int>

    /**
     * Get the list of all the [Pages] from the table.
     */
    @Query("SELECT * FROM ${Pages.PAGES_TABLE}")
    fun getAllPages(): Flowable<List<Pages>>

    /**
     * Get information of the [Pages] with given [id].
     */
    @Query("SELECT * FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun getPageFromId(id: Long): Pages?

    /**
     * Observe the [Pages] with [id] for changes.
     */
    @Query("SELECT * FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun observePage(id: Long): Flowable<Pages>

    /**
     * Get the count of the row with [id] in [Pages] table.
     */
    @Query("SELECT COUNT(*) FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun hasPage(id: Long): Single<Int>

    /**
     * Delete the [Pages] entries which are updated before [updateTime].
     */
    @Query("DELETE FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_UPDATE_TIME}<:updateTime")
    fun deleteNotUpdatedPages(updateTime: Long)

    /**
     * Delete the [Pages] table.
     */
    @Query("DELETE FROM ${Pages.PAGES_TABLE}")
    fun nukeTable()
}