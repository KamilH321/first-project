pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FirstProject"
include(":app")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:di")
include(":core:network")
include(":core:build-config:api")
include(":core:build-config:impl")
include(":core:utils")
include(":core:error-handling")
include(":core:error-handling:api")
include(":core:error-handling:impl")
include(":feature:search")
include(":core:navigation")
include(":feature:detail-info")
