# Tajweed Processor
Tajweed Quran Parser for Android bassed on [quran-tajweed](http://api.alquran.cloud/v1/quran/quran-tajweed). 
It helps to create colored text for Quran reciting rules.

<p align="center">
<img src="https://github.com/vipafattal/QuranX/blob/master/images/tajweedprocessor.png" width=35% >
</p>

# Setup
https://github.com/vipafattal/QuranX/blob/master/README.md#instalition

# Usage

The library is based on https://alquran.cloud/api, on edition `quran-tajweed`.
Once you have text of `quran-tajweed` inside the app you can call `TajweedHelper.getStyledWords(Aya.text)` to return a [Spannable](https://developer.android.com/reference/android/text/Spannable) text. YOu could use `TextView` with `Spannable` text.

```kotlin
val tajweed = Tajweed()
val ayaTextView: TextView = findViewById(R.id.ayahText)
ayaTextView.text = tajweed.getStyledWords(aya.text)
```

You can also overriding the properties color by creating a class of `MetaColors` and pass it to `Tajweed`:

```Kotlin
val metaColors = MetaColors(hsl = "#FF6200EE", ikhafa = "#D50000")
val tajweed = Tajweed(metaColors)
```
There are many options you colud use, for more information, visit the [documentation](https://vipafattal.github.io/QuranX/tajweedprocessor/com.abedfattal.quranx.tajweedprocessor/-tajweed/index.html).

# Full Example
See the [app](https://github.com/vipafattal/QuranX/tree/master/sample/src/main/java/com/abedfattal/quranx/sample/tajweedprocessor) module for full example.

# Styles
This table of the Tajweed metas of the `quran-tajweed` (obtained from [link](https://github.com/vipafattal/alquran-tools/blob/master/docs/tajweed.md)):

# The  Library Functionality 
This library takes the output of the Tajweed edition, and creates an Android `Spannalbe`, which is Widget friendly text.
It uses the table below to parse what the API returns and converts it to something you can use with TextViews widgets.

<table>
    <thead>
        <tr>
            <th>Type</th>
            <th>Identifier</th>
            <th>Colour</th>
            <th>Variable Name</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
                    <tr>
                <td class="ham_wasl">hamza-wasl</td>
                <td>[h</td>
                <td style="background-color: #000000">#AAAAAA</td>
                <td>hsl</td>
                <td>Hamzat ul Wasl</td>
            </tr>
                    <tr>
                <td class="slnt">silent</td>
                <td>[s</td>
                <td style="background-color: #000000">#AAAAAA</td>
                <td>hsl</td>
                <td>Silent</td>
            </tr>
                    <tr>
                <td class="slnt">laam-shamsiyah</td>
                <td>[l</td>
                <td style="background-color: #000000">#AAAAAA</td>
                <td>hsl</td>
                <td>Lam Shamsiyyah</td>
            </tr>
                    <tr>
                <td class="madda_normal">madda-normal</td>
                <td>[n</td>
                <td style="background-color: #537FFF">#537FFF</td>
                <td>madda_normal</td>
                <td>Normal Prolongation: 2 Vowels</td>
            </tr>
                    <tr>
                <td class="madda_permissible">madda-permissible</td>
                <td>[p</td>
                <td style="background-color: #4050FF">#4050FF</td>
                <td>madda_permissible</td>
                <td>Permissible Prolongation: 2, 4, 6 Vowels</td>
            </tr>
         </tr>
                    <tr>
                <td class="madda_necesssary">madda-necesssary</td>
                <td>[m</td>
                <td style="background-color: #000EBC">#000EBC</td>
                <td>madda_necessary</td>
                <td>Necessary Prolongation: 6 Vowels</td>
            </tr>
                    <tr>
                <td class="qlq">qalaqah</td>
                <td>[q</td>
                <td style="background-color: #DD0008">#DD0008</td>
                <td>qlq</td>
                <td>Qalaqah</td>
            </tr>
                    <tr>
                <td class="madda_pbligatory">madda-obligatory</td>
                <td>[o</td>
                <td style="background-color: #2144C1">#2144C1</td>
                <td>madda_pbligatory</td>
                <td>Obligatory Prolongation: 4-5 Vowels</td>
            </tr>
                    <tr>
                <td class="ikhf_shfw">ikhafa-shafawi</td>
                <td>[c</td>
                <td style="background-color: #D500B7">#D500B7</td>
                <td>ikhf_shfw</td>
                <td>Ikhafa' Shafawi - With Meem</td>
            </tr>
                    <tr>
                <td class="ikhf">ikhafa</td>
                <td>[f</td>
                <td style="background-color: #9400A8">#9400A8</td>
                <td>ikhf</td>
                <td>Ikhafa'</td>
            </tr>
                    <tr>
                <td class="idghm_shfw">idgham-shafawi</td>
                <td>[w</td>
                <td style="background-color: #58B800">#58B800</td>
                <td>idghm_shfw</td>
                <td>Idgham Shafawi - With Meem</td>
            </tr>
                    <tr>
                <td class="iqlb">iqlab</td>
                <td>[i</td>
                <td style="background-color: #26BFFD">#26BFFD</td>
                <td>iqlb</td>
                <td>Iqlab</td>
            </tr>
                    <tr>
                <td class="idgh_ghn">idgham-without-ghunnah</td>
                <td>[a</td>
                <td style="background-color: #169777">#169777</td>
                <td>idgh_ghn</td>
                <td>Idgham - With Ghunnah</td>
            </tr>
                    <tr>
                <td class="idgh_w_ghn">idgham-without-ghunnah</td>
                <td>[u</td>
                <td style="background-color: #169200">#169200</td>
                <td>idgh_w_ghn</td>
                <td>Idgham - Without Ghunnah</td>
            </tr>
                    <tr>
                <td class="idgh_mus">idgham-mutajanisayn</td>
                <td>[d</td>
                <td style="background-color: #A1A1A1">#A1A1A1</td>
                <td>idgh_mus</td>
                <td>Idgham - Mutajanisayn</td>
            </tr>
                    <tr>
                <td class="idgh_mus">idgham-mutaqaribayn</td>
                <td>[b</td>
                <td style="background-color: #A1A1A1">#A1A1A1</td>
                <td>idgh_mus</td>
                <td>Idgham - Mutaqaribayn</td>
            </tr>
                    <tr>
                <td class="ghn">ghunnah</td>
                <td>[g</td>
                <td style="background-color: #FF7E1E">#FF7E1E</td>
                <td>ghn</td>
                <td>Ghunnah: 2 Vowels MIMM/NOON with SHADEH</td>
            </tr>
            </tbody>
</table>

for more information about `quran-tajweed` edition see the docs at [link1](https://github.com/islamic-network/alquran-tools/blob/master/docs/tajweed.md) and [link2](https://alquran.cloud/tajweed-guide).

# For listing rules in each word
see [Tajweed Rules](https://github.com/vipafattal/QuranX/tree/master/tajweedrules).
