package com.ucne.parcial2.data.remote.repositorio

import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow


interface TicketRepositorioApi
{
    fun getTickets(): Flow<Resource<List<TicketDto>>>
    fun getTicketsForId(id: Int): Flow<Resource<TicketDto>>
    suspend fun putTickets(id: Int, ticketDto: TicketDto)
    suspend fun deleteTickets(id: Int)
    suspend fun postTickets(ticketDto: TicketDto)
}