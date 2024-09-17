plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKsp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.safeNavArgsKotlin)
}

android {
    namespace = "com.example.m_news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.m_news"
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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.ext.compiler)
    ksp(libs.hilt.compiler)

    //Room
    implementation(libs.room.runtime)
    implementation(libs.room)
    ksp(libs.room.compiler)

    //Gson
    implementation(libs.gson)

    //Coroutines
    implementation(libs.kotlinx.coroutines)

    //Image Loading
    implementation(libs.picasso)

    //Lottie Animation
    implementation(libs.lottie)

    //Kotlinx-serialization
    implementation(libs.kotlinx.serialization)

    //ktor
    implementation(libs.client.android)
    implementation(libs.client.logging)
    implementation(libs.client.auth)
    implementation(libs.client.serialization)
    implementation(libs.client.content.negotiation)
    implementation(libs.client.cio)
    implementation(libs.ktor.serialization)
}