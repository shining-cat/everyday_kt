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
object ApplicationSpecs {

    const val id = "fr.shining_cat.everyday"
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31
}

object Releases {

    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {

    const val app = ":app"
    const val locale = ":locale"
    const val models = ":models"
    const val repositories = ":repositories"
    const val commons = ":commons"
    const val interModulesNavigation = ":navigation"
    const val screens = ":screens"
    const val domain = ":domain"
    const val settings = ":settings"
    const val session = ":session"
    const val testutils = ":testutils"
}
