# Core

A is the core that provides Quran data sources bassed on [AlQuran.cloud](https://alquran.cloud/api) with stylish Kotlin aractucture out of box, which provide data such as Quran, Tafseer, translation, transaliteration, word by word, etc...
In the core there are three options [data sources](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data/-data-sources/index.html):

- Local data source, which performs local query to provides the data, [LocalDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-local-data-source/index.html).
- Remote data source, which performs queries on remote [server](https://alquran.cloud/api) to provides the data [RemoteDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-remote-data-source/index.html).
- Local-based data source, which performs queries on remote only if the data is not available in the local source, [LocalBasedDataSource](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data.sources/-local-based-data-source/index.html).

for more information visit [documentation](https://vipafattal.github.io/QuranX/core/com.abedfattal.quranx.core.framework.data/index.html).

### Note 
You could fully rely remote data source, and ignore the local and local-basedsources.

##


