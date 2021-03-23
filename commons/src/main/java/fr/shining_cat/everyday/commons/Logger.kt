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

import android.util.Log

class Logger {

    fun d(
        tag: String,
        msg: String
    ) {
        if (BuildConfig.DEBUG) {
            Log.d(
                tag,
                msg
            )
        }
    }

    fun e(
        tag: String,
        msg: String,
        throwable: Throwable? = null
    ) {
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(
                    tag,
                    msg
                )
            } else {
                Log.e(
                    tag,
                    msg,
                    throwable
                )
            }
        }
    }
}