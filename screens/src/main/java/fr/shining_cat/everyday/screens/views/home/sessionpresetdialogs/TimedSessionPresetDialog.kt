package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetTimedBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.TimedSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class TimedSessionPresetDialog: AbstractSessionPresetDialog() {

    private val LOG_TAG = TimedSessionPresetDialog::class.java.name

    private val timedSessionPresetViewModel: TimedSessionPresetViewModel by viewModel()
    private val args: TimedSessionPresetDialogArgs by navArgs()
    private val logger: Logger = get()
    private var timedSessionPresetDialogBinding: DialogSessionPresetTimedBinding? = null

    override fun getSessionPresetViewModel(): AbstractSessionPresetViewModel {
        return timedSessionPresetViewModel
    }

    override fun getTitleField(): TextView? {
        return timedSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneTitle
    }

    override fun getDismissButton(): ImageView? {
        return timedSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneDismissButton
    }

    override fun getValidateButton(): ImageView? {
        return timedSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneValidateButton
    }

    override fun getDeleteButton(): MaterialButton? {
        return timedSessionPresetDialogBinding?.sessionPresetDeleteButton
    }

    override fun getVibrationSwitch(): SwitchMaterial? {
        return timedSessionPresetDialogBinding?.vibrationSwitch
    }

    override fun getVibrationZone(): ViewGroup? {
        return timedSessionPresetDialogBinding?.vibrationZone
    }

    override fun getCountdownZone(): ViewGroup? {
        return timedSessionPresetDialogBinding?.countdownZone
    }

    override fun getCountdownLengthValue(): TextView? {
        return timedSessionPresetDialogBinding?.countdownLengthValue
    }

    override fun getStartEndSoundZone(): ViewGroup? {
        return timedSessionPresetDialogBinding?.startEndSoundZone
    }

    override fun getStartEndSoundValue(): TextView? {
        return timedSessionPresetDialogBinding?.startEndSoundValue
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        timedSessionPresetDialogBinding = DialogSessionPresetTimedBinding.inflate(LayoutInflater.from(context))
        //
        timedSessionPresetViewModel.sessionPresetUpdatedLiveData.observe(viewLifecycleOwner,
            {
                updateCommonUi(it)
                updateSpecificUi(it)
            })
        timedSessionPresetViewModel.invalidDurationLiveData.observe(viewLifecycleOwner,
            {
                val textColor = if (it) {
                    MaterialColors.getColor(
                        requireContext(),
                        R.attr.colorOnSurface,
                        Color.BLACK
                    )
                }
                else {
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_600
                    )
                }
                timedSessionPresetDialogBinding?.durationValue?.setTextColor(textColor)
            })
        //
        val deviceDefaultRingtoneUriString = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        ).toString()
        val deviceDefaultRingtoneName = RingtoneManager.getRingtone(
            context,
            Uri.parse(deviceDefaultRingtoneUriString)
        ).getTitle(context)
        timedSessionPresetViewModel.init(
            requireContext(),
            args.preset,
            deviceDefaultRingtoneUriString,
            deviceDefaultRingtoneName
        )
        initUi()
        return timedSessionPresetDialogBinding!!.root
    }

    private fun updateSpecificUi(sessionPreset: SessionPreset) {
        updateDurationZone(sessionPreset)
        updateRandomIntermediateInterval(sessionPreset.intermediateIntervalRandom)
        updateIntermediateIntervalLength(sessionPreset)
        updateIntermediateIntervalSound(sessionPreset)
    }

    private fun updateDurationZone(sessionPreset: SessionPreset) {
        val durationZone = timedSessionPresetDialogBinding?.durationZone
        val durationValue = timedSessionPresetDialogBinding?.durationValue
        if (durationZone == null || durationValue == null) return
        durationValue.text = formatDurationMsToString(sessionPreset.duration)
        durationZone.setOnClickListener {
            showBottomDurationSelector(sessionPreset.duration)
        }
    }

    private fun updateRandomIntermediateInterval(intermediateIntervalRandom: Boolean) {
        timedSessionPresetDialogBinding?.intervalRandomZone?.setOnClickListener(null) // unregister listener to avoid OnCheckedChangeListener trigger when updating value
        timedSessionPresetDialogBinding?.intervalRandomSwitch?.isChecked = intermediateIntervalRandom
        // reset listener
        timedSessionPresetDialogBinding?.intervalRandomZone?.setOnClickListener {
            getVibrationSwitch()?.toggle()
        }
        timedSessionPresetDialogBinding?.intervalRandomSwitch?.setOnCheckedChangeListener {_, p1 ->
            getSessionPresetViewModel().updatePresetIntermediateIntervalRandom(p1)
        }
    }

    private fun updateIntermediateIntervalLength(sessionPreset: SessionPreset) {
        logger.d(LOG_TAG, "updateIntermediateIntervalLength::length = sessionPreset.intermediateIntervalLength")
        if (sessionPreset.intermediateIntervalRandom) {
            timedSessionPresetDialogBinding?.intervalLengthZone?.alpha = DISABLED_ZONE_ALPHA
            timedSessionPresetDialogBinding?.intervalLengthValue?.text = getString(R.string.interval_random)
            timedSessionPresetDialogBinding?.intervalLengthZone?.setOnClickListener {
                Toast.makeText(
                    context,
                    getString(R.string.interval_random_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else {
            timedSessionPresetDialogBinding?.intervalLengthZone?.alpha = ENABLED_ZONE_ALPHA
            timedSessionPresetDialogBinding?.intervalLengthValue?.text = formatDurationMsToString(sessionPreset.intermediateIntervalLength)
            timedSessionPresetDialogBinding?.intervalLengthZone?.setOnClickListener {
                showBottomIntervalLengthSelector(sessionPreset.intermediateIntervalLength)
            }
        }
    }

    private fun updateIntermediateIntervalSound(sessionPreset: SessionPreset) {
        if (sessionPreset.intermediateIntervalSoundUriString.isBlank()) {
            timedSessionPresetDialogBinding?.intervalSoundValue?.text = getString(R.string.generic_string_NONE)
        }
        else {
            timedSessionPresetDialogBinding?.intervalSoundValue?.text = sessionPreset.intermediateIntervalSoundName
            val ringTonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesRawAssetsNames)
            val ringTonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
            timedSessionPresetDialogBinding?.intervalSoundZone?.setOnClickListener {
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
}