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
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetAudioFreeBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AudioFreeSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class AudioFreeSessionPresetDialog: AbstractSessionPresetDialog() {

    private val LOG_TAG = AudioFreeSessionPresetDialog::class.java.name

    private val audioFreeSessionPresetViewModel: AudioFreeSessionPresetViewModel by viewModel()
    private val args: AudioFreeSessionPresetDialogArgs by navArgs()
    private val logger: Logger = get()
    private var audioSessionPresetDialogBinding: DialogSessionPresetAudioFreeBinding? = null

    override fun getSessionPresetViewModel(): AbstractSessionPresetViewModel {
        return audioFreeSessionPresetViewModel
    }

    override fun getTitleField(): TextView? {
        return audioSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneTitle
    }

    override fun getDismissButton(): ImageView? {
        return audioSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneDismissButton
    }

    override fun getValidateButton(): ImageView? {
        return audioSessionPresetDialogBinding?.dialogSessionPresetTitleZone?.dialogFullscreenTitleZoneValidateButton
    }

    override fun getDeleteButton(): MaterialButton? {
        return audioSessionPresetDialogBinding?.sessionPresetDeleteButton
    }

    override fun getVibrationSwitch(): SwitchMaterial? {
        return audioSessionPresetDialogBinding?.vibrationSwitch
    }

    override fun getVibrationZone(): ViewGroup? {
        return audioSessionPresetDialogBinding?.vibrationZone
    }

    override fun getCountdownZone(): ViewGroup? {
        return audioSessionPresetDialogBinding?.countdownZone
    }

    override fun getCountdownLengthValue(): TextView? {
        return audioSessionPresetDialogBinding?.countdownLengthValue
    }

    override fun getStartEndSoundZone(): ViewGroup? {
        return audioSessionPresetDialogBinding?.startEndSoundZone
    }

    override fun getStartEndSoundValue(): TextView? {
        return audioSessionPresetDialogBinding?.startEndSoundValue
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        audioSessionPresetDialogBinding = DialogSessionPresetAudioFreeBinding.inflate(LayoutInflater.from(context))
        //
        audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.observe(viewLifecycleOwner,
            {updateCommonUi(it)})
        audioFreeSessionPresetViewModel.successLiveData.observe(viewLifecycleOwner,
            {dismissOnSuccess()})
        audioFreeSessionPresetViewModel.errorLiveData.observe(viewLifecycleOwner,
            {showErrorDialog(it)})
        //
        val deviceDefaultRingtoneUriString = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        ).toString()
        val deviceDefaultRingtoneName = RingtoneManager.getRingtone(
            context,
            Uri.parse(deviceDefaultRingtoneUriString)
        ).getTitle(context)
        audioFreeSessionPresetViewModel.init(
            requireContext(),
            args.preset,
            deviceDefaultRingtoneUriString,
            deviceDefaultRingtoneName
        )
        initUi()
        return audioSessionPresetDialogBinding!!.root
    }

}