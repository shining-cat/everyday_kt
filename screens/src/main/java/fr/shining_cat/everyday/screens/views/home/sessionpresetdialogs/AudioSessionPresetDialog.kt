package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetAudioBinding
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AudioSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class AudioSessionPresetDialog : AbstractSessionPresetDialog() {
    
    private val LOG_TAG = AudioSessionPresetDialog::class.java.name

    private val audioSessionPresetViewModel: AudioSessionPresetViewModel by viewModel()
    private val args: AudioSessionPresetDialogArgs by navArgs()
    private val logger: Logger = get()
    private var audioSessionPresetDialogBinding:DialogSessionPresetAudioBinding? = null
    
    override fun getSessionPresetViewModel(): AbstractSessionPresetViewModel {
        return audioSessionPresetViewModel
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

    override fun getDurationZone(): ViewGroup? {
        return audioSessionPresetDialogBinding?.durationZone
    }

    override fun getDurationValue(): TextView? {
        return audioSessionPresetDialogBinding?.durationValue
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
        audioSessionPresetDialogBinding = DialogSessionPresetAudioBinding.inflate(LayoutInflater.from(context))
        //
        audioSessionPresetViewModel.sessionPresetUpdatedLiveData.observe(viewLifecycleOwner,
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
        audioSessionPresetViewModel.init(
            requireContext(),
            args.preset,
            deviceDefaultRingtoneUriString,
            deviceDefaultRingtoneName
        )
        initUi()
        return audioSessionPresetDialogBinding!!.root
    }

    private fun updateSpecificUi(sessionPreset: SessionPreset) {
        updateAudioZone(sessionPreset)
        updateDurationZone(sessionPreset)
    }
    
    private fun updateAudioZone(sessionPreset: SessionPreset) {
        if (sessionPreset.audioGuideSoundUriString.isBlank()) {
            audioSessionPresetDialogBinding?.audioGuideValue?.text = getString(R.string.generic_NO_SELECTION)
        }
        else {
            audioSessionPresetDialogBinding?.audioGuideValue?.text = getString(
                R.string.audio_file_display_info,
                sessionPreset.audioGuideSoundTitle,
                sessionPreset.audioGuideSoundArtistName,
                sessionPreset.audioGuideSoundAlbumName
            )
        }
        audioSessionPresetDialogBinding?.audioGuideZone?.setOnClickListener {
            openSystemFilePicker(sessionPreset.audioGuideSoundUriString)
        }
    }

    private fun openSystemFilePicker(pickerInitialUri: String) {
        // Intent.ACTION_OPEN_DOCUMENT is needed for persistent access grant on the retrieved URI, Intent.ACTION_GET_CONTENT would only allow this one-shot access, causing an exception on subsequent access attempts
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
            Constants.ACTIVITY_RESULT_SELECT_AUDIO_FILE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        resultData: Intent?
    ) {
        if (requestCode == Constants.ACTIVITY_RESULT_SELECT_AUDIO_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that the user selected.
            resultData?.data?.also { uri ->
                audioSessionPresetViewModel.updatePresetAudioGuideSoundUriString(
                    requireContext(),
                    uri.toString()
                )
            }
        }
    }
    
}