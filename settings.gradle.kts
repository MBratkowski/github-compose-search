pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "GithubRepository"
include(":app")
include(":feature-searchRepositories")
include(":core-data")
include(":core-data-api")
include(":core-data-network")
include(":core-data-local")
include(":core-designSystem")
