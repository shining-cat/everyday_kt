package fr.shining_cat.everyday.screens.views.home

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.media.RingtoneManager
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
import androidx.fragment.app.DialogFragment
import fr.shining_cat.everyday.commons.Constants.Companion.ACTIVITY_RESULT_SELECT_AUDIO_FILE
import fr.shining_cat.everyday.commons.Constants.Companion.DEFAULT_SESSION_COUNTDOWN_MILLIS
import fr.shining_cat.everyday.commons.Constants.Companion.DEFAULT_SESSION_DURATION_MILLIS
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.autoFormatDurationMsAsSmallestHhMmSsString
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.models.AudioFileMetadata
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

    //
    private var newId = -1L
    private var newAudioGuideUri = ""
    private var newAudioGuideLabel = ""
    private var newDuration = 0L
    private var newStartEndSoundUri: Uri = Uri.parse("")
    private var newStartEndSoundName = ""
    private var newCountdown = 0L
    private var newIntervalLength = 0L
    private var newIntervalSoundUri: Uri = Uri.parse("")
    private var newIntervalSoundName = ""
    private var newVibration = false
    //

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
                    sessionPresetViewModel.init(preset)
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
        val createSessionPresetDialogBinding = DialogSessionPresetBinding.inflate(LayoutInflater.from(context))
        initUi(createSessionPresetDialogBinding)
        return createSessionPresetDialogBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        return dialog
    }

    private fun initUi(uiBindings: DialogSessionPresetBinding) {
        val presetInput = arguments?.getParcelable(PRESET_INPUT_ARG) as SessionPreset?
        initValues(presetInput)
        val isAnAudioSession = newAudioGuideUri.isNotBlank()
        //
        val dismissButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneDismissButton
        dismissButton.setOnClickListener {
            listener?.onDismissButtonClicked()
            dismiss()
        }
        //
        val validateButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneValidateButton
        validateButton.setOnClickListener {
            listener?.onConfirmButtonClicked(sessionPresetViewModel.sessionPresetUpdatedLiveData.value)
            dismiss()
        }
        //
        setUpTitle(
            uiBindings,
            presetInput
        )
        setUpAudioZone(uiBindings)
        setUpDurationZone(
            uiBindings,
            isAnAudioSession
        )
        setUpStartEndSoundZone(uiBindings)
        setUpCountDownZone(uiBindings)
        setUpIntervalLengthZone(
            uiBindings,
            isAnAudioSession
        )
        setUpIntervalSoundZone(
            uiBindings,
            isAnAudioSession
        )
        setUpVibrationZone(uiBindings)
        setUpDeleteButton(
            uiBindings,
            presetInput
        )
    }

    private fun initValues(presetInput: SessionPreset?) {
        val deviceDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        )
        //
        newId = presetInput?.id ?: -1L
        newAudioGuideUri = presetInput?.audioGuideSoundUriString ?: ""
        newDuration = presetInput?.duration ?: DEFAULT_SESSION_DURATION_MILLIS
        newStartEndSoundUri =
            if (presetInput?.startAndEndSoundUriString == null) deviceDefaultRingtoneUri else Uri.parse(presetInput.startAndEndSoundUriString)
        newStartEndSoundName = RingtoneManager.getRingtone(
            context,
            newStartEndSoundUri
        ).getTitle(context)
        newCountdown = presetInput?.startCountdownLength ?: DEFAULT_SESSION_COUNTDOWN_MILLIS
        newIntervalLength = presetInput?.intermediateIntervalLength ?: 0L
        newIntervalSoundUri = if (presetInput?.intermediateIntervalSoundUriString == null) {
            deviceDefaultRingtoneUri
        }
        else {
            Uri.parse(presetInput.intermediateIntervalSoundUriString)
        }
        newIntervalSoundName = RingtoneManager.getRingtone(
            context,
            newIntervalSoundUri
        ).getTitle(context)
        newVibration = presetInput?.vibration ?: false
    }

    private fun setUpTitle(
        uiBindings: DialogSessionPresetBinding,
        presetInput: SessionPreset?
    ) {
        val titleField = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneTitle
        titleField.text = if (presetInput == null) {
            getString(R.string.session_preset_creation_dialog_title)
        }
        else {
            getString(R.string.session_preset_edition_dialog_title)
        }
    }

    private fun setUpAudioZone(uiBindings: DialogSessionPresetBinding) {
        if (newAudioGuideUri.isBlank()) {
            uiBindings.audioGuideValue.text = getString(R.string.generic_NO_SELECTION)
        }
        else {
            //TODO: get selected file name from metadata
        }
        uiBindings.audioGuideZone.setOnClickListener {
            Toast.makeText(
                context,
                "TODO: OPEN FILE PICKER",
                Toast.LENGTH_LONG
            ).show()
            openSystemFilePicker(newAudioGuideUri)
        }
        toggleTimerVsAudioPreset(
            uiBindings,
            newAudioGuideUri.isNotBlank()
        )
    }

    private fun openSystemFilePicker(pickerInitialUri: String? = null) {
        val chooseAudioIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
            if (pickerInitialUri != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Uri.parse(pickerInitialUri)
                // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
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
                newAudioGuideUri = uri.toString()
            }
        }
    }

    private fun toggleTimerVsAudioPreset(
        uiBindings: DialogSessionPresetBinding,
        isAnAudioSession: Boolean
    ) {
        setUpDurationZone(
            uiBindings,
            isAnAudioSession
        )
        setUpIntervalLengthZone(
            uiBindings,
            isAnAudioSession
        )
        setUpIntervalSoundZone(
            uiBindings,
            isAnAudioSession
        )
    }

    private fun setUpDurationZone(
        uiBindings: DialogSessionPresetBinding,
        disabled: Boolean
    ) {
        if (disabled) {
            //TODO: set duration field to audio file duration from metadata
            uiBindings.durationZone.alpha = DISABLED_ZONE_ALPHA
            uiBindings.durationZone.setOnClickListener {
                Toast.makeText(
                    context,
                    getString(R.string.session_duration_vs_audio_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else {
            uiBindings.durationZone.alpha = ENABLED_ZONE_ALPHA
            uiBindings.durationValue.text = formatDurationMsToString(newDuration)
            uiBindings.durationZone.setOnClickListener {
                val durationDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                    title = getString(R.string.duration),
                    showHours = true,
                    showMinutes = true,
                    showSeconds = true,
                    explanationMessage = getString(R.string.session_duration_vs_audio_explanation),
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    initialLengthMs = newDuration
                )
                durationDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
                    uiBindings.durationValue.text = formatDurationMsToString(lengthMs)
                    newDuration = lengthMs
                }
                durationDialog.show(
                    childFragmentManager,
                    "openDurationDialog"
                )
            }
        }
    }

    private fun setUpStartEndSoundZone(uiBindings: DialogSessionPresetBinding) {
        val ringtonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesAssetsNames)
        val ringtonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
        uiBindings.startEndSoundValue.text = newStartEndSoundName
        uiBindings.startEndSoundZone.setOnClickListener {
            val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
                title = getString(R.string.generic_string_ERROR),
                initialSelectionUri = newStartEndSoundUri.toString(),
                confirmButtonLabel = getString(R.string.generic_string_OK),
                showSilenceChoice = true,
                ringTonesAssetsNames = ringtonesAssets,
                ringTonesDisplayNames = ringtonesTitles
            )
            soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener {selectedRingtoneUri, selectedRingtoneName ->
                uiBindings.startEndSoundValue.text = selectedRingtoneName
                newStartEndSoundUri = Uri.parse(selectedRingtoneUri)
                newStartEndSoundName = selectedRingtoneName
            }
            soundPickerDialog.show(
                childFragmentManager,
                "openStartEndSoundPickerDialog"
            )
        }
    }

    private fun setUpCountDownZone(uiBindings: DialogSessionPresetBinding) {
        uiBindings.countdownLengthValue.text = formatDurationMsToString(newCountdown)
        uiBindings.countdownZone.setOnClickListener {
            val countdownDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                title = getString(R.string.duration),
                showHours = false,
                showMinutes = false,
                showSeconds = true,
                explanationMessage = getString(R.string.session_countdown_explanation),
                confirmButtonLabel = getString(R.string.generic_string_OK),
                initialLengthMs = newCountdown
            )
            countdownDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
                uiBindings.countdownLengthValue.text = formatDurationMsToString(lengthMs)
                newCountdown = lengthMs
            }
            countdownDialog.show(
                childFragmentManager,
                "openCountDownDialog"
            )
        }
    }

    private fun setUpIntervalLengthZone(
        uiBindings: DialogSessionPresetBinding,
        disabled: Boolean
    ) {
        if (disabled) {
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
            uiBindings.intervalLengthValue.text = formatDurationMsToString(newIntervalLength)
            uiBindings.intervalLengthZone.setOnClickListener {
                val intervalLengthDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                    title = getString(R.string.duration),
                    showHours = true,
                    showMinutes = true,
                    showSeconds = true,
                    explanationMessage = getString(R.string.session_interval_explanation),
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    initialLengthMs = newIntervalLength
                )
                intervalLengthDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener {lengthMs ->
                    uiBindings.intervalLengthValue.text = formatDurationMsToString(lengthMs)
                    newIntervalLength = lengthMs
                }
                intervalLengthDialog.show(
                    childFragmentManager,
                    "openIntervalLengthDialog"
                )
            }
        }
    }

    private fun setUpIntervalSoundZone(
        uiBindings: DialogSessionPresetBinding,
        disabled: Boolean
    ) {
        if (disabled) {
            uiBindings.intervalSoundZone.alpha = DISABLED_ZONE_ALPHA
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
            val ringtonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesAssetsNames)
            val ringtonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
            uiBindings.intervalSoundValue.text = newIntervalSoundName
            uiBindings.intervalSoundZone.setOnClickListener {
                val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
                    title = getString(R.string.generic_string_ERROR),
                    initialSelectionUri = newIntervalSoundUri.toString(),
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    showSilenceChoice = true,
                    ringTonesAssetsNames = ringtonesAssets,
                    ringTonesDisplayNames = ringtonesTitles
                )
                soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener {selectedRingtoneUri, selectedRingtoneName ->
                    uiBindings.intervalSoundValue.text = selectedRingtoneName
                    newIntervalSoundUri = Uri.parse(selectedRingtoneUri)
                    newIntervalSoundName = newStartEndSoundName
                }
                soundPickerDialog.show(
                    childFragmentManager,
                    "openIntervalSoundPickerDialog"
                )
            }
        }
    }

    private fun setUpVibrationZone(uiBindings: DialogSessionPresetBinding) {
        uiBindings.vibrationZone.setOnClickListener {
            uiBindings.vibrationSwitch.toggle()
        }
        uiBindings.vibrationSwitch.setOnCheckedChangeListener {p0, p1 -> newVibration = p1}
    }

    private fun setUpDeleteButton(
        uiBindings: DialogSessionPresetBinding,
        sessionPreset: SessionPreset?
    ) {
        if (sessionPreset == null) {
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

    /////////////////
    private fun formatDurationMsToString(duration: Long): String {
        return duration.autoFormatDurationMsAsSmallestHhMmSsString(
            getString(R.string.hms_duration_format_short),
            getString(R.string.ms_duration_format_short),
            getString(R.string.s_duration_format_short)
        )
    }
}