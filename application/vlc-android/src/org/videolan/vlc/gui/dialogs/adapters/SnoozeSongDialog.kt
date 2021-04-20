/*
 * *************************************************************************
 *  SleepTimerDialog.java
 * **************************************************************************
 *  Copyright © 2015 VLC authors and VideoLAN
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *  ***************************************************************************
 */

package org.videolan.vlc.gui.dialogs.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.Runnable
import org.videolan.vlc.R
import org.videolan.vlc.gui.dialogs.PickSnoozeTimeFragment
import org.videolan.vlc.gui.helpers.PlayerOptionsDelegate
import org.videolan.vlc.gui.helpers.UiTools

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SnoozeSongDialog() : PickSnoozeTimeFragment() {

    // FIXME - fix double gray background drop - CS 356

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        maxTimeSize = 8
        return view
    }

    override fun executeAction(action: String) {
        var intervalMsg = action; // fixme - cs 356 - check for Custom... and add functionality
        /* TODO - KEEP FOR CUSTOM...
        if (days != "") {
            val days = java.lang.Long.parseLong(days) * DAYS_IN_MICROS
            intervalMsg = (days/DAYS_IN_MICROS).toString() + "d "
        }
        if (hours != "") {
            val hours = java.lang.Long.parseLong(hours) * HOURS_IN_MICROS
            intervalMsg = intervalMsg + (hours/HOURS_IN_MICROS).toString() + "d "
        }
        if (minutes != "") {
            val minutes = java.lang.Long.parseLong(minutes) * MINUTES_IN_MICROS
            intervalMsg = intervalMsg + (minutes/MINUTES_IN_MICROS).toString() + "h "
        }*/
        val message = String.format(getString(R.string.snooze_item), SONG_TITLE, intervalMsg)
        UiTools.snackerWithCancel(requireActivity(), message, null, CANCEL_ACTION)

        dismiss()
    }

    override fun showDeleteCurrent(): Boolean {
        return PlayerOptionsDelegate.playerSleepTime != null
    }

    override fun onClick(v: View) {
        if (v.id == R.id.tim_pic_delete_current) {
            //requireActivity().setSleep(null) // fixme, remove later
            dismiss()
        } else super.onClick(v)
    }

    override fun getTitle(): Int {
        return R.string.snooze_time
    }

    companion object {

        private var ONE_DAY_IN_MILLIS = (24 * 60 * 60 * 1000).toLong()
        private var SONG_TITLE = ""
        var CANCEL_ACTION = Runnable {}

        fun newInstance(title: String, cancelAction: Runnable) = SnoozeSongDialog().apply {
            SONG_TITLE = title
            CANCEL_ACTION = cancelAction
        }
    }
}
