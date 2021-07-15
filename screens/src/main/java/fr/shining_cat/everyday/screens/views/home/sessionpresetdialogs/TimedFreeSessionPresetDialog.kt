package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetTimedFreeBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.TimedFreeSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class TimedFreeSessionPresetDialog : AbstractSessionPresetDialog() {

    private val LOG_TAG = TimedFreeSessionPresetDialog::class.java.name

    private val timedFreeSessionPresetViewModel: TimedFreeSessionPresetViewModel by viewModel()
    private val args: TimedFreeSessionPresetDialogArgs by navArgs()
    private val logger: Logger = get()
    private var timedFreeSessionPresetDialogBinding: DialogSessionPresetTimedFreeBinding? = null

    override fun getSessionPresetViewModel(): AbstractSessionPresetViewModel {
        return timedFreeSessionPresetViewModel
    }

    override fun getTitleField(): TextView? {
        return timedFreeSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneTitle
    }

    override fun getDismissButton(): ImageView? {
        return timedFreeSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneDismissButton
    }

    override fun getValidateButton(): ImageView? {
        return timedFreeSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneValidateButton
    }

    override fun getDeleteButton(): MaterialButton? {
        return timedFreeSessionPresetDialogBinding?.sessionPresetDeleteButton
    }

    override fun getVibrationSwitch(): SwitchMaterial? {
        return timedFreeSessionPresetDialogBinding?.vibrationSwitch
    }

    override fun getVibrationZone(): ViewGroup? {
        return timedFreeSessionPresetDialogBinding?.vibrationZone
    }

    override fun getCountdownZone(): ViewGroup? {
        return timedFreeSessionPresetDialogBinding?.countdownZone
    }

    override fun getCountdownLengthValue(): TextView? {
        return timedFreeSessionPresetDialogBinding?.countdownLengthValue
    }

    override fun getStartEndSoundZone(): ViewGroup? {
        return timedFreeSessionPresetDialogBinding?.startEndSoundZone
    }

    override fun getStartEndSoundValue(): TextView? {
        return timedFreeSessionPresetDialogBinding?.startEndSoundValue
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        timedFreeSessionPresetDialogBinding = DialogSessionPresetTimedFreeBinding.inflate(LayoutInflater.from(context))
        //
        timedFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.observe(
            viewLifecycleOwner,
            {
                updateCommonUi(it)
                updateSpecificUi(it)
            }
        )
        timedFreeSessionPresetViewModel.successLiveData.observe(
            viewLifecycleOwner,
            { dismissOnSuccess() }
        )
        timedFreeSessionPresetViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            { showErrorDialog(it) }
        )
        //
        val deviceDefaultRingtoneUriString = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        ).toString()
        val deviceDefaultRingtoneName = RingtoneManager.getRingtone(
            context,
            Uri.parse(deviceDefaultRingtoneUriString)
        ).getTitle(context)
        timedFreeSessionPresetViewModel.init(
            requireContext(),
            args.preset,
            deviceDefaultRingtoneUriString,
            deviceDefaultRingtoneName
        )
        initUi()
        return timedFreeSessionPresetDialogBinding!!.root
    }

    private fun updateSpecificUi(sessionPreset: SessionPreset) {
        updateRandomIntermediateInterval(sessionPreset.intermediateIntervalRandom)
        updateIntermediateIntervalLength(sessionPreset)
        updateIntermediateIntervalSound(sessionPreset)
    }

    private fun updateRandomIntermediateInterval(intermediateIntervalRandom: Boolean) {
        // unregister listener to avoid OnCheckedChangeListener trigger when updating value
        timedFreeSessionPresetDialogBinding?.intervalRandomZone?.setOnClickListener(null)
        timedFreeSessionPresetDialogBinding?.intervalRandomSwitch?.isChecked = intermediateIntervalRandom
        // reset listener
        timedFreeSessionPresetDialogBinding?.intervalRandomZone?.setOnClickListener {
            getVibrationSwitch()?.toggle()
        }
        timedFreeSessionPresetDialogBinding?.intervalRandomSwitch?.setOnCheckedChangeListener { _, p1 ->
            getSessionPresetViewModel().updatePresetIntermediateIntervalRandom(p1)
        }
    }

    private fun updateIntermediateIntervalLength(sessionPreset: SessionPreset) {
        if (sessionPreset.intermediateIntervalRandom) {
            timedFreeSessionPresetDialogBinding?.intervalLengthZone?.alpha = DISABLED_ZONE_ALPHA
            timedFreeSessionPresetDialogBinding?.intervalLengthValue?.text = getString(R.string.interval_random)
            timedFreeSessionPresetDialogBinding?.intervalLengthZone?.setOnClickListener {
                Toast.makeText(
                    context,
                    getString(R.string.interval_random_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            timedFreeSessionPresetDialogBinding?.intervalLengthZone?.alpha = ENABLED_ZONE_ALPHA
            timedFreeSessionPresetDialogBinding?.intervalLengthValue?.text = formatDurationMsToString(sessionPreset.intermediateIntervalLength)
            timedFreeSessionPresetDialogBinding?.intervalLengthZone?.setOnClickListener {
                showBottomIntervalLengthSelector(sessionPreset.intermediateIntervalLength)
            }
        }
    }

    private fun updateIntermediateIntervalSound(sessionPreset: SessionPreset) {
        if (sessionPreset.intermediateIntervalSoundUriString.isBlank()) {
            timedFreeSessionPresetDialogBinding?.intervalSoundValue?.text = getString(R.string.generic_string_NONE)
        } else {
            timedFreeSessionPresetDialogBinding?.intervalSoundValue?.text = sessionPreset.intermediateIntervalSoundName
            val ringTonesAssets = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesRawAssetsNames)
            val ringTonesTitles = context?.resources?.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
            timedFreeSessionPresetDialogBinding?.intervalSoundZone?.setOnClickListener {
                val soundPickerDialog = BottomDialogDismissibleRingtonePicker.newInstance(
                    title = getString(R.string.interval_sound),
                    initialSelectionUri = sessionPreset.intermediateIntervalSoundUriString,
                    confirmButtonLabel = getString(R.string.generic_string_OK),
                    showSilenceChoice = true,
                    ringTonesAssetsNames = ringTonesAssets,
                    ringTonesDisplayNames = ringTonesTitles
                )
                soundPickerDialog.setBottomDialogDismissibleRingtonePickerListener { selectedRingtoneUri, selectedRingtoneName ->
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