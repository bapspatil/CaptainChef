package bapspatil.captainchef.adapters

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by bapspatil
 */

class MyRecyclerView : RecyclerView {
    private var mLayoutManagerSavedState: Parcelable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(SAVED_SUPER_STATE, super.onSaveInstanceState())
        bundle.putParcelable(SAVED_LAYOUT_MANAGER, this.layoutManager.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var mState = state
        if (mState is Bundle) {
            val bundle = mState as Bundle?
            mLayoutManagerSavedState = bundle!!.getParcelable(SAVED_LAYOUT_MANAGER)
            mState = bundle.getParcelable(SAVED_SUPER_STATE)
        }
        super.onRestoreInstanceState(state)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        super.setAdapter(adapter)
        restorePosition()
    }

    /**
     * Restores scroll position after configuration change.
     * NOTE: Must be called after adapter has been set.
     */
    fun restorePosition() {
        if (mLayoutManagerSavedState != null) {
            this.layoutManager.onRestoreInstanceState(mLayoutManagerSavedState)
            mLayoutManagerSavedState = null
        }
    }

    companion object {
        private const val SAVED_SUPER_STATE = "super-state"
        private const val SAVED_LAYOUT_MANAGER = "layout-manager-state"
    }
}
