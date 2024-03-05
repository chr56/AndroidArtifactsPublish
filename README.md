# AndroidArtifactsPublish

This is a tiny Gradle Plugin for publishing Android Artifacts.

_**(🚧 This Plugin is in earlier Development 🚧)**_

_This plugin once was as Composite Build in Phonograph Plus; now it is planing to be independent._

[<img src="https://img.shields.io/github/v/release/chr56/AndroidArtifactsPublish?label=Github%20Release" alt="Github%20Release">](https://github.com/chr56/AndroidArtifactsPublish/releases/latest)
[<img src="https://img.shields.io/gradle-plugin-portal/v/io.github.chr56.tools.release?label=Gradle%20Plugin%20Portal" alt="Gradle%20Plugin%20Portal">](https://plugins.gradle.org/plugin/io.github.chr56.tools.release)

# Features

- Collect Android Artifacts (Apks, mapping) to one place
- Rename Apks
- Zip R8 obfuscation mapping file
- Generate Apk Hashes



# Usage

This plugin requires `Android Gradle Plugin` (7.0+).

(though it currently is a compile-only dependency.)

```kotlin
    androidComponents { // require ApplicationBaseFlavor
        val name = "YOUR PRODUCTS NAME" // this name would be the prefix of collected files
        onVariants(selector().withBuildType("release")) { variant ->
            tasks.registerPublishTask(name, variant)
        }
    }
```

Then the task `publish<VariantName>` (eg. `publishRelease`) would be registered.


# TODO List

## Output

- [x] Custom location for output
- [x] Custom format for renaming
- [x] Custom Hash Algorithm

## Upload and Release

- [ ] Upload outputs to GitHub Release
- [ ] Generate GitHub Release Note