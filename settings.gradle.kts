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

rootProject.name = "Pixabay_test"
include(":app")
include(":feat:home")
include(":feat:favorites")
include(":feat:contentdetail")
include(":core:designsystem")
include(":core:navigation")
include(":core:common")
include(":core:data")
include(":core:domain")
