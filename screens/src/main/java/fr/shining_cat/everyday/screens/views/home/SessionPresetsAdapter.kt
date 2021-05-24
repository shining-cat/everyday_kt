package fr.shining_cat.everyday.screens.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.databinding.ItemAudioSessionPresetViewHolderBinding
import fr.shining_cat.everyday.screens.databinding.ItemTimedSessionPresetViewHolderBinding
import fr.shining_cat.everyday.screens.databinding.ItemUnknownSessionPresetViewHolderBinding
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.AbstractSessionPresetViewHolder
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.AudioFreeSessionPresetViewHolder
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.AudioSessionPresetViewHolder
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.TimedFreeSessionPresetViewHolder
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.TimedSessionPresetViewHolder
import fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders.UnknownSessionPresetViewHolder
import java.security.InvalidParameterException

class SessionPresetsAdapter(
    private val logger: Logger
) : ListAdapter<SessionPreset, AbstractSessionPresetViewHolder>(SessionPresetDiffCallback()) {

    private val LOG_TAG = SessionPresetsAdapter::class.java.name

    private enum class SessionPresetViewType(val value: Int) {
        AUDIO_SESSION(0),
        AUDIO_FREE_SESSION(1),
        TIMED_SESSION(2),
        TIMED_FREE_SESSION(3),
        UNKNOWN_SESSION(4)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SessionPreset.AudioSessionPreset -> SessionPresetViewType.AUDIO_SESSION.value
            is SessionPreset.AudioFreeSessionPreset -> SessionPresetViewType.AUDIO_FREE_SESSION.value
            is SessionPreset.TimedSessionPreset -> SessionPresetViewType.TIMED_SESSION.value
            is SessionPreset.TimedFreeSessionPreset -> SessionPresetViewType.TIMED_FREE_SESSION.value
            else -> SessionPresetViewType.UNKNOWN_SESSION.value
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
            SessionPresetViewType.TIMED_FREE_SESSION.value -> {
                TimedFreeSessionPresetViewHolder(
                    ItemTimedSessionPresetViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    logger
                )
            }
            SessionPresetViewType.AUDIO_FREE_SESSION.value -> {
                AudioFreeSessionPresetViewHolder(
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
                UnknownSessionPresetViewHolder(
                    ItemUnknownSessionPresetViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    logger
                )
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