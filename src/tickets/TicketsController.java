package tickets;

import java.util.List;

public interface TicketsController {

    void setDispenserState(DispenserState state);

    DispenserState getDispenserState();

    void selectTicket(Ticket ticket);

    void clearTicketList();

    void emptyUserBank();

    void addCoin(Coin coin);

    List<Ticket> getStudentTickets();

    List<Ticket> getNormalTickets();

}
