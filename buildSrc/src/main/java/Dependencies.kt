object ApplicationId {
    val id = "fr.shining_cat.everyday_kt"
}

object Versions {
    const val kotlin = "1.3.50"
    const val gradle = "3.5.1"
    const val googleServices = "4.3.2"
    const val crashlyticsPlugin = "1.31.2"
    const val analytics = "17.2.1"
    const val crashlytics = "2.10.1"
    const val room = "2.1.0"
    const val androidTestRunner = "1.1.2-alpha02"
    const val androidJunit = "1.1.0"
    const val livedata = "2.2.0-alpha03"
    const val appcompat = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val coreRunTime = "2.1.0"
    const val archCoreTest = "2.0.0"
    const val coroutines = "1.3.1"
    const val mockitoCore = "2.25.0"
    const val mockitoInline = "2.8.47"

    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 28
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val localdata = ":localdata"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    const val testutils = ":testutils"
    const val navigation = ":navigation"
    const val home = ":home"
    const val settings = ":settings"
    const val session = ":session"
    const val rewards = ":rewards"
    const val statistics = ":statistics"
}

object Libraries {
    //GRADLE
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    //Google Services
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    //Crashlytics
    const val crashlyticsPlugin = "io.fabric.tools:gradle:${Versions.crashlyticsPlugin}"
    //
    const val analytics = "com.google.firebase:firebase-analytics:${Versions.analytics}"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    //https://github.com/ben-manes/gradle-versions-plugin
    const val gradleVersionsPlugin =  "com.github.ben-manes:gradle-versions-plugin:0.27.0"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object AndroidLibraries {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coreRunTime = "androidx.arch.core:core-runtime:${Versions.coreRunTime}"
}

object TestLibraries {
    // ANDROID TEST
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    // COROUTINE
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    // UNIT TESTS
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    //Mockito for kotlin
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"

}