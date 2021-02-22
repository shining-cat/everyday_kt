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

package fr.shining_cat.everyday.commons

class Constants {

    companion object {

        // Error codes
        const val ERROR_CODE_EXCEPTION = -100
        const val ERROR_CODE_NO_RESULT = -101
        const val ERROR_CODE_DATABASE_OPERATION_FAILED = -102

        //error messages, these are internal resources, they will never be shown to the user
        const val ERROR_MESSAGE_NO_RESULT = "fetching from database did not return any result"
        const val ERROR_MESSAGE_UPDATE_FAILED = "Error while updating in database"
        const val ERROR_MESSAGE_READ_FAILED = "Error while reading in database"
        const val ERROR_MESSAGE_COUNT_FAILED = "Error while counting in database"
        const val ERROR_MESSAGE_INSERT_FAILED = "Error while inserting in database"
        const val ERROR_MESSAGE_DELETE_FAILED = "Error while deleting in database"

        //Constants
        const val SPLASH_MIN_DURATION_MILLIS = 1000L
    }
}