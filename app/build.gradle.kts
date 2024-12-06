plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.modakdev.bootstream"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.modakdev.bootstream"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // Updated CameraX dependencies
    implementation(libs.camera.core.v140)
    implementation(libs.camera.camera2.v140)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)

    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    implementation(libs.okhttp)

}