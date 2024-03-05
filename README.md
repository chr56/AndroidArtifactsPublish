# AndroidArtifactsPublish

This is a tiny Gradle Plugin for publishing Android Artifacts.

_**(🚧 This Plugin is in earlier Development 🚧)**_

_This plugin once was as Composite Build in Phonograph Plus; now it is planing to be independent._

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