/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.databinding.DialogBottomSelectListDividerBinding
import fr.shining_cat.everyday.commons.databinding.DialogBottomSelectListItemBinding
import kotlin.properties.Delegates

class SelectListAdapter(
    private val logger: Logger
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOG_TAG = SelectListAdapter::class.java.name

    private var selectListAdapterListener: SelectListAdapterListener? = null

    interface SelectListAdapterListener {

        fun onOptionSelected(selectedPosition: Int)
    }

    fun setSelectListAdapterListener(listener: SelectListAdapterListener) {
        this.selectListAdapterListener = listener
    }

    var optionsLabels: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // This keeps track of the currently selected position
    var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
        if (newPos in optionsLabels.indices) {
            logger.d(LOG_TAG, "selectedPosition updated to $newPos, old position was $oldPos")
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    fun forceInitialSelectedOption(selectedPosition: Int) {
        if (selectedPosition > -1 && selectedPosition < optionsLabels.size) {
            this.selectedPosition = selectedPosition
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (optionsLabels[position].isNotBlank()) ItemsTypes.NORMAL_ITEM.value
        else ItemsTypes.DIVIDER.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ItemsTypes.NORMAL_ITEM.value -> createNormalItemViewHolder(parent)
        ItemsTypes.DIVIDER.value -> createDividerViewHolder(parent)
        else -> throw IllegalArgumentException()
    }

    private fun createNormalItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindingNormalItemView = DialogBottomSelectListItemBinding.inflate(layoutInflater)
        return SelectListNormalItemViewHolder(bindingNormalItemView, logger)
    }

    private fun createDividerViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindingDivider = DialogBottomSelectListDividerBinding.inflate(layoutInflater)
        return SelectListDividerViewHolder(bindingDivider, logger)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in optionsLabels.indices) {
            if (holder.itemViewType == ItemsTypes.NORMAL_ITEM.value) {
                bindNormalItemViewHolder(holder as SelectListNormalItemViewHolder, position)
            }
            //no binding necessary for divider
        }
    }

    private fun bindNormalItemViewHolder(
        selectListNormalItemViewHolder: SelectListNormalItemViewHolder,
        position: Int
    ) {
        selectListNormalItemViewHolder.bindView(
            optionsLabels[position],
            position == selectedPosition
        )
        selectListNormalItemViewHolder.itemView.setOnClickListener {
            logger.d(LOG_TAG, "click on select item: position = $position")
            selectedPosition = position
            selectListAdapterListener?.onOptionSelected(selectedPosition)
        }
    }


    override fun getItemCount(): Int = optionsLabels.size

}

enum class ItemsTypes(val value: Int) {
    NORMAL_ITEM(0),
    DIVIDER(1)
}

class SelectListNormalItemViewHolder(
    private val dialogBottomSelectListItemBinding: DialogBottomSelectListItemBinding,
    val logger: Logger
) : RecyclerView.ViewHolder(dialogBottomSelectListItemBinding.root) {

    private val LOG_TAG = SelectListNormalItemViewHolder::class.java.name

    fun bindView(label: String, selected: Boolean) {
        dialogBottomSelectListItemBinding.selectListItemLabel.text = label
        dialogBottomSelectListItemBinding.selectListItemRadioBtn.isChecked = selected
    }
}

class SelectListDividerViewHolder(
    dialogBottomSelectListDividerBinding: DialogBottomSelectListDividerBinding,
    val logger: Logger
) : RecyclerView.ViewHolder(dialogBottomSelectListDividerBinding.root)