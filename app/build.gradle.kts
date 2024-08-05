plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.samuelokello.kazihub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.samuelokello.kazihub"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint{
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.libraries.places:places:3.5.0")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    implementation("androidx.datastore:datastore-core-android:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core-jvm:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //extended icons
    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Compose Destinations
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.10.1")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.10.1")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.51")
    ksp ("com.google.dagger:hilt-android-compiler:2.51")
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    ksp ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")

    implementation ("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    //Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //mockito
    implementation("org.mockito:mockito-core:4.0.0")

    // Google Sign In SDK
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Firebase SDK
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase UI Library
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //swipe refresh
    implementation("com.google.accompanist:accompanist-swiperefresh: 0.34.0")

    implementation("androidx.core:core-splashscreen:1.0.1")
}