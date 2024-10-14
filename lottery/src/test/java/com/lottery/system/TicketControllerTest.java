package com.lottery.system;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottery.system.dto.TicketRequest;
import com.lottery.system.model.Ticket;
import com.lottery.system.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    void createTicket() throws Exception {
        TicketRequest request = new TicketRequest();
        request.setNumberOfLines(3);
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        Mockito.when(ticketService.createTicket(Mockito.anyInt())).thenReturn(ticket);

        mockMvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numberOfLines\": 3}")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllTickets() throws Exception {
        List<Ticket> tickets = List.of(new Ticket(), new Ticket());

        Mockito.when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/ticket")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        Mockito.when(ticketService.getTicket(1L)).thenReturn(ticket);

        mockMvc.perform(get("/ticket/1")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void amendTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        Mockito.when(ticketService.amendTicket(Mockito.anyLong(), Mockito.anyInt())).thenReturn(ticket);

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numberOfLines\": 2}")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void checkStatus() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        Mockito.when(ticketService.checkStatus(1L)).thenReturn(ticket);

        mockMvc.perform(put("/ticket/status/1")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
