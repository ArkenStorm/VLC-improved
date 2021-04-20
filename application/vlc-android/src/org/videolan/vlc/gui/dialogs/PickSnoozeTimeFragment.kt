/**
 * **************************************************************************
 * PickTimeFragment.java
 * ****************************************************************************
 * Copyright © 2015 VLC authors and VideoLAN
 * Author: Geoffrey Métais
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 * ***************************************************************************
 */
package org.videolan.vlc.gui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import org.videolan.vlc.PlaybackService
import org.videolan.vlc.R
import org.videolan.vlc.util.launchWhenStarted

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class PickSnoozeTimeFragment : VLCBottomSheetDialogFragment(), View.OnClickListener, View.OnFocusChangeListener {

    private var mTextColor: Int = 0

    var days = ""
    var hours = ""
    var minutes = ""
    private var formatTime = ""
    private var pickedRawTime = ""
    var maxTimeSize = 6
    private lateinit var tvTimeToJump: TextView

    lateinit var playbackService: PlaybackService

    abstract fun getTitle(): Int

    open fun showDeleteCurrent() = false

    override fun getDefaultState(): Int {
        return STATE_EXPANDED
    }

    override fun needToManageOrientation(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_snooze_time_picker, container)

        (view.findViewById<View>(R.id.tim_pic_title) as TextView).setText(getTitle())

        view.findViewById<View>(R.id.tim_pic_day).setOnClickListener(this)
        view.findViewById<View>(R.id.tim_pic_day).onFocusChangeListener = this
        view.findViewById<View>(R.id.tim_pic_week).setOnClickListener(this)
        view.findViewById<View>(R.id.tim_pic_week).onFocusChangeListener = this
        view.findViewById<View>(R.id.tim_pic_month).setOnClickListener(this)
        view.findViewById<View>(R.id.tim_pic_month).onFocusChangeListener = this
        view.findViewById<View>(R.id.tim_pic_custom).setOnClickListener(this)
        view.findViewById<View>(R.id.tim_pic_custom).onFocusChangeListener = this

        return view
    }

    override fun initialFocusedView(): View {
        return view!!.findViewById(R.id.tim_pic_day)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PlaybackService.serviceFlow.filterNotNull().onEach { playbackService = it }.launchWhenStarted(lifecycleScope)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (v is TextView) {
            v.setTextColor(if (hasFocus) ContextCompat.getColor(requireActivity(), R.color.orange500) else mTextColor)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tim_pic_day -> executeAction("1 day")
            R.id.tim_pic_week -> executeAction("1 week")
            R.id.tim_pic_month -> executeAction("1 month")
            R.id.tim_pic_custom -> executeAction("custom")
            //R.id.tim_pic_ok -> executeAction()
        }
    }

    protected abstract fun executeAction(action: String)

    companion object {

        val TAG = "VLC/PickSnoozeTimeFragment"

        const val MILLIS_IN_MICROS: Long = 1000
        const val SECONDS_IN_MICROS = 1000 * MILLIS_IN_MICROS
        const val MINUTES_IN_MICROS = 60 * SECONDS_IN_MICROS
        const val HOURS_IN_MICROS = 60 * MINUTES_IN_MICROS
        const val DAYS_IN_MICROS = 24 * HOURS_IN_MICROS
    }
}