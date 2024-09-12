package com.evg

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.resource.R
import com.evg.ui.theme.mainDarkColor
import com.evg.ui.theme.mainLightColor

@Composable
fun CustomNavigationBar() {
    val profileIndex = 4
    val items = listOf(
        stringResource(R.string.search) to painterResource(R.drawable.avito),
        stringResource(R.string.favorites) to painterResource(R.drawable.favorite),
        stringResource(R.string.ads) to painterResource(R.drawable.list),
        stringResource(R.string.messages) to painterResource(R.drawable.message_bubble),
        stringResource(R.string.profile) to painterResource(R.drawable.user)
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = if (isSystemInDarkTheme()) mainDarkColor else mainLightColor,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        painter = item.second,
                        contentDescription = item.first,
                    )
                },
                label = {
                    Text(
                        text = item.first,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp,
                    )
                },
                selected = index == profileIndex,
                onClick = {},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color(0xFF6B6B6B),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF6B6B6B),
                    unselectedTextColor = Color(0xFF6B6B6B),
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                )
            )
        }
    }
}