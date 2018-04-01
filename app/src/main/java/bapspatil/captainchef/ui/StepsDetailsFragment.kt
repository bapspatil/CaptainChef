package bapspatil.captainchef.ui


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import bapspatil.captainchef.R
import bapspatil.captainchef.model.RecipeStep
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class StepsDetailsFragment : Fragment() {
    @BindView(R.id.step_description_tv)
    internal var mStepDescription: TextView? = null
    @BindView(R.id.video_exoplayer_view)
    internal var mPlayerView: SimpleExoPlayerView? = null
    @BindView(R.id.next_button)
    internal var nextButton: Button? = null
    @BindView(R.id.prev_button)
    internal var prevButton: Button? = null
    @BindView(R.id.next_card_view)
    internal var nextCardView: CardView? = null
    @BindView(R.id.prev_card_view)
    internal var prevCardView: CardView? = null
    private var mPlayer: SimpleExoPlayer? = null
    private var unbinder: Unbinder? = null
    private var recipeStep: RecipeStep? = null
    private var position: Long = -1
    private var videoUri: Uri? = null
    private var recipeStepsList: ArrayList<RecipeStep>? = null
    lateinit var mButtonListener: OnButtonClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_steps_details, container, false)
        unbinder = ButterKnife.bind(this, rootView)

        if (savedInstanceState != null)
            position = savedInstanceState.getLong("SAVED_POSITION")
        recipeStep = arguments!!.getParcelable("recipeStep")
        recipeStepsList = arguments!!.getParcelableArrayList("recipeList")
        if (recipeStep != null) {
            mStepDescription!!.text = recipeStep!!.info
            if (recipeStep!!.videoUrl == "" || recipeStep!!.videoUrl == null) {
                mPlayerView!!.visibility = View.GONE
            } else {
                getPlayer()
            }
            when {
                recipeStep!!.stepId == 0 -> {
                    prevButton!!.visibility = View.INVISIBLE
                    prevCardView!!.visibility = View.INVISIBLE
                }
                recipeStep!!.stepId == recipeStepsList!!.size - 1 -> {
                    nextButton!!.visibility = View.INVISIBLE
                    nextCardView!!.visibility = View.INVISIBLE
                }
                else -> {
                    prevButton!!.visibility = View.VISIBLE
                    nextButton!!.visibility = View.VISIBLE
                    prevCardView!!.visibility = View.VISIBLE
                    nextCardView!!.visibility = View.VISIBLE
                }
            }
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }


    override fun onPause() {
        super.onPause()
        if (mPlayer != null) {
            position = mPlayer!!.currentPosition
            mPlayer!!.stop()
            mPlayer!!.release()
            mPlayer = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (mPlayer != null) {
            if (position.toInt() != -1) mPlayer!!.seekTo(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("SAVED_POSITION", position)
    }

    @OnClick(R.id.prev_button)
    internal fun prevStep(view: View) {
        mButtonListener.onButtonClicked(PREV_BUTTON, recipeStep, recipeStepsList, view)
    }

    @OnClick(R.id.next_button)
    internal fun nextStep(view: View) {
        mButtonListener.onButtonClicked(NEXT_BUTTON, recipeStep, recipeStepsList, view)
    }

    private fun getPlayer() {
        val mainHandler = Handler()
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        mPlayerView!!.useController = true
        mPlayerView!!.requestFocus()
        mPlayerView!!.player = mPlayer
        // Measures bandwidth during playback. Can be null if not required.
        val defaultBandwidthMeter = DefaultBandwidthMeter()
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory = DefaultDataSourceFactory(context!!,
                Util.getUserAgent(context, "CaptainChef"), defaultBandwidthMeter)
        // Produces Extractor instances for parsing the media data.
        val extractorsFactory = DefaultExtractorsFactory()
        // This is the MediaSource representing the media to be played.
        videoUri = Uri.parse(recipeStep!!.videoUrl)
        val videoSource = ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null)
        // Prepare the player with the source.
        if (position.toInt() != -1)
            mPlayer!!.seekTo(position)
        mPlayer!!.prepare(videoSource)
        mPlayer!!.playWhenReady = true
    }

    interface OnButtonClickListener {
        fun onButtonClicked(buttonClicked: Int, recipeStep: RecipeStep?, recipeSteps: ArrayList<RecipeStep>?, view: View)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mButtonListener = context as OnButtonClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement OnButtonClickListener")
        }

    }

    companion object {

        val PREV_BUTTON = 0
        val NEXT_BUTTON = 1

        fun newInstance(recipeStep: RecipeStep, recipeStepArrayList: ArrayList<RecipeStep>): StepsDetailsFragment {
            val stepsDetailsFragment = StepsDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable("recipeStep", recipeStep)
            bundle.putParcelableArrayList("recipeList", recipeStepArrayList)
            stepsDetailsFragment.arguments = bundle
            return stepsDetailsFragment
        }
    }

}// Required empty public constructor
