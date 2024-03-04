# AndroidArtifactsPublish

This is a tiny Gradle Plugin for publishing Android Artifacts.

_**(🚧 This Plugin is in earlier Development 🚧)**_

# Features

- Collect Android Artifacts (Apks, mapping) to one place
- Rename Apks
- Zip R8 obfuscation mapping file
- Generate Apk Hashes



# Usage

```kotlin
    androidComponents { // require ApplicationBaseFlavor
        val name = "YOUR PRODUCTS NAME" // this name would be the prefix of collected files
        onVariants(selector().withBuildType("release")) { variant ->
            tasks.registerPublishTask(name, variant)
        }
    }
```

Then the task `publish<VariantName>` (eg. `publishRelease`) would be registered.