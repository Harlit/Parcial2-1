package com.ucne.parcial2.ui.theme.Tickets


import kotlinx.coroutines.launch
import java.util.*
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.parcial2.ui.theme.Ticket.TicketViewModel
import com.ucne.parcial2.ui.theme.Ticket.TicketViewModelApi
import com.ucne.parcial2.ui.theme.navegacion.ScreenModule


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketsScreen(ticketId: Int, viewModel: TicketViewModelApi = hiltViewModel(), navController: NavController, onSave: () -> Unit) {
    remember {
        viewModel.setTicket(ticketId)
        0
    }
    TicketsBody(viewModel = viewModel, Modifier.fillMaxWidth(), navController)
    {
        onSave()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketsBody(
    viewModel: TicketViewModelApi, modifier: Modifier, navController: NavController,
    onSave: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val mCalendar = Calendar.getInstance()

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current, { datePicker, year, month, dayOfMonth ->
            viewModel.fecha = "$dayOfMonth/${month + 1}/$year"
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxWidth())
    {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp, 30.dp)
                .padding(4.dp)
                .clickable {
                    scope.launch {
                        navController.navigate(ScreenModule.Tickets.route)
                    }
                }
        )

        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Registro Tickets", fontSize = 27.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.asunto,
            onValueChange = { it -> viewModel.asunto = it },
            label = { Text("Asunto") })

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.empresa,
            onValueChange = { it -> viewModel.empresa = it },
            label = { Text("Empresa") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.estatus,
            onValueChange = { viewModel.estatus = it },
            label = { Text("Estatus") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.fecha,
            onValueChange = { viewModel.fecha = it },
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .padding(4.dp)
                        .clickable {
                            mDatePickerDialog.show()
                        })
            },
            label = { Text(text = "Fecha") }
        )

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = { Text("Guardar") },
            icon = { Icon(imageVector = Icons.Filled.Save, contentDescription = "Save") },
            onClick = { viewModel.putTicket()
                onSave() }
        )
    }
}



