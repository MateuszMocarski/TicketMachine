package display;

import java.util.List;
import tickets.Ticket;

public interface TicketDispenserView {

    void showView();

    void showMainMenu();

    void showTicketChoice();

    void showTicketPayment();

    void showSelectedTicketCard(Long totalAmount, List<Ticket> chosenTickets);

    void updatePaymentAmounts(Long totalAmount, Long amountLeft, Long userBank, Long userRest);

    void setLockUnlockPrint(boolean isEnabled);

    void showTicketSummary();

    void showTouristInfo();

    void showBarbakanInfo();

    void showKopiecKrakusaInfo();

    void showKosciolMariackiInfo();

    void showRynekGlownyInfo();

    void showSukienniceInfo();

    void showWawelInfo();

}
