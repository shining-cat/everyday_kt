package fr.shining_cat.everyday.screens.views.home

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.color.MaterialColors
import fr.shining_cat.everyday.commons.Constants.Companion.ACTIVITY_RESULT_SELECT_AUDIO_FILE
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.autoFormatDurationMsAsSmallestHhMmSsString
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleErrorMessage
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetBinding
import fr.shining_cat.everyday.screens.viewmodels.SessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SessionPresetDialog: DialogFragment() {

    private val LOG_TAG = SessionPresetDialog::class.java.name

    private val sessionPresetViewModel: SessionPresetViewModel by viewModel()

    private val DISABLED_ZONE_ALPHA = 0.5f
    private val ENABLED_ZONE_ALPHA = 1f

    private val logger: Logger = get()
    private val PRESET_INPUT_ARG = "session preset to edit argument"
    private var listener: SessionPresetDialogListener? = null

    interface SessionPresetDialogListener {

        fun onDismissButtonClicked()
        fun onConfirmButtonClicked(sessionPreset: SessionPreset)
        fun onDeletePresetConfirmed(sessionPreset: SessionPreset)
    }

    fun setSessionPresetDialogListener(listener: SessionPresetDialogListener) {
        this.listener = listener
    }

    companion object {

        fun newInstance(preset: SessionPreset? = null): SessionPresetDialog = SessionPresetDialog().apply {
            arguments = Bundle().apply {
                if (preset != null) {
                    putParcelable(
                        PRESET_INPUT_ARG,
                        preset
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sessionPresetDialogBinding = DialogSessionPresetBinding.inflate(LayoutInflater.from(context))
        //
        sessionPresetViewModel.sessionPresetUpdatedLiveData.observe(viewLifecycleOwner,
            {
                updateUi(
                    it,
                    sessionPresetDialogBinding
                )
            })
        //
        val presetInput = arguments?.getParcelable(PRESET_INPUT_ARG) as SessionPreset?
        sessionPresetViewModel.init(
            requireContext(),
            presetInput
        )
        initUi(sessionPresetDialogBinding)
        return sessionPresetDialogBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        return dialog
    }

    //////////////////////////
    //  UI elements initialization
    private fun initUi(uiBindings: DialogSessionPresetBinding) {
        setUpTitle(uiBindings)
        setUpDismissButton(uiBindings)
        setUpValidateButton(uiBindings)
        setUpDeleteButton(uiBindings)
    }

    private fun setUpTitle(uiBindings: DialogSessionPresetBinding) {
        val sessionPreset = sessionPresetViewModel.sessionPresetUpdatedLiveData.value
        val isCreation = (sessionPreset == null || sessionPreset.id == -1L)
        val titleField = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneTitle
        titleField.text = if (isCreation) {
            getString(R.string.session_preset_creation_dialog_title)
        }
        else {
            getString(R.string.session_preset_edition_dialog_title)
        }
    }

    private fun setUpDismissButton(uiBindings: DialogSessionPresetBinding) {
        val dismissButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneDismissButton
        dismissButton.setOnClickListener {
            listener?.onDismissButtonClicked()
            dismiss()
        }
    }

    private fun setUpValidateButton(uiBindings: DialogSessionPresetBinding) {
        val validateButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneValidateButton
        validateButton.setOnClickListener {
            if (sessionPresetViewModel.isSessionPresetValid()) {
                val sessionPresetToSave = sessionPresetViewModel.sessionPresetUpdatedLiveData.value
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

    private fun setUpDeleteButton(uiBindings: DialogSessionPresetBinding) {
        val sessionPreset = sessionPresetViewModel.sessionPresetUpdatedLiveData.value
        if (sessionPreset == null || sessionPreset.id == -1L) {
            uiBindings.sessionPresetDeleteButton.visibility = INVISIBLE
        }
        else {
            uiBindings.sessionPresetDeleteButton.visibility = VISIBLE
            uiBindings.sessionPresetDeleteButton.setOnClickListener {
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
    //  UI elements update
    private fun updateUi(
        sessionPreset: SessionPreset,
        uiBindings: DialogSessionPresetBinding
    ) {
        updateAudioZone(
            uiBindings,
            sessionPreset
        )
        updateDurationZone(
            uiBindings,
            sessionPreset
        )
        updateStartEndSoundZone(
            uiBindings,
            sessionPreset
        )
        updateCountDownZone(
            uiBindings,
            sessionPreset
        )
        updateIntervalLengthZone(
            uiBindings,
            sessionPreset
        )
        updateIntervalSoundZone(
            uiBindings,
            sessionPreset
        )
        setUpVibrationZone(
            uiBindings,
            sessionPreset
        )
    }

    //note: once an audio file has been selected, user can only modify the audio selection.
    //=> an audio session preset can never be changed to a timed session, it has to be deleted, and a new timed session created
    //(this is why the default type is timed when user creates a preset
    private fun updateAudioZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        if (sessionPreset.audioGuideSoundUriString.isBlank()) {
            uiBindings.audioGuideValue.text = getString(R.string.generic_NO_SELECTION)
        }
        else {
            uiBindings.audioGuideValue.text = getString(
                R.string.audio_file_display_info,
                sessionPreset.audioGuideSoundTitle,
                sessionPreset.audioGuideSoundArtistName,
                sessionPreset.audioGuideSoundAlbumName
            )
        }
        uiBindings.audioGuideZone.setOnClickListener {
            openSystemFilePicker(sessionPreset.audioGuideSoundUriString)
        }
    }

    private fun openSystemFilePicker(pickerInitialUri: String) {
        //Intent.ACTION_OPEN_DOCUMENT is needed for persistent access grant on the retrieved URI, Intent.ACTION_GET_CONTENT would only allow this one-shot access, causing an exception on subsequent access attempts
        val chooseAudioIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
            if (pickerInitialUri.isNotBlank() && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Uri.parse(pickerInitialUri)
                // specify a URI for the file that should appear in the system file picker when it loads.
                putExtra(
                    DocumentsContract.EXTRA_INITIAL_URI,
                    pickerInitialUri
                )
            }
        }
        startActivityForResult(
            chooseAudioIntent,
            ACTIVITY_RESULT_SELECT_AUDIO_FILE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        resultData: Intent?
    ) {
        if (requestCode == ACTIVITY_RESULT_SELECT_AUDIO_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that the user selected.
            resultData?.data?.also {uri ->
                sessionPresetViewModel.updatePresetAudioGuideSoundUriString(
                    requireContext(),
                    uri.toString()
                )
            }
        }
    }

    private fun updateDurationZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        val durationValueNormalColor = MaterialColors.getColor(requireContext(), R.attr.colorOnSurface, Color.BLACK)
        uiBindings.durationValue.setTextColor(durationValueNormalColor)
        val isAnAudioSession = sessionPreset.audioGuideSoundUriString.isNotBlank()
        val audioSessionDurationUnknown = isAnAudioSession && sessionPreset.duration == -1L
        if (isAnAudioSession) {
            if (audioSessionDurationUnknown) { //we have an audio file for the session, but could not retrieve its duration through its metadata => we need the user to input it manually
                uiBindings.durationZone.alpha = ENABLED_ZONE_ALPHA
                uiBindings.durationValue.text = getString(R.string.unknown_audio_duration_fill_manually)
                val errorColor = ContextCompat.getColor(
                    requireContext(),
                    R.color.red_600
                )
                uiBindings.durationValue.setTextColor(errorColor)
                uiBindings.durationZone.setOnClickListener(
                    getDurationClickListener(
                        uiBindings,
                        0L
                    )
                )
            }
            else { //duration field is filled with duration retrieved from audio file metadata, and interaction is deactivated
                uiBindings.durationZone.alpha = DISABLED_ZONE_ALPHA
                uiBindings.durationValue.text = formatDurationMsToString(sessionPreset.duration)
                uiBindings.durationZone.setOnClickListener {
                    Toast.makeText(
                        context,
                        getString(R.string.session_duration_vs_audio_explanation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        else {//timed session: duration is pre-fillled with input value, or default duration, interaction is active
            uiBindings.durationZone.alpha = ENABLED_ZONE_ALPHA
            uiBindings.durationValue.text = formatDurationMsToString(sessionPreset.duration)
            uiBindings.durationZone.setOnClickListener(
                getDurationClickListener(
                    uiBindings,
                    sessionPreset.duration
                )
            )
        }
    }

    private fun getDurationClickListener(
        uiBindings: DialogSessionPresetBinding,
        initialDuration: Long
    ) = View.OnClickListener {
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
            uiBindings.durationValue.text = formatDurationMsToString(lengthMs)
            sessionPresetViewModel.updatePresetDuration(lengthMs)
        }
        durationDialog.show(
            childFragmentManager,
            "openDurationDialog"
        )
    }

    private fun updateStartEndSoundZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        uiBindings.startEndSoundValue.text = sessionPreset.startAndEndSoundName
        //
        val ringTonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesRawAssetsNames)
        val ringTonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
        uiBindings.startEndSoundZone.setOnClickListener {
            val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
                title = getString(R.string.start_end_sound),
                initialSelectionUri = sessionPreset.startAndEndSoundUriString,
                confirmButtonLabel = getString(R.string.generic_string_OK),
                showSilenceChoice = true,
                ringTonesAssetsNames = ringTonesAssets,
                ringTonesDisplayNames = ringTonesTitles
            )
            soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener {selectedRingtoneUri, selectedRingtoneName ->
                sessionPresetViewModel.updatePresetStartAndEndSoundUriString(selectedRingtoneUri)
                sessionPresetViewModel.updatePresetStartAndEndSoundName(selectedRingtoneName)
            }
            soundPickerDialog.show(
                childFragmentManager,
                "openStartEndSoundPickerDialog"
            )
        }
    }

    private fun updateCountDownZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        uiBindings.countdownLengthValue.text = formatDurationMsToString(sessionPreset.startCountdownLength)
        uiBindings.countdownZone.setOnClickListener {
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
                uiBindings.countdownLengthValue.text = formatDurationMsToString(lengthMs)
                sessionPresetViewModel.updatePresetStartCountdownLength(lengthMs)
            }
            countdownDialog.show(
                childFragmentManager,
                "openCountDownDialog"
            )
        }
    }

    private fun updateIntervalLengthZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        if (sessionPreset.intermediateIntervalLength == -1L) {//this is an audio session
            uiBindings.intervalLengthValue.text = getString(R.string.generic_string_NONE)
            uiBindings.intervalLengthZone.alpha = DISABLED_ZONE_ALPHA
            uiBindings.intervalLengthZone.setOnClickListener {
                Toast.makeText(
                    context,
                    getString(R.string.session_interval_vs_audio_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else {
            uiBindings.intervalLengthZone.alpha = ENABLED_ZONE_ALPHA
            uiBindings.intervalLengthValue.text = formatDurationMsToString(sessionPreset.intermediateIntervalLength)
            uiBindings.intervalLengthZone.setOnClickListener {
                val intervalLengthDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                    title = getString(R.string.interval),
                    showHours = true,
                    showMinutes = true,
                    showSeconds = true,
                    explanationMessage = getString(R.string.session_interval_explanation),
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    initialLengthMs = sessionPreset.intermediateIntervalLength
                )
                intervalLengthDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
                    uiBindings.intervalLengthValue.text = formatDurationMsToString(lengthMs)
                    sessionPresetViewModel.updatePresetIntermediateIntervalLength(lengthMs)
                }
                intervalLengthDialog.show(
                    childFragmentManager,
                    "openIntervalLengthDialog"
                )
            }
        }
    }

    private fun updateIntervalSoundZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        if (sessionPreset.intermediateIntervalSoundName.isBlank()) {//this is an audio session
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
                    sessionPresetViewModel.updatePresetIntermediateIntervalSoundUriString(selectedRingtoneUri)
                    sessionPresetViewModel.updatePresetIntermediateIntervalSoundName(selectedRingtoneName)
                }
                soundPickerDialog.show(
                    childFragmentManager,
                    "openIntervalSoundPickerDialog"
                )
            }
        }
    }

    private fun setUpVibrationZone(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset
    ) {
        uiBindings.vibrationZone.setOnClickListener(null) //unregister listener to avoid OnCheckedChangeListener trigger when updating value
        uiBindings.vibrationSwitch.isChecked = sessionPreset.vibration
        //reset listener
        uiBindings.vibrationZone.setOnClickListener {
            uiBindings.vibrationSwitch.toggle()
        }
        uiBindings.vibrationSwitch.setOnCheckedChangeListener {_, p1 ->
            sessionPresetViewModel.updatePresetVibration(p1)
        }
    }

    /////////////////
    private fun formatDurationMsToString(duration: Long): String {
        return duration.autoFormatDurationMsAsSmallestHhMmSsString(
            getString(R.string.hms_duration_format_short),
            getString(R.string.ms_duration_format_short),
            getString(R.string.s_duration_format_short)
        )
    }
}