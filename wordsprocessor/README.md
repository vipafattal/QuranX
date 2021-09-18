# WordByWord Procossor
A library for listing Quran verses meaning and transliteration in other languages (English only). The library currently support these editions: [WordByword](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword-2) and [WordByword](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword-2).

<p align="center">
<img src="https://github.com/vipafattal/QuranX/blob/master/images/wordsprocossor.png" width=35% />
</p>

# Usage

Using `WordByWord` is feirly simple, just decide which version you want to use: 

#### [WordByword v2](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword) support only word meaning.
#### [WordByword v2](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword-2) support words meaning and English translitration.

then call on from `WordByWordEnglish` the crosseponds function:

```kotlin
 val wordByWord: List<AyaWordV2> = WordByWordEnglish.getWordOfAyaV2(aya.text)
```

For more infomation visit the [documentation](https://vipafattal.github.io/QuranX/tajweedprocessor/index.html).

# Full Example
See the [tajweedrules sample](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/wordsprocessor) for implementation example.
