package tickets;

public enum Ticket {
    STUDENT_ONE_JOURNEY(190l, true),
    STUDENT_TWO_JOURNEYS(360l, true),
    STUDENT_TWENTY_MINUTES(140l, true),
    STUDENT_FORTY_MINUTES(190l, true),
    STUDENT_SIXTY_MINUTES(250l, true),
    STUDENT_NINTY_MINUTES(300l, true),
    STUDENT_24_HOURS(750l, true),
    STUDENT_48_HOURS(1200l, true),
    STUDENT_72_HOURS(1800l, true),
    STUDENT_7_DAYS(2400l, true),
    STUDENT_GROUP(3000l, true),
    NORMAL_ONE_JOURNEY(380l, false),
    NORMAL_TWO_JOURNEYS(720l, false),
    NORMAL_TWENTY_MINUTES(280l, false),
    NORMAL_FORTY_MINUTES(380l, false),
    NORMAL_SIXTY_MINUTES(500l, false),
    NORMAL_NINTY_MINUTES(600l, false),
    NORMAL_24_HOURS(1500l, false),
    NORMAL_48_HOURS(2400l, false),
    NORMAL_72_HOURS(3600l, false),
    NORMAL_7_DAYS(4800l, false),
    NORMAL_GROUP(6000l, false);

    private final long monetaryValue;
    private final boolean student;

    private Ticket(long monetaryValue, boolean student) {
        this.monetaryValue = monetaryValue;
        this.student = student;
    }

    public long getMonetaryValue() {
        return monetaryValue;
    }

    public boolean isStudent() {
        return student;
    }
}
