plugins {
    alias(libs.plugins.android.application) // Plugin de Android
}

android {
    namespace = "com.example.recetasappproyectofinal" // Paquete del proyecto
    compileSdk = 35 // Versión del SDK de compilación

    defaultConfig {
        applicationId = "com.example.recetasappproyectofinal" // ID de la app
        minSdk = 24 // SDK mínimo soportado
        targetSdk = 35 // SDK objetivo
        versionCode = 1 // Código de versión
        versionName = "1.0" // Nombre de versión

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner de pruebas
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Minificación desactivada
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ) // Configuración de ProGuard
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Compatibilidad con Java 11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat) // Biblioteca de compatibilidad de Android
    implementation(libs.material) // Material Design
    implementation(libs.activity) // Actividad de AndroidX
    implementation(libs.constraintlayout) // ConstraintLayout
    testImplementation(libs.junit) // JUnit para pruebas unitarias
    androidTestImplementation(libs.ext.junit) // JUnit extendido para Android
    androidTestImplementation(libs.espresso.core) // Espresso para pruebas de UI
    implementation("androidx.recyclerview:recyclerview:1.3.0") // RecyclerView
    implementation("androidx.appcompat:appcompat:1.6.1") // AppCompat para soporte extendido
}
