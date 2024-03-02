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

rootProject.name = "Dota2Companion"
include(":app")
include(":core:presentation")
include(":core:db")
include(":core:netclient")
include(":screens:main:presentation")
include(":screens:main:node")
include(":screens:player:presentation")
include(":screens:player:domain")
include(":screens:player:node")
include(":core:main")
