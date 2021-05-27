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

package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.autoFormatDurationMsAsSmallestHhMmSsString
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleErrorMessage
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import org.koin.android.ext.android.get

abstract class AbstractSessionPresetDialog: DialogFragment() {

    private val LOG_TAG = AbstractSessionPresetDialog::class.java.name

    private val logger: Logger = get()
    private var listener: SessionPresetDialogListener? = null

    private val DISABLED_ZONE_ALPHA = 0.5f
    private val ENABLED_ZONE_ALPHA = 1f

    abstract fun getSessionPresetViewModel(): AbstractSessionPresetViewModel
    abstract fun getTitleField(): TextView?
    abstract fun getDismissButton(): ImageView?
    abstract fun getValidateButton(): ImageView?
    abstract fun getDeleteButton(): MaterialButton?
    abstract fun getVibrationSwitch(): SwitchMaterial?
    abstract fun getVibrationZone(): ViewGroup?
    abstract fun getCountdownZone(): ViewGroup?
    abstract fun getCountdownLengthValue(): TextView?
    abstract fun getDurationZone(): ViewGroup?
    abstract fun getDurationValue(): TextView?
    abstract fun getStartEndSoundZone(): ViewGroup?
    abstract fun getStartEndSoundValue(): TextView?

    fun setSessionPresetDialogListener(listener: SessionPresetDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        return dialog
    }

    //////////////////////////
    // static UI elements initialization
    protected fun initUi() {
        setUpTitle()
        setUpDismissButton()
        setUpValidateButton()
        setUpDeleteButton()
    }

    private fun setUpTitle() {
        val titleField = getTitleField()
        val sessionPreset = getSessionPresetViewModel().sessionPresetUpdatedLiveData.value
        val isCreation = (sessionPreset == null || sessionPreset.id == -1L)
        titleField?.text = if (isCreation) {
            getString(R.string.session_preset_creation_dialog_title)
        }
        else {
            getString(R.string.session_preset_edition_dialog_title)
        }
    }

    private fun setUpDismissButton() {
        val dismissButton = getDismissButton()
        dismissButton?.setOnClickListener {
            listener?.onDismissButtonClicked()
            dismiss()
        }
    }

    private fun setUpValidateButton() {
        val validateButton = getValidateButton()
        validateButton?.setOnClickListener {
            if (getSessionPresetViewModel().isSessionPresetValid()) {
                val sessionPresetToSave = getSessionPresetViewModel().sessionPresetUpdatedLiveData.value
                if (sessionPresetToSave != null) {
                    listener?.onConfirmButtonClicked(sessionPresetToSave)
                    dismiss()
                }
                else {
                    logger.e(
                        LOG_TAG,
                        "validateButton::onClick:: no SessionPreset found to save"
                    )
                }
            }
            else {
                val presetInvalidDialog = BottomDialogDismissibleErrorMessage.newInstance(
                    getString(R.string.generic_string_ERROR),
                    getString(R.string.session_preset_invalid_preset)
                )
                presetInvalidDialog.show(
                    childFragmentManager,
                    "openPresetInvalidDialog"
                )
            }
        }
    }

    private fun setUpDeleteButton() {
        val deleteButton = getDeleteButton()
        val sessionPreset = getSessionPresetViewModel().sessionPresetUpdatedLiveData.value
        if (sessionPreset == null || sessionPreset.id == -1L) {
            deleteButton?.visibility = INVISIBLE
        }
        else {
            deleteButton?.visibility = VISIBLE
            deleteButton?.setOnClickListener {
                val confirmDeleteDialog = BottomDialogDismissibleBigButton.newInstance(
                    getString(R.string.generic_string_WARNING),
                    getString(R.string.generic_string_DELETE)
                )
                confirmDeleteDialog.setBottomDialogDismissibleBigButtonListener {
                    listener?.onDeletePresetConfirmed(sessionPreset)
                    confirmDeleteDialog.dismiss()
                    dismiss()
                }
                confirmDeleteDialog.show(
                    childFragmentManager,
                    "openConfirmDeletePresetDialog"
                )
            }
        }
    }

