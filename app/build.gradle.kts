plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.myapplicationyoga"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplicationyoga"
        minSdk = 30
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.compose.material:material-icons-extended")

    implementation("androidx.navigation:navigation-compose:2.8.6")

    implementation(platform("io.github.jan-tennert.supabase:bom:2.6.1"))

    implementation("io.github.jan-tennert.supabase:postgrest-kt")

    implementation("io.github.jan-tennert.supabase:gotrue-kt")

    implementation("io.ktor:ktor-client-android:2.3.12")

    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("io.coil-kt:coil-svg:2.2.2")

    implementation("androidx.core:core-ktx:1.12.0")

    implementation("com.google.accompanist:accompanist-permissions:0.31.2-alpha")

    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation("androidx.compose.foundation:foundation:1.7.8")



}