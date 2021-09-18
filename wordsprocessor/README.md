# WordByWord Procossor
A library for listing Quran verses meaning and transliteration in other languages (English only). The library currently support these editions: [WordByword by Tanzil.net](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword) and [WordByword by Dr. Shehnaz Shaikh](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword-2).

<p align="center">
<img src="https://github.com/vipafattal/QuranX/blob/master/images/wordsprocessor.png" width=35% />
</p>

# Usage

Using `WordByWord` is feirly simple, just decide which version you want to use: 

#### [WordByword v1](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword) support only word meaning.
#### [WordByword v2](https://api.alquran.cloud/v1/ayah/289/quran-wordbyword-2) support words meaning and English translitration.

then call from `WordByWordEnglish` the crosseponds function:

```kotlin
 val wordByWord: List<AyaWordV2> = WordByWordEnglish.getWordOfAyaV2(aya.text)
```

For more infomation visit the [documentation](https://vipafattal.github.io/QuranX/tajweedprocessor/index.html).

# Full Example
See the [sample](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/wordsprocessor) for implementation example.
