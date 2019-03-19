package tickets;

import display.TicketDispenserViewImpl;
import display.TicketDispenserView;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.machineModel;
import model.userModel;
import utils.FormatUtils;
import utils.InputOutputUtils;

public class TicketsControllerImpl implements Runnable, TicketsController {

    private final TicketDispenserView view;

    private final InputOutputUtils inputOutputUtils;

    private final List<Ticket> chosenTickets;

    private DispenserState currentState = DispenserState.MAIN_MENU;

    public static void main(String[] args) {
        new Thread(new TicketsControllerImpl()).start();
    }

    public TicketsControllerImpl() {
        this.view = new TicketDispenserViewImpl(this);
        this.inputOutputUtils = new InputOutputUtils(this);
        inputOutputUtils.setLockUnlockPayment(false);
        machineModel.ticketPaperAmount = 10000000L;
        machineModel.coinsInBank.put(Coin.ONE_PLN, 100000l);
        machineModel.coinsInBank.put(Coin.TWO_PLN, 100000l);
        machineModel.coinsInBank.put(Coin.FIVE_PLN, 100000l);
        machineModel.coinsInBank.put(Coin.TEN_CENTS, 100000l);
        machineModel.coinsInBank.put(Coin.TWENTY_CENTS, 100000l);
        machineModel.coinsInBank.put(Coin.FIFTY_CENTS, 100000l);
        this.chosenTickets = new ArrayList<>();
    }

    @Override
    public void run() {
        this.view.showView();
        this.inputOutputUtils.showUtils();
    }

    @Override
    public void selectTicket(Ticket ticket) {
        if (!paperChecker(this.chosenTickets)) {
            this.chosenTickets.add(ticket);
            inputOutputUtils.appendString(ticket.toString());
            inputOutputUtils.appendString("" + FormatUtils.formatMonetaryValue(getSelectedTicketsPrice()));
            setDispenserState(DispenserState.TICKET_SUMMARY);
        }
    }

    private Long getSelectedTicketsPrice() {
        Long totalAmountOfMoney = 0l;
        for (Ticket ticket : chosenTickets) {
            totalAmountOfMoney += ticket.getMonetaryValue();
        }
        return totalAmountOfMoney;
    }

    private boolean paperChecker(List list) {
        return machineModel.ticketPaperAmount < list.size();
    }

