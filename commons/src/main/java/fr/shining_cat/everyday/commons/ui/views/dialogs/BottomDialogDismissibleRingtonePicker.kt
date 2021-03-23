/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import fr.shining_cat.everyday.commons.databinding.DialogBottomRingtonePickerAndConfirmBinding
import org.koin.android.ext.android.get
import java.security.InvalidParameterException

class BottomDialogDismissibleRingtonePicker : BottomSheetDialogFragment() {

    private val LOG_TAG = BottomDialogDismissibleRingtonePicker::class.java.name
    private val logger: Logger = get()

    private val TITLE_ARG = "title_argument"
    private val INITIAL_SELECTION_URI_ARG = "initial_selection_uri_argument"
    private val SILENCE_ARG = "silence_argument"
    private val RINGTONES_ASSETS_ARG = "ringTones_assets_argument"
    private val RINGTONES_TITLES_ARG = "ringTones_titles_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var listener: BottomDialogDismissibleRingtonePickerListener? = null

    private var playingRingtone: Ringtone? = null

    override fun onDestroy() {
        super.onDestroy()
        playingRingtone?.stop()
    }

    fun interface BottomDialogDismissibleRingtonePickerListener {
        fun onValidateRingtoneSelected(
            selectedRingtoneUri: String,
            selectedRingtoneName: String
        )
    }

