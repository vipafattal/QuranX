# QuranX
A framework that helps android developers to build high performance, feature rich, and powerful Quran apps.

### Installation

#### Step 1
add to your project build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2
add the dependency to the `build.gradle` app module:

```groovy
def quranx_version = "1.0.9"
// All modules
implementation "com.github.vipafattal:QuranX:$quranx_version"

// Or pick the modules you only needs:

// Core module only.
implementation "com.github.vipafattal.QuranX:core:$quranx_version"
// Tajweed Processor module only.
implementation "com.github.vipafattal.QuranX:tajweedparser:$quranx_version"
// Tajweed Rules module only.
implementation "com.github.vipafattal.QuranX:tajweedrules:$quranx_version"
// WordByWord Processor module only.
implementation "com.github.vipafattal.QuranX:tajweedparser:$quranx_version"
```
