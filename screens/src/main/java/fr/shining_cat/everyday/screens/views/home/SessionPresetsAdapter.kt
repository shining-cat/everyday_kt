package fr.shining_cat.everyday.screens.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.databinding.ItemAudioSessionPresetViewHolderBinding
import fr.shining_cat.everyday.screens.databinding.ItemTimedSessionPresetViewHolderBinding
import java.security.InvalidParameterException

class SessionPresetsAdapter(
    private val logger: Logger
) : ListAdapter<SessionPreset, AbstractSessionPresetViewHolder>(SessionPresetDiffCallback()) {

    private val LOG_TAG = SessionPresetsAdapter::class.java.name

    private enum class SessionPresetViewType(val value: Int) {
        AUDIO_SESSION(0), TIMED_SESSION(1)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.audioGuideSoundUri.isBlank()) {
            SessionPresetViewType.TIMED_SESSION.value
        }
        else {
            SessionPresetViewType.AUDIO_SESSION.value
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractSessionPresetViewHolder {
        return when (viewType) {
            SessionPresetViewType.TIMED_SESSION.value -> {
                TimedSessionPresetViewHolder(
                    ItemTimedSessionPresetViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    logger
                )
            }
            SessionPresetViewType.AUDIO_SESSION.value -> {
                AudioSessionPresetViewHolder(
                    ItemAudioSessionPresetViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    logger
                )
            }
            else -> {
                logger.e(
                    LOG_TAG,
                    "onCreateViewHolder::invalid module type"
                )
                throw InvalidParameterException()
            }
        }
    }

    override fun onBindViewHolder(
        holder: AbstractSessionPresetViewHolder,
        position: Int
    ) {
        val cornerStripHolder = getItem(position)
        holder.bindView(cornerStripHolder)
    }

    private class SessionPresetDiffCallback : DiffUtil.ItemCallback<SessionPreset>() {

        override fun areItemsTheSame(
            oldItem: SessionPreset,
            newItem: SessionPreset
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: SessionPreset,
            newItem: SessionPreset
        ): Boolean = oldItem == newItem
    }
}

