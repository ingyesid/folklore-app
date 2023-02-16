// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
}

/*
Allows to run detekt for all files in the Gradle project and all subprojects without a need to configure detekt
plugin in every subproject.
 */
tasks.register("detektCheck", io.gitlab.arturbosch.detekt.Detekt::class) {
    val autoCorrectParam = project.hasProperty("detektAutoCorrect")

    description = "Custom detekt for to check all modules"
    parallel = true
    ignoreFailures = false
    autoCorrect = autoCorrectParam
    setSource(file(projectDir))
    // Custom detekt config
    config.setFrom("$projectDir/detekt.yml")

    // Use default configuration on top of custom config (new detect rules will work out of the box after upgrading detekt version)
    buildUponDefaultConfig = true

    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**", "**/generated/**")

    reports {
        html.required.set(true)
        xml.required.set(true)
    }

    dependencies {
        // detekt wrapper for rules implemented by ktlint https://detekt.dev/docs/rules/formatting
        detektPlugins(libs.detektFormatting)
    }
}
