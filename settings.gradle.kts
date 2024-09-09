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

rootProject.name = "FakeShop"
include(":app")
include(":feature:registration")
include(":core:resource")
include(":core:fakeshop-api")
include(":feature:login")
include(":core:shared-prefs")
include(":feature:product-list")
include(":core:database")
