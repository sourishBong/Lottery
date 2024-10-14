package com.lottery.system.controller;

import com.lottery.system.model.Ticket;
import com.lottery.system.service.TicketService;
import com.lottery.system.dto.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {
        Ticket ticket = ticketService.createTicket(ticketRequest.getNumberOfLines());
        return ResponseEntity.status(201).body(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> amendTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {
        Ticket ticket = ticketService.amendTicket(id, ticketRequest.getNumberOfLines());
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Ticket> checkStatus(@PathVariable Long id) {
        Ticket ticket = ticketService.checkStatus(id);
        return ResponseEntity.ok(ticket);
    }
}
