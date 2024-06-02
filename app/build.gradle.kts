import java.io.FileInputStream
import java.util.*

plugins {
	id("com.android.application")
}

android {
	namespace = "com.gbulgaru.simpleweather"
	compileSdk = 34
	buildFeatures.buildConfig = true

	defaultConfig {
		applicationId = "com.gbulgaru.simpleweather"
		minSdk = 31
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		val secretsPropertiesFile = rootProject.file("secrets.properties")
		val secretsProperties = Properties()
		secretsProperties.load(FileInputStream(secretsPropertiesFile))

		buildConfigField("String", "OpenW_API_KEY", "\"${secretsProperties.getProperty("OpenW_API_KEY")}\"")
		buildConfigField("String", "AccuW_API_KEY", "\"${secretsProperties.getProperty("AccuW_API_KEY")}\"")

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.12.0")
	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	implementation("com.google.android.gms:play-services-location:21.3.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}