plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.sm.android.baseproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sm.android.baseproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        setProperty("archivesBaseName", "BaseProject_V${versionCode}_${versionName}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //    signingConfigs {
//        create("release") {
//            keyAlias = "key0"
//            keyPassword = "base"
//            storeFile = file("F:\\AndroidStudioProjects\\Projects\\BaseProject\\BaseProject\\app\\jks\\baseproject.jks")
//            storePassword = "base"
//        }
//    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            resValue("string", "app_id", "ca-app-pub-3940256099942544~3347511713")

        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            resValue("string", "app_id", "ca-app-pub-3940256099942544~3347511713")

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
        viewBinding =  true
        buildConfig = true
    }
    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {

    //    test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //    androidX
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.datastore.preferences)

    //    lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.process)

    //     Billing
    implementation(libs.billing.ktx)

    //    ads
    implementation(libs.play.services.ads)

    //    Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

//    // Facebook
//    implementation ("com.facebook.android:facebook-android-sdk:17.0.1")

    //    firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.config.ktx)

    //    roomdb
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    //    common
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.shimmer)
    implementation(libs.lottie)
    implementation(libs.glide)
    implementation(libs.gson)

    //    others


}