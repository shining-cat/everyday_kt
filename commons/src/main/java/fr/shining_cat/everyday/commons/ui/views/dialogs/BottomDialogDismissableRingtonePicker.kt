package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.R
import java.util.*

class BottomDialogDismissableRingtonePicker : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val INITIAL_SELECTION_URI_ARG = "initial_selection_uri_argument"
    private val SILENCE_ARG = "silence_argument"
    private val RINGTONES_ASSETS_ARG = "ringtones_assets_argument"
    private val RINGTONES_TITLES_ARG = "ringtones_titles_argument"
    private var bottomDialogDismissableRingtonePickerListener: BottomDialogDismissableRingtonePickerListener? =
        null

    interface BottomDialogDismissableRingtonePickerListener {
        fun onDismissed()
        fun onValidateRingtoneSelected(selectedRingtoneUri: String, selectedRingtoneName: String)
    }

    fun setBottomDialogDismissableRingtonePickerListener(listener: BottomDialogDismissableRingtonePickerListener) {
        this.bottomDialogDismissableRingtonePickerListener = listener
    }

    companion object {
        fun newInstance(
            title: String,
            initialSelectionUri: String,
            showSilenceChoice: Boolean = false,
            ringtonesAssetsNames: Array<String> = emptyArray(),
            ringtonesDisplayNames: Array<String> = emptyArray()
        ): BottomDialogDismissableRingtonePicker =
            BottomDialogDismissableRingtonePicker()
                .apply {
                    arguments = Bundle().apply {
                        putString(TITLE_ARG, title)
                        putString(INITIAL_SELECTION_URI_ARG, initialSelectionUri)
                        putBoolean(SILENCE_ARG, showSilenceChoice)
                        putStringArray(RINGTONES_ASSETS_ARG, ringtonesAssetsNames)
                        putStringArray(RINGTONES_TITLES_ARG, ringtonesDisplayNames)
                    }
                }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_big_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "") ?: ""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissableRingtonePickerListener?.onDismissed()
            dismiss()
        }
        //TODO: build list of options: start with silent if set to true, then assets ringtones, the device ringtones
        //TODO: we need a kind of divider between these 3 sets
        //TODO: set the selected option to initialSelectionUri, if empty && silence allowed set to silence else set to nothing

    }

    private fun transmitSelectedRingtone(
        selectedRingtoneUri: String,
        selectedRingtoneName: String
    ) {
        bottomDialogDismissableRingtonePickerListener?.onValidateRingtoneSelected(
            selectedRingtoneUri,
            selectedRingtoneName
        )
        dismiss()
    }

    private fun getSilentRingtone(): Map<String, Uri> {
        val list: MutableMap<String, Uri> = TreeMap()
        list[getString(R.string.silence)] = Uri.parse("")
        return list
    }

    private fun getDeviceRingtones(): Map<String, Uri> {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION)
        val cursor = ringtoneManager.cursor
        val list: MutableMap<String, Uri> = TreeMap()
        while (cursor.moveToNext()) {
            val notificationTitle =
                cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val notificationUri =
                ringtoneManager.getRingtoneUri(cursor.position)
            list[notificationTitle] = notificationUri
        }
        return list
    }

    private fun getAssetsRingtones(
        ringtonesAssetsNames: Array<String>,
        ringtonesDisplayNames: Array<String>
    ): Map<String, Uri> {
        val list: MutableMap<String, Uri> = TreeMap()
        for ((index, ringtoneAssetName) in ringtonesAssetsNames.withIndex()) {
            context?.let { list[ringtonesDisplayNames[index]] = uriFromRaw(it, ringtoneAssetName) }
        }
        return list
    }

    private fun uriFromRaw(context: Context, assetName: String): Uri {
        val resId = context.resources?.getIdentifier(assetName, "raw", context.packageName) ?: -1
        return if (resId != -1) {
            Uri.parse("android.resource://" + context.packageName + "/" + resId)
        } else {
            Uri.parse("")
        }
    }

}