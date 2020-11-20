

package com.zeynelerdi.pastryshop.repository.db

import com.zeynelerdi.pastryshop.bin.Pages
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class MockPagesDao(val tableItems: ArrayList<Pages>) : PagesDao {

    override fun insert(pages: Pages): Long {
        tableItems.add(pages)
        return tableItems.lastIndex.toLong()
    }

    override fun update(pages: Pages): Int {
        val numberOfUpdatedItem = 0

        (0 until tableItems.size)
                .filter { it -> tableItems[it].id == pages.id }
                .forEach { it ->
                    numberOfUpdatedItem.inc()
                    tableItems[it] = pages
                }
        return numberOfUpdatedItem
    }

    override fun getCount(): Single<Int> {
        return Single.create { it.onSuccess(tableItems.size) }
    }

    override fun getAllPages(): Flowable<List<Pages>> {
        return Flowable.create({
            it.onNext(tableItems)

            // We won't call onComplete().
            // Because in real room database, it is never ending stream of data.
        }, BackpressureStrategy.LATEST)
    }

    override fun getPageFromId(id: Long): Pages? {
        tableItems.filter { it.id == id }
                .forEach {
                    return it
                }

        return null
    }

    override fun observePage(id: Long): Flowable<Pages> {
        return Flowable.create({
            val emitter = it

            tableItems.filter { it.id == id }
                    .forEach { emitter.onNext(it) }

            // We won't call onComplete().
            // Because in real room database, it is never ending stream of data.
        }, BackpressureStrategy.LATEST)
    }

    override fun hasPage(id: Long): Single<Int> {
        return Single.create {
            val emitter = it

            var isFound = false
            tableItems.filter { it.id == id }
                    .forEach { isFound = true }

            emitter.onSuccess(if (isFound) 1 else 0)
        }
    }

    override fun deleteNotUpdatedPages(updateTime: Long) {
        val updateCards = ArrayList<Pages>()

        tableItems.filter { it.updateMills < updateTime }.forEach { updateCards.add(it) }

        updateCards.forEach { tableItems.remove(it) }
    }

    override fun nukeTable() {
        tableItems.clear()
    }

}