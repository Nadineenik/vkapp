package com.nadinee.vkapp.presentation.applistscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nadinee.vkapp.data.showcasePages
import com.nadinee.vkapp.presentation.model.UiAppInfo

@Composable
fun ShowcasePager(
    onAppClick: (UiAppInfo) -> Unit, // ИСПРАВЛЕНО: используем UiAppInfo
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp
        ) { pageIndex ->
            val pageApps = showcasePages[pageIndex].apps

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                pageApps.forEach { app ->
                    AppListItem(
                        app = app,
                        onClick = { onAppClick(app) },
                        onLogoTap = { /* Игнорируем клик по логотипу на витрине */ } // ИСПРАВЛЕНО: добавлен параметр
                    )
                }
            }
        }
    }
}
