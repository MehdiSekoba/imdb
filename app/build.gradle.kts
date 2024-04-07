plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.safeargs.plugin)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.mehdisekoba.imdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mehdisekoba.imdb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.swiperefreshlayout)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    // Dagger - Hilt
    implementation(libs.hilt)
    ksp(libs.hiltcompiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // OkHTTP client
    implementation(libs.okhttp)
    implementation(libs.interceptor)
    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    // Lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    // Image Loading
    implementation(libs.coil)
    // Gson
    implementation(libs.google.gson)
    // Calligraphy
    implementation(libs.calligraphy)
    implementation(libs.viewpump)
    // shimmer
    implementation(libs.shimmer.recyclerview.x)
    // lottie
    implementation(libs.lottie)
    // datastore
    implementation(libs.androidx.datastore.preferences)
    //  video player
    implementation(libs.youtubeplayer)
    // Other
    implementation(libs.dynamicsizes)
}
