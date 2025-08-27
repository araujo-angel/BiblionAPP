plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    //alias(libs.plugins.ksp)
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
}

android {
    namespace = "com.example.biblion"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.biblion"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Adicione esta configuração para o Room
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    // Adicione esta configuração para Compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

val roomVersion = "2.7.2"

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.foundation:foundation:1.7.6")
    implementation("androidx.compose.material:material:1.7.6")
    implementation("androidx.compose.ui:ui-text:1.7.6")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.6") // Atualizada para versão consistente
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7") // Atualizada
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha12")

    // Firebase
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0") // Atualizada
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") // Atualizada

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.6.0") // Atualizada
    implementation("com.github.bumptech.glide:glide:4.16.0") // Atualizada

    // Pager & Indicators
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")

    // Dependency Injection
    implementation("io.insert-koin:koin-android:4.0.3")
    implementation("io.insert-koin:koin-androidx-compose:4.0.3")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation(libs.androidx.constraintlayout.compose.android)
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // JSON
    implementation("com.google.code.gson:gson:2.11.0")
}