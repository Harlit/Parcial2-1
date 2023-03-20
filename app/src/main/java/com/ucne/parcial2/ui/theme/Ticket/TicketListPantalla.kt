package com.ucne.parcial2.ui.theme.Ticket

import kotlinx.coroutines.launch
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.ui.theme.navegacion.ScreenModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicktetListScreen(
    onNewTicket: () -> Unit,
    viewModel: TicketViewModelApi = hiltViewModel(),
    navController: NavController,
    onTicket: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(50.dp))
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp, 30.dp)
                .padding(4.dp)
                .wrapContentSize(Alignment.TopStart)
                .clickable {
                    scope.launch {
                        navController.navigate(ScreenModule.TicketsList.route)
                    }
                }
        )
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNewTicket() }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Save")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {

            val uiState by viewModel.uiState.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                TicketListBody(uiState.tickets) {
                    onTicket(it)
                }
            }
        }
    }
}

@Composable
fun TicketListBody(ticketList: List<TicketDto>,onTicket: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            items(ticketList) {ticket ->
                TicketRow(ticket){
                    onTicket(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketRow(ticket: TicketDto, onTicket: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onTicket(ticket.ticketId) })
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        // Empresa y fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Empresa
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ticket.empresa.foldIndexed("") { index, acc, c ->
                        if (index % 20 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Fecha
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentSize(Alignment.Center)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .size(250.dp, 50.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    value = ticket.fecha,
                    onValueChange = { ticket.fecha = it },
                    enabled = false,
                    //textStyle = MaterialTheme.typography.body1,
                    label = { Text("Fecha") }
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        // Asunto, eliminar y completado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Asunto
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ticket.asunto.foldIndexed("") { index, acc, c ->
                        if (index % 30 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Eliminar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Eliminar ticket */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar ticket",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            // Completado
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckBox,
                    contentDescription = "Marcar como completado",
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))
        }

        Spacer(modifier = Modifier.padding(10.dp))

        // Separador
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color.Gray
        )
    }


}