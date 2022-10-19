package com.kocoukot.holdgame.ui.leaderboard.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.domain.model.RecordType
import timber.log.Timber

@Composable
fun RecordTabBar(
    backgroundColor: Color,
    tabPage: RecordType,
    onTabSelected: (tabPage: RecordType) -> Unit
) {
    TabRow(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .border(
                2.dp, HTheme.colors.primaryBlue, RoundedCornerShape(10.dp)
            ),
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            RecordTabIndicator(tabPositions, tabPage)
        }
    ) {
        RecordTab(
            title = stringResource(R.string.my_records),
            onClick = {
                Timber.d("selectedRecords clicked personal")
                onTabSelected(RecordType.PERSONAL)
            }
        )
        RecordTab(
            title = stringResource(R.string.world_records),
            onClick = {
                Timber.d("selectedRecords clicked world")
                onTabSelected(RecordType.GLOBAL)
            }
        )
    }
}

@Composable
private fun RecordTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: RecordType
) {
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (RecordType.PERSONAL isTransitioningTo RecordType.GLOBAL) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessHigh)
            }
        },
        label = "Indicator left",

        ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (RecordType.PERSONAL isTransitioningTo RecordType.GLOBAL) {
                spring(stiffness = Spring.StiffnessHigh)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .fillMaxSize()
            .background(
                HTheme.colors.primaryBlue,
                RoundedCornerShape(8.dp)
            )
    )
}

@Composable
private fun RecordTab(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .zIndex(1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = HTheme.typography.titleSubHeader, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    RecordTabBar(
        backgroundColor = HTheme.colors.primaryBackground,
        tabPage = RecordType.PERSONAL,
        onTabSelected = {}
    )
}