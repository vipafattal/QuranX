# Tajweed Rules
A library for listing Tajweed rules in words of Quran verses which is bassed on [quran-tajweed](http://api.alquran.cloud/v1/quran/quran-tajweed) edition of alquran.cloud.

<p align="center">
<img src="https://github.com/vipafattal/QuranX/blob/master/images/tajweedrules1.png" width=35% />
<img src="https://github.com/vipafattal/QuranX/blob/master/images/tajweedrules2.png" width=35% >
</p>


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
  val tajweed: Tajweed = Tajweed()
  val tajweedRules = TajweedRules(tajweed)
  
  val rules : List<WordsWithRules> =  TajweedRulesActivity.tajweedRules.getRulesOfAya(ayaText)
 ```

You can also list all supported rules see docs
```Kotlin
val metaColors = MetaColors(hsl = "#FF6200EE", ikhafa = "#D50000")
val tajweed = Tajweed(metaColors)
```

# Full Example
See the [tajweedrules sample](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/tajweedrules) for implementation example.


# For listing rules meaning in each word
see [Tajweed Processor](https://github.com/vipafattal/QuranX/tree/master/tajweedprocessor).
