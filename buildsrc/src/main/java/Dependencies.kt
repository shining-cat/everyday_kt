object ApplicationId {
    val id = "fr.shining_cat.everyday_kt"
}

object Versions {
    val kotlin = "1.3.50"
    val gradle = "3.5.0"
    val room = "2.1.0"
    val androidTestRunner = "1.1.2-alpha02"
    val androidJunit = "1.1.0"
    val livedata = "2.2.0-alpha03"
    val appcompat = "1.1.0"
    val constraintLayout = "1.1.3"

    val compileSdk = 28
    val minSdk = 21
    val targetSdk = 28
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Modules {
    val app = ":app"
    val localdata = ":localdata"
    val model = ":model"
    val repository = ":repository"
    val utils = ":utils"
    val testutils = ":testutils"
}

object Libraries {
    // ROOM
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    //GRADLE
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object AndroidLibraries {
    val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object TestLibraries {
    // ANDROID TEST
    val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    // UNIT TESTS
    val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
}