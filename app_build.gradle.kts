plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
 namespace = "com.soulai.player"
 compileSdk = 35
 defaultConfig { applicationId = "com.soulai.player"; minSdk = 26; targetSdk = 35; versionCode = 1; versionName = "0.1" }
}
dependencies {
 implementation("androidx.core:core-ktx:1.16.0")
 implementation("androidx.appcompat:appcompat:1.7.0")
 implementation("com.google.android.material:material:1.12.0")
 implementation("androidx.media3:media3-exoplayer:1.6.1")
 implementation("androidx.media3:media3-ui:1.6.1")
}
