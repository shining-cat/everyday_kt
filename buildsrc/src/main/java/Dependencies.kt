object ApplicationId {
    val id = "fr.shining_cat.everyday_kt"
}

object Versions {
    val kotlin = "1.3.50"
    val gradle = "3.5.0"
    val room = "2.1.0"

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
    val appCompat = "com.android.support:appcompat-v7:27.1.1"
    val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-alpha03"
}