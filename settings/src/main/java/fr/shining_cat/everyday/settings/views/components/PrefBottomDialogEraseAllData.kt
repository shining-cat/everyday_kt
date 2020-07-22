package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogEraseAllData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogEraseAllData::class.java.name

    init {
        title = context.getString(R.string.eraseDataPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val eraseAllDataBottomSheetDialog = BottomDialogDismissibleBigButton.newInstance(
            title = context.getString(R.string.confirm_suppress),
            bigButtonLabel = context.getString(R.string.generic_string_DELETE)
        )
        eraseAllDataBottomSheetDialog.setBottomDialogDismissibleBigButtonListener(object :
            BottomDialogDismissibleBigButton.BottomDialogDismissibleBigButtonListener {
            override fun onDismissed() {
                //nothing to do here
            }

            override fun onBigButtonClicked() {
                //TODO: call Erase all data Usecase
                logger.e(
                    LOG_TAG,
                    "openEraseAllDataDialog::onBigButtonClicked::TODO: implement second confirmation in mid-screen \"popup\" Dialog, then call Erase all data Usecase"
                )
            }

        })
        eraseAllDataBottomSheetDialog.show(fragmentManager, "openEraseAllDataDialog")
    }
}