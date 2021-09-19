# Tajweed Rules
A library for listing Tajweed rules in words of Quran verses which is based on [quran-tajweed](http://api.alquran.cloud/v1/quran/quran-tajweed) edition of alquran.cloud.

<p align="center">
<img src="https://github.com/vipafattal/QuranX/blob/master/images/tajweedrules1.png" width=35% />
<img src="https://github.com/vipafattal/QuranX/blob/master/images/tajweedrules2.png" width=35% >
</p>


## Setup
https://github.com/vipafattal/QuranX/blob/master/README.md#instalition

## Usage

Tajweed Rules depended on `Tajweed` to create a custom action on words that list all rules in each word of the verse, so first make sure you have [Tajweed Processor](https://github.com/vipafattal/QuranX/tree/master/tajweedparser) in your app, Then pass your `Tajweed` object into `TajweedRules`:

```kotlin
 val tajweed: Tajweed = Tajweed()
 val tajweedRules = TajweedRules(tajweed)
```
Once you have `TajweedRules`, you could call:

``` Kotlin
  val tajweed: Tajweed = Tajweed()
  val tajweedRules = TajweedRules(tajweed)
  
  val rules : List<WordsWithRules> =  tajweedRules.getRulesOfAya(ayaText)
 ```

You can also list all supported rules see [docs](https://vipafattal.github.io/QuranX/tajweedrules/com.abedfattal.quranx.tajweedrules/-tajweed-rules/index.html).

## Full Example
See the [sample](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/tajweedrules) for implementation example.


## For coloring
see [Tajweed Processor](https://github.com/vipafattal/QuranX/tree/master/tajweedprocessor).
