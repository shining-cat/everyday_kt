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

object Versions {

    const val kotlin = "1.6.10"
    const val koin = "3.1.5"
    const val room = "2.4.2"
    const val lifecycle = "2.4.1"
    const val navigation = "2.4.1"
}

object Libraries {

    //gradle plugin to manage dependencies updates: see gradle task
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.36.0"

    //koin
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"

    //debugging stetho
    const val stetho = "com.facebook.stetho:stetho:1.6.0"

    //AirBnB Lottie
    const val lottie = "com.airbnb.android:lottie:5.0.3"
}

object KotlinLibraries {

    const val kotlinJdk7 = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

}

object AndroidLibraries {

    const val gradle = "com.android.tools.build:gradle:7.0.0"

    //
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val coreRunTime = "androidx.arch.core:core-runtime:2.1.0"

    //
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val activity_ktx = "androidx.activity:activity-ktx:1.2.0"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:1.3.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"

    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    //JETPACK navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    //Material
    const val material = "com.google.android.material:material:1.5.0"

    //Lifecycle
    const val lifecycle_commons_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //preferences
    const val jetpack_preferences = "androidx.preference:preference-ktx:1.2.0"

    //Java 8+ API desugaring support
    const val desugaring_support = "com.android.tools:desugar_jdk_libs:1.1.5"
}

object TestLibraries {

    const val mockk = "io.mockk:mockk:1.12.3"
    const val junit = "junit:junit:4.13.2"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
    const val robolectric = "org.robolectric:robolectric:4.7.3"
}

object AndroidTestLibraries {

    const val core_testing = "androidx.arch.core:core-testing:2.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    const val junit = "androidx.test.ext:junit:1.1.3"
    const val runner = "androidx.test:runner:1.4.0"
    const val room_testing = "androidx.room:room-testing:2.2.1"
}