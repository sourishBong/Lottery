package com.lottery.system;

import com.lottery.system.model.Line;
import com.lottery.system.model.Ticket;
import com.lottery.system.repository.TicketRepository;
import com.lottery.system.service.TicketService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TicketServiceTest {

    @Mock  // Mock the repository
    private TicketRepository ticketRepository;

    @InjectMocks  // Inject the mock into the service
    private TicketService ticketService;

    @Test
    void createTicket() {
        Ticket ticket = new Ticket();
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.createTicket(3);
        assertNotNull(result);
        Mockito.verify(ticketRepository, Mockito.times(1)).save(Mockito.any(Ticket.class));
    }

    @Test
    void getAllTickets() {
        List<Ticket> tickets = List.of(new Ticket(), new Ticket());
        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getAllTickets();
        assertEquals(2, result.size());
    }

    @Test
    void getTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicket(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void amendTicket_afterStatusChecked_throwsException() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatusChecked(true);  // Simulate status already being checked

        // Mock the repository to return the ticket with status already checked
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ticketService.amendTicket(1L, 3);
        });

        // Verify the exception message
        assertEquals("Ticket status already checked, cannot amend.", exception.getMessage());

        // Verify that the save method was never called because an exception was thrown
        Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(Ticket.class));
    }

    @Test
    void checkStatus() {
        //Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatusChecked(false);

        // Initialize the 'lines' list and set it to the ticket
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(new int[]{1, 2, 3}, 10));  // Assuming a constructor for Line exists
        ticket.setLines(lines);

        // Mock the repository behavior
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        // Act
        Ticket result = ticketService.checkStatus(1L);

        // Assert
        assertTrue(result.isStatusChecked());
        Mockito.verify(ticketRepository, Mockito.times(1)).save(Mockito.any(Ticket.class));
        assertFalse(result.getLines().isEmpty());
    }
}
