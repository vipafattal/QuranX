# Core

A is the core that provides Quran data sources based on [AlQuran.cloud](https://alquran.cloud/api) with stylish Kotlin architecture out of box, which provide data such as Quran, Tafseer, translation, transliteration, word by word, etc...
In the core there are three options [data sources](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data/-data-sources/index.html):

- Local data source, which performs local query to provides the data, see [LocalDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-local-data-source/index.html).
- Remote data source, which performs queries on remote [server](https://alquran.cloud/api) to provides the data, see [RemoteDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-remote-data-source/index.html).
- Local-based data source, which performs queries on remote only if the data is not available in the local source, see [LocalBasedDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-local-based-data-source/index.html).

### Note that you could fully rely remote data source, and safely ignore the local and local-based sources, for more information on Core library see the [documentation](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data/index.html).


## Setup
https://github.com/vipafattal/QuranX/blob/master/README.md#instalition

## Sample
The most of the [samples](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/) make use of the Core library, but you could see the an extended [sample](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/core) which provides an implementation for downloading a Quran book.



