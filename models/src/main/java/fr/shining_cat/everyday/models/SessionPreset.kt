package fr.shining_cat.everyday.models

data class SessionPreset(
    var id: Long,
    var duration: Long,
    var startAndEndSoundUri: String,
    var intermediateIntervalLength: Long,
    var startCountdownLength: Long,
    var intermediateIntervalRandom: Boolean,
    var intermediateIntervalSoundUri: String,
    var audioGuideSoundUri: String,
    var vibration: Boolean,
    var lastEditTime: Long,
    var sessionTypeId: Long
)