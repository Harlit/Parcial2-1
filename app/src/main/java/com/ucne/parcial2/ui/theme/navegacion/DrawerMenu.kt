package com.ucne.parcial2.ui.theme.navegacion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ucne.parcial2.ui.theme.Ticket.TicktetListScreen
import com.ucne.parcial2.ui.theme.Tickets.TicketsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val ic  =Icons.TwoTone.Favorite

    val items = listOf(ScreenModule.Start, ScreenModule.TicketsList)
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                Spacer(Modifier.height(20.dp))
                Button(onClick = { scope.launch { drawerState.open() } }) {
                    Text("Click para abrir")
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HostList()
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenModule.Start.route
    ) {
        composable(ScreenModule.Start.route) {
            DrawerMenu(navController = navController)
        }
        composable(route = ScreenModule.TicketsList.route){
            TicktetListScreen( {}, navController = navController){ id ->
                navController.navigate(ScreenModule.Tickets.route + "/${id}")
            }
        }
        composable(
            route = ScreenModule.Tickets.route + "/{id}",
            arguments = listOf( navArgument("id") { type = NavType.IntType })
        ) { capturar -> val ticketId = capturar.arguments?.getInt("id") ?: 0

            TicketsScreen(ticketId = ticketId, navController = navController) {
                navController.navigate(ScreenModule.TicketsList.route)
            }
        }
    }
}