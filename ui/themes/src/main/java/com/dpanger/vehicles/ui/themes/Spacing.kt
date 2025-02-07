package com.dpanger.vehicles.ui.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val margins: Margins,
    val padding: Padding,
    val lists: Lists,
)

@Immutable
data class Margins(
    val horizontal: Dp,
    val vertical: Dp,
)

@Immutable
data class Padding(
    val small: Dp,
    val large: Dp,
)

@Immutable
data class Lists(
    val singleLine: Dp,
)

val MaterialTheme.spacing: Spacing
    get() =
        Spacing(
            margins = Margins(horizontal = 16.dp, vertical = 16.dp),
            padding = Padding(small = 4.dp, large = 16.dp),
            lists = Lists(singleLine = 6.dp),
        )
