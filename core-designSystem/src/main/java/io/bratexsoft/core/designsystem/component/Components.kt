package io.bratexsoft.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import io.bratexsoft.core.designsystem.R

@Composable
fun BaseCard(modifier: Modifier = Modifier, composable: @Composable() () -> Unit) {
    Card(
        modifier = modifier.padding(
            top = dimensionResource(id = R.dimen.spacingMedium),
            bottom = dimensionResource(id = R.dimen.spacingMedium)
        )
    ) {
        composable()
    }
}

@Composable
fun HeadlineMedium(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
}

@Composable
fun HeadlineSmall(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}

@Composable
fun SpacerSmall(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacingMedium)))
}

@Composable
fun SpacerMedium(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacingLarge)))
}