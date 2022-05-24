package com.abedfattal.quranx.ui.library.framework.download

import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.CoroutineScope

class EditionDownloadJob internal constructor(
    val edition: Edition,
    val job: CoroutineScope,
    var downloadState: DownloadingProcess<Unit>
)