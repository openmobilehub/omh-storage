import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase
import org.gradle.testing.jacoco.tasks.JacocoReportsContainer

private const val jacocoTaskGroup = "verification"

fun Project.setupJacoco() {
    tasks.withType<Test> {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }

    tasks.register<JacocoReport>("jacocoTestReport") {
        group = jacocoTaskGroup
        description = "Code coverage report for both Android and Unit tests."
        dependsOn(tasks.getByName("testDebugUnitTest"))
        reports.setUp(this@setupJacoco)
        setDirs(this@setupJacoco)
    }

    val minimunCoverageAllowed = properties.get("minimunCoverageAllowed").toString().toBigDecimal()
    tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
        group = jacocoTaskGroup
        description = "Code coverage verification for Android both Android and Unit tests."
        dependsOn(tasks.getByName("testDebugUnitTest"))
        violationRules {
            rule {
                limit {
                    minimum = minimunCoverageAllowed
                }
            }
            rule {
                element = "CLASS"
                excludes = listOf(
                    "**.FactorFacade.Builder",
                    "**.ServiceFacade.Builder",
                    "**.ChallengeFacade.Builder",
                    "**.Task"
                )
                limit {
                    minimum = minimunCoverageAllowed
                }
            }
        }
        setDirs(this@setupJacoco)
    }
}

fun JacocoReportsContainer.setUp(project: Project) {
    csv.required.set(false)
    xml.apply {
        required.set(true)
        outputLocation.set(project.file("${project.buildDir}/reports/code-coverage/xml"))
    }
    html.apply {
        required.set(true)
        outputLocation.set(project.file("${project.buildDir}/reports/code-coverage/html"))
    }
}


private fun JacocoReportBase.setDirs(project: Project) {
    val classDirectoriesTree = project.fileTree("${project.buildDir}") {
        include(
            "**/classes/**/main/**",
            "**/intermediates/classes/debug/**",
            "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
            "**/tmp/kotlin-classes/debug/**"
        )
        exclude(
            "**/R.class",
            "**/R\$*.class",
            "**/*\$1*",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*",
            "**/models/**",
            "**/*\$Lambda$*.*",
            "**/*\$inlined$*.*"
        )
    }

    val sourceDirectoriesTree = project.files("${project.projectDir}/src/main/java")

    val executionDataTree = project.fileTree("${project.buildDir}") {
        include(
            "outputs/code_coverage/**/*.ec",
            "jacoco/jacocoTestReportDebug.exec",
            "jacoco/testDebugUnitTest.exec",
            "jacoco/test.exec"
        )
    }
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}
