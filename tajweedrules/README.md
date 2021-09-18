# Tajweed Rules
A library for listing Tajweed rules in words of Quran verses which is bassed on [quran-tajweed](http://api.alquran.cloud/v1/quran/quran-tajweed) edition of alquran.cloud.


# Setup
Tajweed Quran Parser for Android bassed on [quran-tajweed](http://api.alquran.cloud/v1/quran/quran-tajweed)

#### Usage

Tajweed Rules dpended on `Tajweed` to create a custom action on words that list all rules in each word of the verse, so first make sure you have [Tajweed Processor](https://github.com/vipafattal/QuranX/tree/master/tajweedparser) in your app, Then pass your `Tajweed` object into `TajweedRules`:  

```kotlin
 val tajweed: Tajweed = Tajweed()
 val tajweedRules = TajweedRules(tajweed)
```
Once you have `TajweedRules`, you could call:

``` Kotlin
 fun getRules(ayaText: String): List<WordsWithRules> {
  val tajweed: Tajweed = Tajweed()
  val tajweedRules = TajweedRules(tajweed)
  
  return TajweedRulesActivity.tajweedRules.getRulesOfAya(ayaText)
 }
 ```

You can also list all supported rules see docs
```Kotlin
val metaColors = MetaColors(hsl = "#FF6200EE", ikhafa = "#D50000")
val tajweed = Tajweed(metaColors)
```

# Full Example
See the [app](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/tajweedparser) module for full example.

You can also download the apk from this [link](https://drive.google.com/file/d/10EbERrszIuVqBfxIQkm5whGXcHjibpG5/view?usp=sharing).

<p align="center">
<img src="https://github.com/vipafattal/TajweedParser/blob/master/test.png" width=35% >
</p>

# Styles
This table of the Tajweed metas of the `quran-tajweed` (obtained from [link](https://github.com/vipafattal/alquran-tools/blob/master/docs/tajweed.md)):


# For listing rules meaning in each word
see [Tajweed Rules](https://github.com/vipafattal/QuranX/tree/master/tajweedrules).
