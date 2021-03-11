package fr.shining_cat.everyday.screens.views.home.sessionpresets

import android.app.Dialog
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import androidx.fragment.app.DialogFragment
import fr.shining_cat.everyday.commons.Constants.Companion.DEFAULT_SESSION_COUNTDOWN_MILLIS
import fr.shining_cat.everyday.commons.Constants.Companion.DEFAULT_SESSION_DURATION_MILLIS
import fr.shining_cat.everyday.commons.Constants.Companion.ONE_HOUR_AS_MS
import fr.shining_cat.everyday.commons.Constants.Companion.ONE_MINUTE_AS_MS
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.formatDurationMsAsHhMmSsString
import fr.shining_cat.everyday.commons.extensions.formatDurationMsAsMmSsString
import fr.shining_cat.everyday.commons.extensions.formatDurationMsAsSsString
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetBinding
import org.koin.android.ext.android.get

class SessionPresetDialog : DialogFragment() {

    private val LOG_TAG = SessionPresetDialog::class.java.name

    private val logger: Logger = get()
    private val PRESET_INPUT_ARG = "session preset to edit argument"
    private var listener: SessionPresetDialogListener? = null

    //
    private var newId = -1L
    private var newAudioGuideUri = ""
    private var newDuration = 0L
    private var newStartEndSoundUri: Uri = Uri.parse("")
    private var newStartEndSoundName = ""
    private var newCountdown = 0L
    private var newInterval = 0L
    private var newIntervalSoundUri: Uri = Uri.parse("")
    private var newIntervalSoundName = ""
    private var newVibration = false
    //

    fun interface SessionPresetDialogListener {
        fun onConfirmButtonClicked(sessionPreset: SessionPreset)
    }

    fun setSessionPresetDialogListener(listener: SessionPresetDialogListener) {
        this.listener = listener
    }

    companion object {
        fun newInstance(preset: SessionPreset? = null): SessionPresetDialog =
            SessionPresetDialog().apply {
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
        //
        val dismissButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneDismissButton
        dismissButton.setOnClickListener { dismiss() }
        //
        val validateButton = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneValidateButton
        validateButton.setOnClickListener {
            listener?.onConfirmButtonClicked(buildSessionPreset())
            dismiss()
        }
        //
        setUpTitle(uiBindings, presetInput)
        setUpAudioZone(uiBindings)
        setUpDurationZone(uiBindings)
        setUpStartEndSoundZone(uiBindings)
        setUpCountDownZone(uiBindings)
        setUpIntervalZone(uiBindings)
        setUpIntervalSoundZone(uiBindings)
        setUpVibrationZone(uiBindings)
    }

    private fun initValues(presetInput: SessionPreset?) {
        val deviceDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION)
        //
        newId = presetInput?.id ?: -1L
        newAudioGuideUri = presetInput?.audioGuideSoundUri ?: ""
        newDuration = presetInput?.duration ?: DEFAULT_SESSION_DURATION_MILLIS
        newStartEndSoundUri = if (presetInput?.startAndEndSoundUri == null) deviceDefaultRingtoneUri else Uri.parse(presetInput.startAndEndSoundUri)
        newStartEndSoundName = RingtoneManager.getRingtone(context, newStartEndSoundUri).getTitle(context)
        newCountdown = presetInput?.startCountdownLength ?: DEFAULT_SESSION_COUNTDOWN_MILLIS
        newInterval = presetInput?.intermediateIntervalLength ?: 0L
        newIntervalSoundUri = if (presetInput?.intermediateIntervalSoundUri == null) {
            deviceDefaultRingtoneUri
        }
        else {
            Uri.parse(presetInput.intermediateIntervalSoundUri)
        }
        newIntervalSoundName = RingtoneManager.getRingtone(context, newIntervalSoundUri).getTitle(context)
        newVibration = presetInput?.vibration ?: false
    }

    private fun setUpTitle(uiBindings: DialogSessionPresetBinding, presetInput: SessionPreset?) {
        val titleField = uiBindings.dialogSessionPresetTitleZone.dialogFullscreenTitleZoneTitle
        titleField.text = if (presetInput == null) {
            getString(R.string.session_preset_creation_dialog_title)
        }
        else {
            getString(R.string.session_preset_edition_dialog_title)
        }
    }

