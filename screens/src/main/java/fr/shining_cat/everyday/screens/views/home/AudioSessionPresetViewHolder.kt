package fr.shining_cat.everyday.screens.views.home

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.databinding.ItemAudioSessionPresetViewHolderBinding

class AudioSessionPresetViewHolder(
    private val itemAudioSessionPresetViewHolderBinding: ItemAudioSessionPresetViewHolderBinding,
    private val logger: Logger
) : AbstractSessionPresetViewHolder(
    itemAudioSessionPresetViewHolderBinding.root,
    logger
) {

    private val LOG_TAG = AudioSessionPresetViewHolder::class.java.name

    override fun bindView(sessionPreset: SessionPreset) {
        itemAudioSessionPresetViewHolderBinding.audioSessionPresetItemFileName.text =
            "TODO: get file info from metadata using the file URI in SessionPreset"
    }

}