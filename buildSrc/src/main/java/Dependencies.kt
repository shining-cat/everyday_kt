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

    const val koin = "2.2.2"
    const val kotlin = "1.4.32"
    const val benManesVersionsPlugin = "0.36.0"
    const val jacoco = "0.8.6"
    const val room = "2.2.6"
    const val lifecycle = "2.3.0"
    const val navigation = "2.3.4"
    const val ktlint = "0.41.0"
    const val detekt = "1.1.1"
}

object Libraries {

    //gradle plugin to manage dependencies updates: see gradle tasks help>dependencyUpdates
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.benManesVersionsPlugin}"

    const val jacocoPlugin = "org.jacoco:org.jacoco.core:${Versions.jacoco}"

    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"

    //koin
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_viewmodel = "io.insert-koin:koin-android-viewmodel:${Versions.koin}"

    //debugging stetho
    const val stetho = "com.facebook.stetho:stetho:1.5.1"

    //AirBnB Lottie
    const val lottie = "com.airbnb.android:lottie:3.6.1"
}

object KotlinLibraries {

    const val kotlinJdk7 = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

}

object AndroidLibraries {

    const val gradle = "com.android.tools.build:gradle:4.0.2"//keep at 4.0.2, because 4.1.3 still breaks jacoco tests reports task

    //
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val coreRunTime = "androidx.arch.core:core-runtime:2.1.0"

    //
    const val appCompat = "androidx.appcompat:appcompat:1.2.0"
    const val activity_ktx = "androidx.activity:activity-ktx:1.2.0"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:1.3.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

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
    const val material = "com.google.android.material:material:1.3.0"

    //Lifecycle
    const val lifecycle_commons_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //preferences
    const val jetpack_preferences = "androidx.preference:preference-ktx:1.1.1"
}

object TestLibraries {

    const val mockk = "io.mockk:mockk:1.11.0"
    const val junit = "junit:junit:4.13.2"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3"
    const val robolectric = "org.robolectric:robolectric:4.5.1"
}

object AndroidTestLibraries {

    const val core_testing = "androidx.arch.core:core-testing:2.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    const val junit = "androidx.test.ext:junit:1.1.2"
    const val room_testing = "androidx.room:room-testing:2.2.1"
    const val runner = "androidx.test:runner:1.3.0"
}