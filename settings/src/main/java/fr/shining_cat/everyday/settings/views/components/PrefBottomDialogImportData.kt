package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleMessageAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogImportData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogImportData::class.java.name

    init {
        title = context.getString(R.string.importSessionsPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val setImportDataBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                title = context.getString(R.string.importSessionsPreference_dialog_title),
                message = context.getString(R.string.importSessionsPreference_dialog_message),
                confirmButtonLabel = context.getString(R.string.importSessionsPreference_dialog_confirm_button)
            )
        setImportDataBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    //TODO: call import sessions UseCase
                    logger.e(
                        LOG_TAG,
                        "openImportSessionsDialog::onConfirmButtonClicked::TODO: call import sessions Usecase"
                    )
                }

            })
        setImportDataBottomSheetDialog.show(fragmentManager, "openImportSessionsDialog")
    }
}