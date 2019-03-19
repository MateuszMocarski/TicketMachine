package tickets;

public enum Coin {
    TEN_CENTS(10l),
    TWENTY_CENTS(20l),
    FIFTY_CENTS(50l),
    ONE_PLN(100),
    TWO_PLN(200l),
    FIVE_PLN(500l);

    private final long monetaryValue;

    private Coin(long monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public long getMonetaryValue() {
        return monetaryValue;
    }
}
