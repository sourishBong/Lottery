package com.lottery.system.service;

import com.lottery.system.exception.TicketNotFoundException;
import com.lottery.system.model.Line;
import com.lottery.system.model.Ticket;
import com.lottery.system.repository.TicketRepository;
import com.lottery.system.util.ResultCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(int numberOfLines) {
        Ticket ticket = new Ticket();
        ticket.setLines(generateLines(numberOfLines));
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket amendTicket(Long id, int numberOfLines) {
        Ticket ticket = getTicket(id);
        if (ticket.isStatusChecked()) {
            throw new IllegalStateException("Ticket status already checked, cannot amend.");
        }
        ticket.getLines().addAll(generateLines(numberOfLines));
        return ticketRepository.save(ticket);
    }

    public Ticket checkStatus(Long id) {
        Ticket ticket = getTicket(id);
        ticket.setStatusChecked(true);
        ticket.getLines().sort(Comparator.comparingInt(Line::getResult).reversed());
        return ticketRepository.save(ticket);
    }

    private List<Line> generateLines(int numberOfLines) {
        return IntStream.range(0, numberOfLines)
                .mapToObj(i -> {
                    int[] numbers = new int[] { randomInt(), randomInt(), randomInt() };
                    int result = ResultCalculator.calculateResult(numbers);
                    return new Line(numbers, result);
                })
                .collect(Collectors.toList());
    }

    // returns 0, 1, or 2
    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(0, 3);
    }
}