    private String paperError() {
        if (paperChecker(chosenTickets)) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame,
                    "Not enough paper in this machine/Brak wystarczającej ilości papieru",
                    "Paper error/Błąd papieru",
                    JOptionPane.ERROR_MESSAGE);
            clearTicketList();
            return "Nie można wydać biletów ze względu na brak papieru";
        } else {
            spendTickets();
            return "W automacie znajduje się odpowiednia ilość papieru do wydrukowania biletów";

        }

    }

    private void spendTickets() {
        for (Ticket ticket : chosenTickets) {
            inputOutputUtils.appendString("Wydano bilet: " + ticket.name());
            machineModel.ticketPaperAmount -= 1;
        }

    }

    @Override
    public void clearTicketList() {
        chosenTickets.clear();
    }

    private boolean paymentButtonsEnabler() {
        return DispenserState.PAY_FOR_TICKETS.equals(this.getDispenserState()) && getAmountLeft().compareTo(0l) > 0;
    }

    @Override
    public void addCoin(Coin coin) {
        if (DispenserState.PAY_FOR_TICKETS.equals(getDispenserState())) {
            userModel.currentUserBank.add(coin);
            Long oldValue = machineModel.coinsInBank.get(coin);
            Long newValue = oldValue + 1l;
            machineModel.coinsInBank.replace(coin, oldValue, newValue);
            requestPaymentValueRefresh();
            inputOutputUtils.setLockUnlockPayment(paymentButtonsEnabler());

            inputOutputUtils.appendString("Coin inserted [" + FormatUtils.formatMonetaryValue(coin.getMonetaryValue()) + "]");
        } else {
            inputOutputUtils.appendString("Invalid Dispenser State [" + getDispenserState().name() + "]");

        }
        view.setLockUnlockPrint(printButtonEnabler());
        inputOutputUtils.appendString("--------------------------------------------");

    }

    @Override
    public void setDispenserState(DispenserState state) {
        currentState = state;
        switch (currentState) {
            case MAIN_MENU:
                view.showMainMenu();
                break;
            case CHOOSE_TICKET:
                view.showTicketChoice();
                break;
            case TOURIST_INFO:
                view.showTouristInfo();
                break;
            case TICKET_SUMMARY:
                view.showSelectedTicketCard(getSelectedTicketsPrice(), chosenTickets);
                break;
            case PAY_FOR_TICKETS:
                view.setLockUnlockPrint(false);
                requestPaymentValueRefresh();
                view.showTicketPayment();
                break;
            case SUMMARY:
                view.showTicketSummary();
                this.printSummaryOfOperations();
                break;
            case CANCEL_TICKETS:
                break;
            case BARBAKAN:
                view.showBarbakanInfo();
                break;
            case KOPIEC_KRAKUSA:
                view.showKopiecKrakusaInfo();
                break;
            case KOSCIOL_MARIACKI:
                view.showKosciolMariackiInfo();
                break;
            case RYNEK_GLOWNY:
                view.showRynekGlownyInfo();
                break;
            case SUKIENNICE:
                view.showSukienniceInfo();
                break;
            case WAWEL:
                view.showWawelInfo();
                break;
            default:
                throw new AssertionError(currentState.name());
        }
        inputOutputUtils.setLockUnlockPayment(paymentButtonsEnabler());
    }

    @Override
    public DispenserState getDispenserState() {
        return currentState;
    }

    private Long getAmountLeft() {
        Long amountLeft = getSelectedTicketsPrice() - getUserBankValue();
        if (amountLeft.compareTo(0l) < 0) {
            amountLeft = 0l;
        }
        return amountLeft;
    }

    private Long getRestAmount() {
        if (getAmountLeft().compareTo(0l) > 0) {
            return 0l;
        } else {
            return getUserBankValue() - getSelectedTicketsPrice();
        }
    }

    private void payRest() {
        Long rest = getRestAmount();
        Long sum = 0l;
        if (rest.compareTo(0l) == 0) {
            inputOutputUtils.appendString("Brak reszty");
        }
        while (rest > 0) {
            if (rest % 500 == 0 && machineModel.coinsInBank.get(Coin.FIVE_PLN) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.FIVE_PLN);
                Long newValue = machineModel.coinsInBank.get(Coin.FIVE_PLN) - 1l;
                machineModel.coinsInBank.replace(Coin.FIVE_PLN, oldValue, newValue);
                sum += 500;
                rest -= 500;
                inputOutputUtils.appendString("Wydano monetę 5zł");
                continue;
            }
            if (rest % 200 == 0 && machineModel.coinsInBank.get(Coin.TWO_PLN) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.TWO_PLN);
                Long newValue = machineModel.coinsInBank.get(Coin.TWO_PLN) - 1l;
                machineModel.coinsInBank.replace(Coin.TWO_PLN, oldValue, newValue);
                sum += 200;
                rest -= 200;
                inputOutputUtils.appendString("Wydano monetę 2zł");
                continue;
            }
            if (rest % 100 == 0 && machineModel.coinsInBank.get(Coin.ONE_PLN) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.ONE_PLN);
                Long newValue = machineModel.coinsInBank.get(Coin.ONE_PLN) - 1l;
                machineModel.coinsInBank.replace(Coin.ONE_PLN, oldValue, newValue);
                sum += 100;
                rest -= 100;
                inputOutputUtils.appendString("Wydano monetę 1zł");
                continue;
            }
            if (rest % 50 == 0 && machineModel.coinsInBank.get(Coin.FIFTY_CENTS) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.FIFTY_CENTS);
                Long newValue = machineModel.coinsInBank.get(Coin.FIFTY_CENTS) - 1l;
                machineModel.coinsInBank.replace(Coin.FIFTY_CENTS, oldValue, newValue);
                sum += 50;
                rest -= 50;
                inputOutputUtils.appendString("Wydano monetę 50gr");
                continue;
            }
            if (rest % 20 == 0 && machineModel.coinsInBank.get(Coin.TWENTY_CENTS) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.TWENTY_CENTS);
                Long newValue = machineModel.coinsInBank.get(Coin.TWENTY_CENTS) - 1l;
                machineModel.coinsInBank.replace(Coin.TWENTY_CENTS, oldValue, newValue);
                sum += 20;
                rest -= 20;
                inputOutputUtils.appendString("Wydano monetę 20gr");
                continue;
            }
            if (rest % 10 == 0 && machineModel.coinsInBank.get(Coin.TEN_CENTS) >= 1) {
                Long oldValue = machineModel.coinsInBank.get(Coin.TEN_CENTS);
                Long newValue = machineModel.coinsInBank.get(Coin.TEN_CENTS) - 1l;
                machineModel.coinsInBank.replace(Coin.TEN_CENTS, oldValue, newValue);
                sum += 10;
                rest -= 10;
                inputOutputUtils.appendString("Wydano monetę 10gr");
                continue;
            }
            System.out.println("Wydano resztę w wysokości: " + FormatUtils.formatMonetaryValue(sum));

        }
    }

    private void requestPaymentValueRefresh() {

        view.updatePaymentAmounts(getSelectedTicketsPrice(), getAmountLeft(), getUserBankValue(), getRestAmount());
    }

    @Override
    public void emptyUserBank() {
        userModel.currentUserBank.clear();
    }

    private Long getUserBankValue() {
        Long value = 0l;
        for (Coin coin : userModel.currentUserBank) {
            value += coin.getMonetaryValue();
        }
        return value;
    }

    @Override
    public List<Ticket> getStudentTickets() {
        List<Ticket> studentTickets = new ArrayList<>();
        for (Ticket ticket : Ticket.values()) {
            if (ticket.isStudent()) {
                studentTickets.add(ticket);
            }
        }
        return studentTickets;
    }

    @Override
    public List<Ticket> getNormalTickets() {
        List<Ticket> normalTickets = new ArrayList<>();
        for (Ticket ticket : Ticket.values()) {
            if (!ticket.isStudent()) {
                normalTickets.add(ticket);
            }
        }
        return normalTickets;
    }

    private boolean printButtonEnabler() {
        return getAmountLeft().compareTo(0l) <= 0;
    }

    public void printSummaryOfOperations() {
        inputOutputUtils.appendString(paperError());
        payRest();
        emptyUserBank();
        clearTicketList();
    }

}
