repositories {
    mavenCentral()
}

val detekt by configurations.creating

val detektTask = tasks.register<JavaExec>("detekt") {
    main = "io.gitlab.arturbosch.detekt.cli.Main"
    classpath = detekt

    val input = projectDir
    val config = "$projectDir/detekt.yml"
    val exclude = ".*/build/.*,.*/resources/.*"
    val params = listOf("-i", input, "-c", config, "-ex", exclude)

    args(params)
}

dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.22.0")
}

// Remove this block if you don't want to run detekt on every build
tasks.check {
    dependsOn(detektTask)
}