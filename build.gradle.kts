subprojects {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }
}

tasks.register("installPrePushHook", Copy::class) {
    from("tools/scripts/pre-push")
    into(".git/hooks")
    fileMode = 0b000_111_111_111
}

tasks.register("installPreCommitHook", Copy::class) {
    from("tools/scripts/pre-commit")
    into(".git/hooks")
    fileMode = 0b000_111_111_111
}

tasks {
    val installPrePushHook by existing
    val installPreCommitHook by existing
    getByName("prepareKotlinBuildScriptModel").dependsOn(installPrePushHook)
    getByName("prepareKotlinBuildScriptModel").dependsOn(installPreCommitHook)
}
