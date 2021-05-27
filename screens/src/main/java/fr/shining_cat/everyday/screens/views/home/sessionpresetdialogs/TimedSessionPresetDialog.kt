package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
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

    override fun getDurationZone(): ViewGroup? {
        return timedSessionPresetDialogBinding?.durationZone
    }

    override fun getDurationValue(): TextView? {
        return timedSessionPresetDialogBinding?.durationValue
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
}