plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    id ("kotlin-kapt")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
}

android {
    namespace = "com.deletech.malakoff"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.deletech.malakoff"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Image Loading library
    implementation(libs.coil.compose)
    // used for making api requests
    implementation( libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.androidx.room.runtime)
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.maps)
    implementation (libs.androidx.browser)
    implementation(libs.androidx.appcompat)
    kapt (libs.androidx.room.compiler)
    annotationProcessor (libs.androidx.room.compiler)
    api (libs.kotlinx.coroutines.android)
    api (libs.kotlinx.coroutines.core)
    implementation (libs.androidx.constraintlayout)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.accompanist.systemuicontroller)
    // For preference Datastore
    implementation (libs.androidx.datastore.preferences)
    //kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    //dependency injection with hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt (libs.androidx.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}