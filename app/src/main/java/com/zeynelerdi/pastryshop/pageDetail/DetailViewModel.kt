

package com.zeynelerdi.pastryshop.pageDetail

import android.arch.lifecycle.MutableLiveData
import com.zeynelerdi.pastryshop.bin.Pages
import com.zeynelerdi.pastryshop.repository.Repository
import com.zeynelerdi.pastryshop.utils.BaseViewModel
import com.zeynelerdi.pastryshop.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 * View model for the [DetailFragment].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class DetailViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    /**
     * [MutableLiveData] of the name/title of the page.
     */
    internal val name = MutableLiveData<String>()

    /**
     * [MutableLiveData] for the description test of the page.
     */
    internal val description = MutableLiveData<String>()

    /**
     * [MutableLiveData] for the list of images urls.
     */
    internal val image = MutableLiveData<ArrayList<String>>()

    /**
     * [SingleLiveEvent] which contains the message for the error occurred while loading the page detail.
     */
    internal val errorLoadingPage = SingleLiveEvent<String>()

    init {
        // Initialize
        image.value = ArrayList()
    }

    /**
     * Observe the [Pages] with [pageId] for any changes in the database.
     */
    internal fun observePage(pageId: Long) {
        val d = repository.getPage(pageId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    // Some changes occurred into the database
                    // Rebuild the pages list.
                    name.value = it.title
                    description.value = it.description
                    image.value = it.image
                }, {

                    // Error occurred
                    errorLoadingPage.value = it.message
                })

        addDisposable(d)
    }
}