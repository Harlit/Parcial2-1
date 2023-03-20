package com.ucne.parcial2.data.remote

import com.ucne.parcial2.data.remote.dto.TicketDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TicketsApi{
    @GET("/api/tickets")
    suspend fun getTickets(): List<TicketDto>

    @POST("/api/Tickets")
    suspend fun postTicket(@Body ticketDto: TicketDto): TicketDto

    @GET("/api/tickets/{id}")
    suspend fun getTicketsForId(@Path("id") id: Int): TicketDto

    @PUT("/api/Tickets/{id}")
    suspend fun putTicket(@Path("id") id: Int ,@Body ticketDto: TicketDto): Response<Unit>
    @DELETE("/api/Tickets/{id}")
    suspend fun deleteTicket(@Path("id") id: Int)

}