    //////////////////////////
    //  COMMON UI elements update

    protected fun updateCommonUi(sessionPreset: SessionPreset) {
        updateCountDownZone(sessionPreset)
        updateStartEndSoundZone(sessionPreset)
        updateVibrationZone(sessionPreset)
    }

    private fun updateStartEndSoundZone(sessionPreset: SessionPreset) {
        getStartEndSoundValue()?.text = sessionPreset.startAndEndSoundName
        getStartEndSoundZone()?.setOnClickListener {
            showBottomRingtonePicker(sessionPreset)
        }
    }

    private fun showBottomRingtonePicker(sessionPreset: SessionPreset) {
        val ringTonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesRawAssetsNames)
        val ringTonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
        val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
            title = getString(R.string.start_end_sound),
            initialSelectionUri = sessionPreset.startAndEndSoundUriString,
            confirmButtonLabel = getString(R.string.generic_string_OK),
            showSilenceChoice = true,
            ringTonesAssetsNames = ringTonesAssets,
            ringTonesDisplayNames = ringTonesTitles
        )
        soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener {selectedRingtoneUri, selectedRingtoneName ->
            getSessionPresetViewModel().updatePresetStartAndEndSoundUriString(selectedRingtoneUri)
            getSessionPresetViewModel().updatePresetStartAndEndSoundName(selectedRingtoneName)
        }
        soundPickerDialog.show(
            childFragmentManager,
            "openStartEndSoundPickerDialog"
        )
    }

    private fun updateVibrationZone(sessionPreset: SessionPreset) {
        getVibrationZone()?.setOnClickListener(null) // unregister listener to avoid OnCheckedChangeListener trigger when updating value
        getVibrationSwitch()?.isChecked = sessionPreset.vibration
        // reset listener
        getVibrationZone()?.setOnClickListener {
            getVibrationSwitch()?.toggle()
        }
        getVibrationSwitch()?.setOnCheckedChangeListener {_, p1 ->
            getSessionPresetViewModel().updatePresetVibration(p1)
        }
    }

    private fun updateCountDownZone(sessionPreset: SessionPreset) {
        getCountdownLengthValue()?.text = formatDurationMsToString(sessionPreset.startCountdownLength)
        getCountdownZone()?.setOnClickListener {
            showBottomCountDownPicker(sessionPreset)
        }
    }

    private fun showBottomCountDownPicker(sessionPreset: SessionPreset) {
        val countdownDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
            title = getString(R.string.countdown),
            showHours = false,
            showMinutes = false,
            showSeconds = true,
            explanationMessage = getString(R.string.session_countdown_explanation),
            confirmButtonLabel = getString(R.string.generic_string_OK),
            initialLengthMs = sessionPreset.startCountdownLength
        )
        countdownDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
            getCountdownLengthValue()?.text = formatDurationMsToString(lengthMs)
            getSessionPresetViewModel().updatePresetStartCountdownLength(lengthMs)
        }
        countdownDialog.show(
            childFragmentManager,
            "openCountDownDialog"
        )
    }

    protected fun updateDurationZone(sessionPreset: SessionPreset) {
        val durationValueNormalColor = MaterialColors.getColor(
            requireContext(),
            R.attr.colorOnSurface,
            Color.BLACK
        )
        val durationZone = getDurationZone()
        val durationValue = getDurationValue()
        if (durationZone == null || durationValue == null) return
        durationValue.setTextColor(durationValueNormalColor)
        val isAnAudioSession = sessionPreset is SessionPreset.AudioSessionPreset
        val audioSessionDurationUnknown = isAnAudioSession && sessionPreset.duration == -1L
        if (isAnAudioSession) {
            if (audioSessionDurationUnknown) { // we have an audio file for the session, but could not retrieve its duration through its metadata => we need the user to input it manually
                durationZone.alpha = ENABLED_ZONE_ALPHA
                durationValue.text = getString(R.string.unknown_audio_duration_fill_manually)
                val errorColor = ContextCompat.getColor(
                    requireContext(),
                    R.color.red_600
                )
                durationValue.setTextColor(errorColor)
                durationZone.setOnClickListener {
                    showBottomDurationSelector(0L)
                }
            }
            else { // duration field is filled with duration retrieved from audio file metadata, and interaction is deactivated
                durationZone.alpha = DISABLED_ZONE_ALPHA
                durationValue.text = formatDurationMsToString(sessionPreset.duration)
                durationZone.setOnClickListener {
                    Toast.makeText(
                        context,
                        getString(R.string.session_duration_vs_audio_explanation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        else { // timed session:
            if (sessionPreset.intermediateIntervalRandom) {//a random interval has been activated: duration is useless so disabled
                durationZone.alpha = DISABLED_ZONE_ALPHA
                durationValue.text = getString(R.string.interval_random)
                durationZone.setOnClickListener {
                    Toast.makeText(
                        context,
                        getString(R.string.session_duration_vs_audio_explanation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {//duration is pre-filled with input value, or default duration, interaction is active
                durationZone.alpha = ENABLED_ZONE_ALPHA
                durationValue.text = formatDurationMsToString(sessionPreset.duration)
                durationZone.setOnClickListener {
                    showBottomDurationSelector(sessionPreset.duration)
                }
            }
        }
    }

    private fun showBottomDurationSelector(initialDuration: Long) {
        val durationDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
            title = getString(R.string.duration),
            showHours = true,
            showMinutes = true,
            showSeconds = true,
            explanationMessage = getString(R.string.session_duration_vs_audio_explanation),
            confirmButtonLabel = getString(R.string.generic_string_OK),
            initialLengthMs = initialDuration
        )
        durationDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
            getDurationValue()?.text = formatDurationMsToString(lengthMs)
            getSessionPresetViewModel().updatePresetDuration(lengthMs)
        }
        durationDialog.show(
            childFragmentManager,
            "openDurationDialog"
        )
    }

    //TODO: remove field uiBindings and replace with adequate getter methods for Views
    protected fun updateIntervalSoundZone(
        sessionPreset: SessionPreset,
        uiBindings: DialogSessionPresetBinding
    ) {
        if (sessionPreset.intermediateIntervalSoundName.isBlank()) { // this is an audio session
            uiBindings.intervalSoundZone.alpha = DISABLED_ZONE_ALPHA
            uiBindings.intervalSoundValue.text = getString(R.string.generic_string_NONE)
            uiBindings.intervalSoundZone.setOnClickListener {
                Toast.makeText(
                    context,
                    getString(R.string.session_interval_vs_audio_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else {
            uiBindings.intervalSoundZone.alpha = ENABLED_ZONE_ALPHA
            uiBindings.intervalSoundValue.text = sessionPreset.intermediateIntervalSoundName
            val ringTonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesRawAssetsNames)
            val ringTonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
            uiBindings.intervalSoundZone.setOnClickListener {
                val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
                    title = getString(R.string.interval_sound),
                    initialSelectionUri = sessionPreset.intermediateIntervalSoundUriString,
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    showSilenceChoice = true,
                    ringTonesAssetsNames = ringTonesAssets,
                    ringTonesDisplayNames = ringTonesTitles
                )
                soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener {selectedRingtoneUri, selectedRingtoneName ->
                    getSessionPresetViewModel().updatePresetIntermediateIntervalSoundUriString(selectedRingtoneUri)
                    getSessionPresetViewModel().updatePresetIntermediateIntervalSoundName(selectedRingtoneName)
                }
                soundPickerDialog.show(
                    childFragmentManager,
                    "openIntervalSoundPickerDialog"
                )
            }
        }
    }

    /////////////////
    private fun formatDurationMsToString(duration: Long): String {
        return duration.autoFormatDurationMsAsSmallestHhMmSsString(
            resources.getString(R.string.duration_format_hours_minutes_seconds_short),
            resources.getString(R.string.duration_format_hours_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_hours_no_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_minutes_seconds_short),
            resources.getString(R.string.duration_format_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_seconds_short)
        )
    }
}