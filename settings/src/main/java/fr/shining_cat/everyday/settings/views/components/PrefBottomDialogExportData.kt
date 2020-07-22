package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleEditTextAndConfirm
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleMessageAndConfirm
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogExportData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogExportData::class.java.name

    init {
        title = context.getString(R.string.exportSessionsPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val setExportDataBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                title = context.getString(R.string.exportSessionsPreference_dialog_title),
                message = context.getString(R.string.exportSessionsPreference_dialog_message),
                confirmButtonLabel = context.getString(R.string.exportSessionsPreference_dialog_confirm_button)
            )
        setExportDataBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    //TODO: call export sessions UseCase
                    logger.e(
                        LOG_TAG,
                        "openExportSessionsDialog::onConfirmButtonClicked::TODO: call export sessions Usecase"
                    )
                }

            })
        setExportDataBottomSheetDialog.show(fragmentManager, "openExportSessionsDialog")
    }
}