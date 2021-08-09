package fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.switchmaterial.SwitchMaterial
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.DialogSessionPresetAudioBinding
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AbstractSessionPresetViewModel
import fr.shining_cat.everyday.screens.viewmodels.sessionpresets.AudioSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioSessionPresetDialog : AbstractSessionPresetDialog() {

    private val LOG_TAG = AudioSessionPresetDialog::class.java.name

    private val audioSessionPresetViewModel: AudioSessionPresetViewModel by viewModel()
    private val args: AudioSessionPresetDialogArgs by navArgs()
    private val logger: Logger = get()
    private var audioSessionPresetDialogBinding: DialogSessionPresetAudioBinding? = null

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
        audioSessionPresetViewModel.sessionPresetUpdatedLiveData.observe(
            viewLifecycleOwner,
            {
                updateCommonUi(it)
                updateSpecificUi(it)
            }
        )
        audioSessionPresetViewModel.successLiveData.observe(
            viewLifecycleOwner,
            { dismissOnSuccess() }
        )
        audioSessionPresetViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            { showErrorDialog(it) }
        )
        audioSessionPresetViewModel.invalidAudioGuideLiveData.observe(
            viewLifecycleOwner,
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
                audioSessionPresetDialogBinding?.audioGuideValue?.setTextColor(textColor)
            }
        )
        audioSessionPresetViewModel.invalidDurationLiveData.observe(
            viewLifecycleOwner,
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
                audioSessionPresetDialogBinding?.durationValue?.setTextColor(textColor)
            }
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
        if (sessionPreset.audioGuideSoundUriString.isBlank()) {
            audioSessionPresetDialogBinding?.audioGuideValue?.text = getString(R.string.generic_NO_SELECTION)
            audioSessionPresetDialogBinding?.durationZone?.visibility = GONE
        }
        else {
            audioSessionPresetDialogBinding?.audioGuideValue?.text = getString(
                R.string.audio_file_display_info,
                sessionPreset.audioGuideSoundTitle,
                sessionPreset.audioGuideSoundArtistName,
                sessionPreset.audioGuideSoundAlbumName
            )
            updateDurationZone(sessionPreset)
        }
        audioSessionPresetDialogBinding?.audioGuideZone?.setOnClickListener {
            openSystemFilePicker(sessionPreset.audioGuideSoundUriString)
        }
    }

    private fun updateDurationZone(sessionPreset: SessionPreset) {
        audioSessionPresetDialogBinding?.durationZone?.visibility = VISIBLE
        val durationValueNormalColor = MaterialColors.getColor(
            requireContext(),
            R.attr.colorOnSurface,
            Color.BLACK
        )
        val durationZone = audioSessionPresetDialogBinding?.durationZone
        val durationValue = audioSessionPresetDialogBinding?.durationValue
        if (durationZone == null || durationValue == null) return
        durationValue.setTextColor(durationValueNormalColor)
        val audioSessionDurationUnknown = sessionPreset.duration == -1L
        if (audioSessionDurationUnknown) {
            // we have an audio file for the session, but could not retrieve its duration through its metadata => we need the user to input it manually
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