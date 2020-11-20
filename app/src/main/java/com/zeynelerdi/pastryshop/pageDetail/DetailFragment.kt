

package com.zeynelerdi.pastryshop.pageDetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeynelerdi.pastryshop.R
import com.zeynelerdi.pastryshop.di.DaggerAppDiComponent
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.pastryshop.utils.showSnack
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass to display the page details.
 *
 */
class DetailFragment : Fragment() {

    private lateinit var model: DetailViewModel

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private var pageId: Long = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        DaggerAppDiComponent.builder()
                .rootComponent(BaseApplication.getRootComponent(context!!))
                .build()
                .inject(this@DetailFragment)
        model = ViewModelProviders
                .of(this@DetailFragment, viewModelProvider)
                .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        parseArguments()

        //Set the transition names.
        ViewCompat.setTransitionName(rootView.detail_title_tv,
                "${getString(R.string.transition_name_home_card_title)}_$pageId")
        ViewCompat.setTransitionName(rootView.detail_description_tv,
                "${getString(R.string.transition_name_home_card_description)}_$pageId")
        ViewCompat.setTransitionName(rootView.detail_images_flipper,
                "${getString(R.string.transition_name_home_card_image)}_$pageId")
        ViewCompat.setTransitionName(rootView.details_card,
                "${getString(R.string.transition_name_home_card_view)}_$pageId")
        ViewCompat.setTransitionName(rootView.detail_close_btn,
                "${getString(R.string.transition_name_close_button)}_$pageId")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.errorLoadingPage.observe(this@DetailFragment, Observer {
            it?.let { showSnack(it) }
        })
        model.name.observe(this@DetailFragment, Observer {
            it?.let { detail_title_tv.text = it }
        })
        model.description.observe(this@DetailFragment, Observer {
            it?.let { detail_description_tv.text = it }
        })

        // Set images
        val adapter = ImagePagerAdapter(context!!, model.image.value!!)
        detail_images_flipper.adapter = adapter

        model.image.observe(this@DetailFragment, Observer {
            it?.let {
                if (it.isEmpty()) return@let

                // Update the image slider
                adapter.notifyDataSetChanged()

                // Update the bottom image
                Picasso.get()
                        .load(it[0])
                        .noFade()
                        .placeholder(R.drawable.ic_placeholder)
                        .into(detail_second_image_iv)
            }
        })

        // Set indicators
        detail_images_indicator.setViewPager(detail_images_flipper)
        adapter.registerDataSetObserver(detail_images_indicator.dataSetObserver)

        // Close button
        detail_close_btn.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }

        // Start observing for the changes,
        model.observePage(pageId)
    }

    /**
     * Parse and validate the arguments received in the fragment.
     */
    private fun parseArguments() {
        when {
            arguments == null -> throw IllegalArgumentException("No argument passed.")
            !arguments!!.containsKey(ARG_ID) -> throw IllegalArgumentException("No images passed.")
            !arguments!!.containsKey(ARG_IMAGES) -> throw IllegalArgumentException("No images passed.")
            !arguments!!.containsKey(ARG_DESCRIPTION) -> throw IllegalArgumentException("No page title passed.")
            !arguments!!.containsKey(ARG_NAME) -> throw IllegalArgumentException("No page description passed.")
            else -> {
                pageId = arguments!!.getLong(ARG_ID)
                model.name.value = arguments!!.getString(ARG_NAME)
                model.description.value = arguments!!.getString(ARG_DESCRIPTION)
                model.image.value = arguments!!.getStringArrayList(ARG_IMAGES)
            }
        }
    }

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_NAME = "name"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGES = "images"

        /**
         * Static factory method to get new instance of the [DetailFragment] class. Make [uesTransition]
         * false if you don't want to use shared element transitions between fragments (Useful in tests).
         */
        internal fun newInstance(
                context: Context,
                id: Long,
                name: String,
                description: String,
                images: ArrayList<String>,
                uesTransition: Boolean = true
        ): DetailFragment {

            return DetailFragment().apply {
                retainInstance = true

                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                    putString(ARG_NAME, name)
                    putString(ARG_DESCRIPTION, description)
                    putStringArrayList(ARG_IMAGES, images)
                }

                //Enter animation
                if (uesTransition) {
                    sharedElementEnterTransition = TransitionInflater.from(context)
                            .inflateTransition(R.transition.detail_transition)
                    enterTransition = TransitionInflater.from(context)
                            .inflateTransition(android.R.transition.fade)
                }
            }
        }
    }
}
