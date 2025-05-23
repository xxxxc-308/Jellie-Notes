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
    buildscript{
        repositories{
            maven {
                url = uri("https://storage.googleapis.com/r8-releases/raw/main")
            }
        }
        dependencies{
            classpath("com.android.tools:r8:dc2a45cbda6abd6b740e87e40ead3442e6e164b2")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "JellieNotes"
include(":app")
include(":baselineprofile")
include(":editor")
include(":plugins")
include(":runtime")
