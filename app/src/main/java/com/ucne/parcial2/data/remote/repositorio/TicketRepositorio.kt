package com.ucne.parcial2.data.remote.repositorio

import com.ucne.parcial2.data.remote.TicketEntity.TicketEntity
import com.ucne.parcial2.data.remote.TicketEntity.toTicketDto
import com.ucne.parcial2.data.remote.TicketsApi
import com.ucne.parcial2.data.remote.dao.TicketDao
import com.ucne.parcial2.data.remote.dto.TicketDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepositorio @Inject constructor
    (
    private val ticketDao: TicketDao,
    private val ticketsApi: TicketsApi
) {
    suspend fun insert(ticket: TicketEntity) {

        ticketDao.insert(ticket)
        ticketsApi.putTicket(ticket.ticketId!!,ticket.toTicketDto())
    }

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)
    suspend fun find(ticketId:Int) = ticketDao.find(ticketId)
    suspend fun putTickets(id: Int, ticketDto: TicketDto) = ticketsApi.putTicket(id,ticketDto)
    fun getList(): Flow<List<TicketEntity>> = ticketDao.getList()
}