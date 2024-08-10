import java.io.FileInputStream
import java.util.*

plugins {
	id("com.android.application")
}

android {
	namespace = "com.gbulgaru.simpleweather"
	compileSdk = 35
	buildFeatures.buildConfig = true

	defaultConfig {
		applicationId = "com.gbulgaru.simpleweather"
		minSdk = 23
		targetSdk = 35
		versionCode = 1
		versionName = "1.6"

		val secretsPropertiesFile = rootProject.file("secrets.properties")
		val secretsProperties = Properties()
		secretsProperties.load(FileInputStream(secretsPropertiesFile))

		buildConfigField("String", "OpenW_API_KEY", "\"${secretsProperties.getProperty("OpenW_API_KEY")}\"")
		buildConfigField("String", "AccuW_API_KEY", "\"${secretsProperties.getProperty("AccuW_API_KEY")}\"")
		buildConfigField("String", "Ninjas_API_KEY", "\"${secretsProperties.getProperty("Ninjas_API_KEY")}\"")

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	androidResources{
		generateLocaleConfig = true
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
}

dependencies {
	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("com.google.android.material:material:1.12.0")
	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	implementation("com.google.android.gms:play-services-location:21.3.0")
	implementation("com.github.bumptech.glide:glide:4.12.0")
}