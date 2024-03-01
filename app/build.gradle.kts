plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.realm.kotlin")
}

android {
    namespace = "com.example.pokedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokedex"
        minSdk = 26
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
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dataBinding {
        this.enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //new libreries
    implementation ("com.airbnb.android:lottie:3.0.7")
    implementation ("com.github.pwittchen:reactivenetwork-rx2:3.0.8")
    implementation ("io.reactivex.rxjava2:rxkotlin:2.0.2")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.github.pwittchen:reactivenetwork-rx2:3.0.8")
    implementation ("com.jakewharton.rxbinding3:rxbinding:3.0.0")
    implementation ("com.jakewharton.rxbinding2:rxbinding:2.1.1")
    implementation ("com.jakewharton.rxbinding2:rxbinding-design:2.1.1")
    implementation ("com.jakewharton.rxbinding2:rxbinding-kotlin:2.1.1")
    implementation ("com.github.simformsolutions:SSCustomBottomNavigation:3.4")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")
    implementation ("io.reactivex.rxjava2:rxkotlin:2.0.2")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.squareup.retrofit2:retrofit:2.5.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.13.0")
}