    fun setBottomDialogDismissibleRingtonePickerListener(
        listener: BottomDialogDismissibleRingtonePickerListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            initialSelectionUri: String,
            confirmButtonLabel: String,
            showSilenceChoice: Boolean = false,
            ringTonesAssetsNames: Array<String>? = emptyArray(),
            ringTonesDisplayNames: Array<String>? = emptyArray()
        ): BottomDialogDismissibleRingtonePicker = BottomDialogDismissibleRingtonePicker().apply {
            arguments = Bundle().apply {
                putString(
                    TITLE_ARG,
                    title
                )
                putString(
                    INITIAL_SELECTION_URI_ARG,
                    initialSelectionUri
                )
                putString(
                    CONFIRM_BUTTON_LABEL_ARG,
                    confirmButtonLabel
                )
                putBoolean(
                    SILENCE_ARG,
                    showSilenceChoice
                )
                putStringArray(
                    RINGTONES_ASSETS_ARG,
                    ringTonesAssetsNames
                )
                putStringArray(
                    RINGTONES_TITLES_ARG,
                    ringTonesDisplayNames
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomRingtonePickerAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomRingtonePickerAndConfirmBinding) {
        val title = arguments?.getString(
            TITLE_ARG,
            ""
        ) ?: ""
        val titleField = uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomTitle
        titleField.text = title
        //
        val dismissButton = uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomDismissButton
        dismissButton.setOnClickListener {
            dismiss()
        }
        //
        val completeRingTonesList = buildCompleteRingTonesMap()
        val completeRingTonesLabels: List<String> = completeRingTonesList.map { it.first }
        val completeRingTonesUris: List<Uri> = completeRingTonesList.map { it.second }
        val initialSelectionUriString = arguments?.getString(
            INITIAL_SELECTION_URI_ARG,
            ""
        ) ?: ""
        if (initialSelectionUriString.isBlank() && arguments?.getBoolean(SILENCE_ARG) == false) {
            logger.e(LOG_TAG, "initUi::initial selection is silence but silence is not allowed, this should never happen!")
            throw InvalidParameterException("initial selection is silence but silence is not allowed")
        }
        val selectListAdapter = SelectListAdapter(logger)
        selectListAdapter.optionsLabels = completeRingTonesLabels
        selectListAdapter.forceInitialSelectedOption(
            completeRingTonesUris.indexOf(
                Uri.parse(
                    initialSelectionUriString
                )
            )
        )
        val selectListRecycler = uiBindings.dialogBottomRecycler
        selectListRecycler.adapter = selectListAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        selectListRecycler.layoutManager = layoutManager
        //
        selectListAdapter.setSelectListAdapterListener(object : SelectListAdapter.SelectListAdapterListener {
            override fun onOptionSelected(selectedPosition: Int) {
                playSelectedRingtone(completeRingTonesUris[selectedPosition])
            }
        })
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener {
            transmitSelectedRingtone(completeRingTonesList[selectListAdapter.selectedPosition])
        }
        // preventing disturbing dialog size-changes when scrolling list by setting peek height to expanded (full) height
        this.dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
        }
    }

    private fun playSelectedRingtone(ringtoneToPlayUri: Uri) {
        playingRingtone?.stop()
        if (ringtoneToPlayUri.toString().isNotBlank()) {
            playingRingtone = RingtoneManager.getRingtone(
                context,
                ringtoneToPlayUri
            )
            playingRingtone?.play()
        }
    }

    private fun buildCompleteRingTonesMap(): List<Pair<String, Uri>> {
        val silenceList = if (arguments?.getBoolean(SILENCE_ARG) == true) {
            getSilentRingtone()
        } else {
            listOf()
        }
        //
        val ringTonesAssetsNames = arguments?.getStringArray(RINGTONES_ASSETS_ARG) ?: emptyArray()
        val ringTonesDisplayNames = arguments?.getStringArray(RINGTONES_TITLES_ARG) ?: emptyArray()
        val ringTonesAssetsList = if (ringTonesAssetsNames.size == ringTonesDisplayNames.size) {
            getAssetsRingTones(
                ringTonesAssetsNames,
                ringTonesDisplayNames
            )
        } else {
            logger.e(
                LOG_TAG,
                "buildCompleteRingTonesMap:: incompatible sources sizes"
            )
            listOf()
        }
        //
        val deviceRingTonesList = getDeviceRingTones()
        //
        val list: MutableList<Pair<String, Uri>> = mutableListOf()
        list.addAll(silenceList)
        list.addAll(ringTonesAssetsList)
        list.addAll(deviceRingTonesList)
        return list
    }

    private fun getSilentRingtone(): List<Pair<String, Uri>> = listOf(
        Pair(
            getString(R.string.silence),
            Uri.parse("")
        ),
        selectListDividerItem
    )

    private val selectListDividerItem = Pair(
        "",
        Uri.parse("")
    )

    private fun getDeviceRingTones(): List<Pair<String, Uri>> {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION)
        val cursor = ringtoneManager.cursor
        val list: MutableList<Pair<String, Uri>> = mutableListOf()
        while (cursor.moveToNext()) {
            val notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val notificationUri = ringtoneManager.getRingtoneUri(cursor.position)
            list.add(
                Pair(
                    notificationTitle,
                    notificationUri
                )
            )
        }
        return list
    }

    private fun getAssetsRingTones(
        ringTonesAssetsNames: Array<String>,
        ringTonesDisplayNames: Array<String>
    ): List<Pair<String, Uri>> {
        val list: MutableList<Pair<String, Uri>> = mutableListOf()
        for ((index, ringtoneAssetName) in ringTonesAssetsNames.withIndex()) {
            context?.let {
                list.add(
                    Pair(
                        ringTonesDisplayNames[index],
                        uriFromRaw(
                            it,
                            ringtoneAssetName
                        )
                    )
                )
            }
        }
        if (list.isNotEmpty()) list.add(selectListDividerItem)
        return list
    }

    private fun uriFromRaw(
        context: Context,
        assetName: String
    ): Uri {
        val resId = context.resources?.getIdentifier(
            assetName,
            "raw",
            context.packageName
        ) ?: -1
        return if (resId != -1) {
            Uri.parse("android.resource://" + context.packageName + "/" + resId)
        } else {
            Uri.parse("")
        }
    }

    private fun transmitSelectedRingtone(selectedRingtone: Pair<String, Uri>) {
        listener?.onValidateRingtoneSelected(
            selectedRingtoneUri = selectedRingtone.second.toString(),
            selectedRingtoneName = selectedRingtone.first
        )
        dismiss()
    }
}