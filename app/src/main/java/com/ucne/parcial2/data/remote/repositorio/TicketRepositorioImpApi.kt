package com.ucne.parcial2.data.remote.repositorio

import com.ucne.parcial2.data.remote.TicketsApi
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TicketRepositorioImpApi @Inject constructor(
    private val ticketsApi: TicketsApi
): TicketRepositorioApi {

    override fun getTickets(): Flow<Resource<List<TicketDto>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val ticket = ticketsApi.getTickets() //descarga las ocupaciones de internet, se supone quedemora algo

            emit(Resource.Success(ticket)) //indicar que se cargo correctamente y pasarle las monedas
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override fun getTicketsForId(id: Int): Flow<Resource<TicketDto>> = flow {
        try {
            emit(Resource.Loading())

            val tickets =
                ticketsApi.getTicketsForId(id)

            emit(Resource.Success(tickets))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override suspend fun putTickets(id: Int, ticketDto: TicketDto) {
        ticketsApi.putTicket(id, ticketDto)
    }

    override suspend fun deleteTickets(id: Int) = ticketsApi.deleteTicket(id)
    override suspend fun postTickets(ticketDto: TicketDto) {
        ticketsApi.postTicket(ticketDto)
    }
}