object ApplicationId {
    const val id = "fr.shining_cat.everyday"
}

object Versions {
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 28
    const val jacoco = "0.8.5"
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
    const val screens = ":screens"
    const val domain = ":domain"
    const val testutils = ":testutils"
}

object Libraries {
    //gradle plugin to manage dependencies updates: see gradle tasks help>dependencyUpdates
    const val gradleVersionsPlugin =  "com.github.ben-manes:gradle-versions-plugin:0.27.0"
    //koin
    const val koin = "org.koin:koin-android:2.0.1"
    const val koin_viewmodel = "org.koin:koin-android-viewmodel:2.0.1"
    //debugging stetho
    const val stetho = "com.facebook.stetho:stetho:1.5.1"
}

object KotlinLibraries {
    //stay at the IDE gradle plugin expected version of kotlin for the present gradle version to avoid gradle unwarranted compatibility warning
    const val kotlinJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
}

object AndroidLibraries {
    const val gradle = "com.android.tools.build:gradle:3.5.2"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc02"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta3"
    const val coreRunTime = "androidx.arch.core:core-runtime:2.1.0"
    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:2.2.2"
    const val roomRunTime = "androidx.room:room-runtime:2.2.2"
    const val roomKtx = "androidx.room:room-ktx:2.2.2"

    const val navigation_fragment = "androidx.navigation:navigation-fragment:2.2.2"
    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:2.2.2"
    const val navigation_ui = "androidx.navigation:navigation-ui:2.2.2"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:2.2.2"

}

object TestLibraries {
    // ANDROID TEST
    const val androidTestRunner = "androidx.test:runner:1.3.0-alpha02"
    const val archCoreTest = "androidx.arch.core:core-testing:2.1.0"
    // COROUTINE
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.1"
    // UNIT TESTS
    const val junit = "androidx.test.ext:junit:1.1.2-alpha02"
    //Mockito for kotlin
    const val mockitoCore = "org.mockito:mockito-core:3.1.0"
    const val mockitoInline = "org.mockito:mockito-inline:3.1.0"

}