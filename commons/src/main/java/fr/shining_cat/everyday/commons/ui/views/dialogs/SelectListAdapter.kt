package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import kotlinx.android.synthetic.main.dialog_bottom_select_list_item.view.*
import kotlin.properties.Delegates

class SelectListAdapter(
    private val logger: Logger
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOG_TAG = SelectListAdapter::class.java.name

    private var selectListAdapterListener: SelectListAdapterListener? =
        null

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
    var selectedPosition by Delegates.observable(-1) { property, oldPos, newPos ->
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
        return SelectListNormalItemViewHolder(
            layoutInflater.inflate(
                R.layout.dialog_bottom_select_list_item,
                parent,
                false
            ),
            logger
        )
    }

    private fun createDividerViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SelectListDividerViewHolder(
            layoutInflater.inflate(
                R.layout.dialog_bottom_select_list_divider,
                parent,
                false
            ),
            logger
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in optionsLabels.indices) {
            if (holder.itemViewType == ItemsTypes.NORMAL_ITEM.value) bindNormalItemViewHolder(
                holder as SelectListNormalItemViewHolder,
                position
            )
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
    itemView: View,
    val logger: Logger
) : RecyclerView.ViewHolder(itemView) {

    private val LOG_TAG = SelectListNormalItemViewHolder::class.java.name

    fun bindView(label: String, selected: Boolean) {
        itemView.select_list_item_label.text = label
        itemView.select_list_item_radio_btn.isChecked = selected
    }
}

class SelectListDividerViewHolder(
    itemView: View,
    val logger: Logger
) : RecyclerView.ViewHolder(itemView) {

    private val LOG_TAG = SelectListDividerViewHolder::class.java.name

    fun bindView(label: String, selected: Boolean) {
        //nothing to do for divider
    }
}