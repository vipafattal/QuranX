# QuranX
[![](https://jitpack.io/v/vipafattal/QuranX.svg)](https://jitpack.io/#vipafattal/QuranX)

A framework to help android developers to build high performance, feature-rich, and powerful Quran apps.

Currently, QuranX contains:

### 📚 [Core](./core/)
A library that provides Quran data based on [AlQuran.cloud](https://alquran.cloud/api) with stylish Kotlin architecture out of box, which cloud be Quran, Tafseer, translation, transaliteration, word by word, etc...

### 🖌 [Tajweed Processor](./tajweedprocessor/)
A library that provides tools to process Tajweed (reciting) rules in which is based on [tajweed edition](http://api.alquran.cloud/v1/quran/quran-tajweed) of alquran.cloud.

### 🔥 [Tajweed Rules](./tajweedrules/)
A library for listing Tajweed rules in words of Quran verses which is based on [tajweed edition](http://api.alquran.cloud/v1/quran/quran-tajweed) of alquran.cloud.

### 🌏 [WordByWord Processor](./wordsprocessor/)
A library for listing Quran verses meaning and transliteration in other languages (English only).

# Installation

#### Step 1
add to your project build.gradle at the end of repositories
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
> Note though the library is targeting JVM, and have minor dependency on Android it can be modified quit easly (Removing or Replacing Room Database and Android Strings) to work where ever Kotlin compiler works (JVM, JS, LVVM).
# Documentation
https://vipafattal.github.io/QuranX/