    private fun setUpAudioZone(uiBindings: DialogSessionPresetBinding) {
        //TODO: open file picker, get result as file Uri
    }

    private fun setUpDurationZone(uiBindings: DialogSessionPresetBinding) {
        uiBindings.durationValue.text = formatDurationMsToString(newDuration)
        uiBindings.durationZone.setOnClickListener {
            val durationDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                title = getString(R.string.duration),
                showHours = true,
                showMinutes = true,
                showSeconds = true,
                explanationMessage = getString(R.string.session_duration_explanation),
                confirmButtonLabel = getString(R.string.generic_string_OK),
                initialLengthMs = newDuration
            )
            durationDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener { lengthMs ->
                uiBindings.durationValue.text = formatDurationMsToString(lengthMs)
                newDuration = lengthMs
            }
            durationDialog.show(childFragmentManager, "openDurationDialog")
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
            soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener { selectedRingtoneUri, selectedRingtoneName ->
                uiBindings.startEndSoundValue.text = selectedRingtoneName
                newStartEndSoundUri = Uri.parse(selectedRingtoneUri)
                newStartEndSoundName = selectedRingtoneName
            }
            soundPickerDialog.show(childFragmentManager, "openStartEndSoundPickerDialog")
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
            countdownDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener { lengthMs ->
                uiBindings.countdownLengthValue.text = formatDurationMsToString(lengthMs)
                newCountdown = lengthMs
            }
            countdownDialog.show(childFragmentManager, "openDurationDialog")
        }
    }

    private fun setUpIntervalZone(uiBindings: DialogSessionPresetBinding) {
        uiBindings.intervalLengthValue.text = formatDurationMsToString(newInterval)
        uiBindings.intervalZone.setOnClickListener {
            val intervalDialog = BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                title = getString(R.string.duration),
                showHours = true,
                showMinutes = true,
                showSeconds = true,
                explanationMessage = getString(R.string.session_interval_explanation),
                confirmButtonLabel = getString(R.string.generic_string_OK),
                initialLengthMs = newInterval
            )
            intervalDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener { lengthMs ->
                uiBindings.intervalLengthValue.text = formatDurationMsToString(lengthMs)
                newInterval = lengthMs
            }
            intervalDialog.show(childFragmentManager, "openIntervalDialog")
        }
    }

    private fun setUpIntervalSoundZone(uiBindings: DialogSessionPresetBinding) {
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
            soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener { selectedRingtoneUri, selectedRingtoneName ->
                uiBindings.intervalSoundValue.text = selectedRingtoneName
                newIntervalSoundUri = Uri.parse(selectedRingtoneUri)
                newIntervalSoundName = newStartEndSoundName
            }
            soundPickerDialog.show(childFragmentManager, "openIntervalSoundPickerDialog")
        }
    }

    private fun setUpVibrationZone(uiBindings: DialogSessionPresetBinding) {
        uiBindings.vibrationZone.setOnClickListener {
            uiBindings.vibrationSwitch.toggle()
        }
        uiBindings.vibrationSwitch.setOnCheckedChangeListener { p0, p1 -> newVibration = p1 }
    }

    //////////////////
    private fun buildSessionPreset(): SessionPreset {
        return SessionPreset(
            id = newId,
            duration = newDuration,
            startAndEndSoundUri = newStartEndSoundUri.toString(),
            intermediateIntervalLength = newInterval,
            startCountdownLength = newCountdown,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = newIntervalSoundUri.toString(),
            audioGuideSoundUri = newAudioGuideUri,
            vibration = newVibration,
            lastEditTime = System.currentTimeMillis(),
            sessionTypeId = -1L
        )
    }

    /////////////////
    private fun formatDurationMsToString(duration: Long): String {
        return when {
            duration < ONE_MINUTE_AS_MS -> {
                duration.formatDurationMsAsSsString(getString(R.string.s_duration_format_short))
            }
            duration < ONE_HOUR_AS_MS -> {
                duration.formatDurationMsAsMmSsString(getString(R.string.ms_duration_format_short))
            }
            else -> {
                duration.formatDurationMsAsHhMmSsString(getString(R.string.hms_duration_format_short))
            }
        }
    }

}