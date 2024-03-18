pluginManagement {
    repositories {
        gradlePluginPortal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("catalog") {
            from(files("gradle/libs.versions.toml"))
        }
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dota2Companion"
include(":app")

include(":core:db")
include(":core:net")
include(":core:presentation")
include(":core:widgets")
include(":core:main")

include(":screens:main:data")
include(":screens:main:domain")
include(":screens:main:presentation")
include(":screens:main:node")

include(":screens:player:data")
include(":screens:player:domain")
include(":screens:player:presentation")
include(":screens:player:node")
include(":core:entities")
include(":prefetch")
