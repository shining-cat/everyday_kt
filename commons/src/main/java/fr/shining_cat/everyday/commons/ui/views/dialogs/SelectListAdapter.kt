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
) : RecyclerView.Adapter<SelectListAdapter.SelectListViewHolder>() {

    private val LOG_TAG = SelectListAdapter::class.java.name

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

    fun forceInitialSelectedPosition(selectedPosition: Int){
        this.selectedPosition = selectedPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SelectListViewHolder(
        layoutInflater.inflate(
            R.layout.dialog_bottom_select_list_item,
            parent,
            false
        ),
        logger
    )
    }

    override fun onBindViewHolder(holder: SelectListViewHolder, position: Int) {
        if (position in optionsLabels.indices) {
            holder.bindView(optionsLabels[position], position == selectedPosition)
            holder.itemView.setOnClickListener {
                logger.d(LOG_TAG, "click on select item: position = $position")
                selectedPosition = position }
        }
    }

    override fun getItemCount(): Int = optionsLabels.size

    inner class SelectListViewHolder(
        itemView: View,
        val logger: Logger
    ) : RecyclerView.ViewHolder(itemView) {

        private val LOG_TAG = SelectListViewHolder::class.java.name

        fun bindView(label: String, selected: Boolean) {
            itemView.select_list_item_label.text = label
            itemView.select_list_item_radio_btn.isChecked = selected
        }
    }
}