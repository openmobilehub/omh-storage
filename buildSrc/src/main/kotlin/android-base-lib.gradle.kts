plugins {
    id("com.android.library")
    id("io.gitlab.arturbosch.detekt")
    kotlin("android")
    id("jacoco")
    id("maven-publish")
    id("signing")
}

detekt {
    autoCorrect = properties.get("autoCorrect")?.toString()?.toBoolean() ?: false
}

android {
    compileSdk = ConfigData.compileSdkVersion
    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        vectorDrawables {
            useSupportLibrary = true
        }
        consumerProguardFiles("consumer-rules.pro")
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("**/LICENSE.txt")
        resources.excludes.add("**/README.txt")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

setupJacoco()

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}

// Publishing block

val androidSourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from("src/main/java")
    from("src/main/kotlin")
}

artifacts {
    add("archives", androidSourcesJar)
}

val groupProperty = getPropertyOrFail("group")
val versionProperty = getPropertyOrFail("version")
val artifactId = getPropertyOrFail("artifactId")
val mDescription = getPropertyOrFail("description")

group = groupProperty
version = versionProperty

afterEvaluate {
    publishing {
        publications {
            register("release", MavenPublication::class.java) {
                setupPublication()
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        rootProject.ext["signingKeyId"].toString(),
        rootProject.ext["signingKey"].toString(),
        rootProject.ext["signingPassword"].toString(),
    )
    sign(publishing.publications)
}

fun MavenPublication.setupPublication() {
    groupId = groupProperty
    artifactId = artifactId
    version = versionProperty

    if (project.project.plugins.findPlugin("com.android.library") != null) {
        from(project.components["release"])
    } else {
        from(project.components["java"])
    }

    artifact(androidSourcesJar)

    pom {
        name.set(artifactId)
        description.set(mDescription)
        url.set("https://github.com/openmobilehub/omh-storage")
        licenses {
            license {
                name.set("Apache-2.0 License")
                url.set("https://github.com/openmobilehub/omh-storage/blob/main/LICENSE")
            }
        }

        developers {
            developer {
                id.set("HectorNarvaez")
                name.set("Hector Narvaez")
            }
        }

        // Version control info - if you're using GitHub, follow the
        // format as seen here
        scm {
            connection.set("scm:git:github.com/openmobilehub/omh-storage.git")
            developerConnection.set("scm:git:ssh://github.com/openmobilehub/omh-storage.git")
            url.set("https://github.com/openmobilehub/omh-storage/tree/main")
        }
    }
}

