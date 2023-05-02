package io.bratexsoft.feature.searchRepositories.util

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import io.bratexsoft.core.data.api.model.CommitInformation
import javax.inject.Inject

class SendCommitsIntentProvider @Inject constructor(
    @ActivityContext val context: Context,
    private val intentMessageProvider: IntentMessageProvider,
) {

    operator fun invoke(commitsToShare: List<CommitInformation>) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, intentMessageProvider(commitsToShare))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}
