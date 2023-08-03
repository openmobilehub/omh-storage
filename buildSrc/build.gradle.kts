plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
}

configurations.all {
    resolutionStrategy.eachDependency {
        when (requested.name) {
            "javapoet" -> useVersion("1.13.0")
        }
    }
}

dependencies {
    gradleApi()
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("com.android.tools.build:gradle:7.4.1")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    implementation("org.jacoco:org.jacoco.core:0.8.8")
    implementation("com.openmobilehub.android:omh-core:1.0.4-beta")
}
