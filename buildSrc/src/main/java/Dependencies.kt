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

object ApplicationId {

    const val id = "fr.shining_cat.everyday"
}

object Versions {

    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 28
    const val kotlin = "1.4.30"
    const val benManesVersionsPlugin = "0.36.0"
    const val jacoco = "0.8.6"
    const val room = "2.2.6"
    const val lifecycle = "2.3.0"
    const val navigation = "2.3.3"
    const val ktlint = "0.40.0"
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
    const val testutils = ":testutils"
}

object Libraries {

    //gradle plugin to manage dependencies updates: see gradle tasks help>dependencyUpdates
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.benManesVersionsPlugin}"

    const val jacocoPlugin = "org.jacoco:org.jacoco.core:${Versions.jacoco}"

    //koin
    const val koin = "org.koin:koin-android:2.0.1"
    const val koin_viewmodel = "org.koin:koin-android-viewmodel:2.0.1"

    //debugging stetho
    const val stetho = "com.facebook.stetho:stetho:1.5.1"

    //AirBnB Lottie
    const val lottie = "com.airbnb.android:lottie:3.6.0"
}

object KotlinLibraries {

    //stay at the IDE gradle plugin expected version of kotlin for the present gradle version to avoid gradle unwarranted compatibility warning
    const val kotlinJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object AndroidLibraries {

    const val gradle = "com.android.tools.build:gradle:4.1.2"
    const val activity_ktx = "androidx.activity:activity-ktx:1.2.0"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:1.3.0"
    const val appCompat = "androidx.appcompat:appcompat:1.2.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val coreRunTime = "androidx.arch.core:core-runtime:2.1.0"
    const val materialViews = "com.google.android.material:material:1.2.1"

    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    //JETPACK navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    //Material
    const val material = "com.google.android.material:material:1.3.0"

    //Lifecycle
    const val lifecycle_commons_java8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //preferences
    const val jetpack_preferences = "androidx.preference:preference-ktx:1.1.1"
}

object TestLibraries {

    const val mockk = "io.mockk:mockk:1.10.5"
    const val junit = "junit:junit:4.12"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
    const val robolectric = "org.robolectric:robolectric:4.2.1"
}

object AndroidTestLibraries {

    const val core_testing = "androidx.arch.core:core-testing:2.0.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val junit = "androidx.test.ext:junit:1.1.3-alpha04"
    const val room_testing = "androidx.room:room-testing:2.2.1"
    const val runner = "androidx.test:runner:1.4.0-alpha04"
}