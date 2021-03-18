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

package fr.shining_cat.everyday.domain

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import fr.shining_cat.everyday.models.AudioFileMetadata

class FileMetadataRetrieveUseCase(
    val logger: Logger
) {

    private val LOG_TAG = FileMetadataRetrieveUseCase::class.simpleName

    fun execute(
        context: Context,
        fileUri: Uri
    ): AudioFileMetadata {

        val resources = context.resources
        //default values
        var fileName = context.getString(R.string.unknown_audio_file)
        var artist = context.getString(R.string.unknown_artist)
        var album = context.getString(R.string.unknown_album)
        var durationMs = -1L

        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(
            context,
            fileUri
        )
        //get audio file display name :
        val fileNameStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        if (fileNameStr.isNullOrBlank()) {
            Log.e(
                LOG_TAG,
                "retrieveMetadata::MediaMetadataRetriever could not retrieve METADATA_KEY_TITLE :: fileNameStr is NULL or EMPTY!!"
            )
        }
        else {
            fileName = fileNameStr
        }
        //get audio file artist name
        val artistNameStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        if (artistNameStr.isNullOrBlank()) {
            Log.e(
                LOG_TAG,
                "retrieveMetadata::MediaMetadataRetriever could not retrieve METADATA_KEY_ARTIST :: artistNameStr is NULL or EMPTY!!"
            )
        }
        else {
            artist = artistNameStr
        }
        //get audio file album name
        val albumNameStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
        if (albumNameStr.isNullOrBlank()) {
            Log.e(
                LOG_TAG,
                "retrieveMetadata::MediaMetadataRetriever could not retrieve METADATA_KEY_ALBUM :: artistNameStr is NULL or EMPTY!!"
            )
        }
        else {
            album = albumNameStr
        }
        //get audio file duration :
        val durationMsStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        if (durationMsStr.isNullOrBlank()) {
            Log.e(
                LOG_TAG,
                "retrieveMetadata::MediaMetadataRetriever could not retrieve METADATA_KEY_DURATION :: durationStr is NULL or EMPTY!!"
            )
        }
        else {
            durationMs = durationMsStr.toLong()
        }
        return AudioFileMetadata(
            fileName,
            artist,
            album,
            durationMs
        )
    }
}