package display;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.File;
import tickets.DispenserState;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tickets.Ticket;
import tickets.TicketsController;
import utils.FormatUtils;

public class TicketDispenserViewImpl extends javax.swing.JFrame implements TicketDispenserView {

    private final TicketsController controller;

    private final Properties plTranslations = new Properties();
    private final Properties enTranslations = new Properties();

    private Language currentLanguage;

    private final EnumMap<Ticket, JButton> ticketButtons = new EnumMap<>(Ticket.class);

    public TicketDispenserViewImpl(TicketsController controller) {
        this.controller = controller;
        initComponents();
        initTranslations();
        initTicketSelections();
        setLanguage(Language.POLISH);
    }

    private void initTranslations() {
        try {
            File pl = new File("src/display/i18n/pl_translation.properties");
            File en = new File("src/display/i18n/en_translation.properties");
            InputStream plStream = new FileInputStream(pl);
            plTranslations.load(plStream);
            InputStream enStream = new FileInputStream(en);
            enTranslations.load(enStream);
        } catch (IOException ex) {
            Logger.getLogger(TicketDispenserViewImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLanguage(Language language) {
        currentLanguage = language;
        switch (currentLanguage) {
            case POLISH:
                polishLanguage.setEnabled(false);
                englishLanguage.setSelected(false);
                englishLanguage.setEnabled(true);
                break;
            case ENGLISH:
                englishLanguage.setEnabled(false);
                polishLanguage.setSelected(false);
                polishLanguage.setEnabled(true);
                break;
        }
        translateComponents(getTranslationsForCurrentLanguage());
    }

    private Properties getTranslationsForCurrentLanguage() {
        switch (currentLanguage) {
            case POLISH:
                return plTranslations;
            case ENGLISH:
                return enTranslations;
            default:
                return plTranslations;
        }
    }

    private void translateComponents(Properties translations) {
        translateTopMainLabel(translations);
        translateTicketButtons(translations);
        buyATicketButton.setText(translations.getProperty("main.menu.buy.a.ticket"));
        touristInfoButton.setText(translations.getProperty("main.menu.tourist.info"));
        normalTicketsLabel.setText(translations.getProperty("choose.ticket.normal.tickets.label"));
        studentTicketsLabel.setText(translations.getProperty("choose.ticket.student.tickets.label"));
        totalAmountLabel.setText(translations.getProperty("pay.for.ticket.total.amount.label"));
        amountLeftLabel.setText(translations.getProperty("pay.for.ticket.amount.left.label"));
        cancelPaymentButton.setText(translations.getProperty("pay.for.ticket.cancel.payment"));
        userBankLabel.setText(translations.getProperty("pay.for.ticket.user.bank.label"));
        addAnotherTicketButton.setText(translations.getProperty("add.another.ticket"));
        goToPayForTicketsCard.setText(translations.getProperty("goto.payment"));
        amountPriceLabel.setText(translations.getProperty("amount.price.label"));
        cancelSelection.setText(translations.getProperty("pay.for.ticket.cancel.payment"));
        goToPrintTicketsButton.setText(translations.getProperty("print.ticket.button"));
        changeAmountLabel.setText(translations.getProperty("change.amount.label"));
        goToMainMenuButton.setText(translations.getProperty("go.to.main.menu.button"));
        mainMenuReturnButton.setText(translations.getProperty("go.to.main.menu.button"));
        backToWorthToSeeButton.setText(translations.getProperty("go.to.tourist.menu.button"));
        backToWorthToSeeButton1.setText(translations.getProperty("go.to.tourist.menu.button"));
        backToWorthToSeeButton2.setText(translations.getProperty("go.to.tourist.menu.button"));
        backToWorthToSeeButton3.setText(translations.getProperty("go.to.tourist.menu.button"));
        backToWorthToSeeButton4.setText(translations.getProperty("go.to.tourist.menu.button"));
        backToWorthToSeeButton5.setText(translations.getProperty("go.to.tourist.menu.button"));
        thanksLabel.setText(translations.getProperty("thanks.label"));
        barbakanButton.setText(translations.getProperty("barbakan.label"));
        kopiecKrakusaButton.setText(translations.getProperty("kopiec.krakusa.label"));
        kosciolMariackiButton.setText(translations.getProperty("kosciol.mariacki.label"));
        rynekGlownyButton.setText(translations.getProperty("rynek.glowny.label"));
        sukienniceButton.setText(translations.getProperty("sukiennice.label"));
        wawelButton.setText(translations.getProperty("wawel.label"));
        barbakanDescriptionTextArea.setText(translations.getProperty("barbakan.desc"));
        barbakanImgLabel.setText("");
        kopiecKrakusaDescriptionTextArea.setText(translations.getProperty("kopiec.krakusa.desc"));
        kopiecKrakaImgLabel.setText("");
        kosciolMariackiDescriptionTextArea.setText(translations.getProperty("kosciol.mariacki.desc"));
        kosciolMariackiImgLabel.setText("");
        rynekGlownyDescriptionTextArea.setText(translations.getProperty("rynek.glowny.desc"));
        rynekGlownyImgLabel.setText("");
        sukienniceDescriptionTextArea.setText(translations.getProperty("sukiennice.desc"));
        sukienniceImgLabel.setText("");
        wawelDescriptionTextArea.setText(translations.getProperty("wawel.desc"));
        wawelImgLabel.setText("");

    }

    private void translateTopMainLabel(Properties translations) {
        topMainPanelLabel.setText(translations.getProperty(getMainLabelTranslationKey()));
    }

    private void translateTicketButtons(Properties translations) {
        for (Map.Entry<Ticket, JButton> entry : ticketButtons.entrySet()) {
            String text = translations.getProperty("ticket." + entry.getKey().name() + ".label");
            text = text + " " + FormatUtils.formatMonetaryValue(entry.getKey().getMonetaryValue());
            entry.getValue().setText(text);
        }
    }

    private String getMainLabelTranslationKey() {
        switch (controller.getDispenserState()) {
            case MAIN_MENU:
                return "top.panel.main.label.main.menu";
            case CHOOSE_TICKET:
                return "top.panel.main.label.choose.ticket";
            case TOURIST_INFO:
                return "top.panel.main.label.tourist.info";
            case TICKET_SUMMARY:
                return "top.panel.main.label.ticket.summary";
            case PAY_FOR_TICKETS:
                return "top.panel.main.label.pay.for.tickets";
            case CANCEL_TICKETS:
                return "top.panel.main.label.cancel.tickets";
            case SUMMARY:
                return "top.panel.main.label.summary";
            case BARBAKAN:
                return "barbakan.label";
            case KOPIEC_KRAKUSA:
                return "kopiec.krakusa.label";
            case KOSCIOL_MARIACKI:
                return "kosciol.mariacki.label";
            case RYNEK_GLOWNY:
                return "rynek.glowny.label";
            case SUKIENNICE:
                return "sukiennice.label";
            case WAWEL:
                return "wawel.label";
            default:
                throw new AssertionError(controller.getDispenserState());
        }
    }

    private void requestSwitchDispenserState(DispenserState state) {
        controller.setDispenserState(state);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bottomMainPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JPanel();
        buyATicketButton = new javax.swing.JButton();
        touristInfoButton = new javax.swing.JButton();
        chooseTicket = new javax.swing.JPanel();
        studentTicketsPanel = new javax.swing.JPanel();
        studentTicketsLabel = new javax.swing.JLabel();
        normalTicketsPanel = new javax.swing.JPanel();
        normalTicketsLabel = new javax.swing.JLabel();
        selectedTicketsCard = new javax.swing.JPanel();
        amountPriceLabel = new javax.swing.JLabel();
        amountPriceValue = new javax.swing.JLabel();
        addAnotherTicketButton = new javax.swing.JButton();
        goToPayForTicketsCard = new javax.swing.JButton();
        cancelSelection = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chosenTicketsConsoleOutput = new javax.swing.JTextArea();
        payForTickets = new javax.swing.JPanel();
        totalAmountLabel = new javax.swing.JLabel();
        totalAmountValue = new javax.swing.JLabel();
        userBankLabel = new javax.swing.JLabel();
        userBankValue = new javax.swing.JLabel();
        amountLeftLabel = new javax.swing.JLabel();
        amountLeftValue = new javax.swing.JLabel();
        changeAmountLabel = new javax.swing.JLabel();
        changeAmountValue = new javax.swing.JLabel();
        cancelPaymentButton = new javax.swing.JButton();
        goToPrintTicketsButton = new javax.swing.JButton();
        summaryCard = new javax.swing.JPanel();
        goToMainMenuButton = new javax.swing.JButton();
        thanksLabel = new javax.swing.JLabel();
        touristInfoCard = new javax.swing.JPanel();
        barbakanButton = new javax.swing.JButton();
        kopiecKrakusaButton = new javax.swing.JButton();
        kosciolMariackiButton = new javax.swing.JButton();
        rynekGlownyButton = new javax.swing.JButton();
        sukienniceButton = new javax.swing.JButton();
        wawelButton = new javax.swing.JButton();
        mainMenuReturnButton = new javax.swing.JButton();
        barbakanCard = new javax.swing.JPanel();
        barbakanImgLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        barbakanDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton = new javax.swing.JButton();
        kopiecKrakaCard = new javax.swing.JPanel();
        kopiecKrakaImgLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        kopiecKrakusaDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton1 = new javax.swing.JButton();
        kosciolMariackiCard = new javax.swing.JPanel();
        kosciolMariackiImgLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        kosciolMariackiDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton2 = new javax.swing.JButton();
        rynekGlownyCard = new javax.swing.JPanel();
        rynekGlownyImgLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        rynekGlownyDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton3 = new javax.swing.JButton();
        sukienniceCard = new javax.swing.JPanel();
        sukienniceImgLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        sukienniceDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton4 = new javax.swing.JButton();
        wawelCard = new javax.swing.JPanel();
        wawelImgLabel = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        wawelDescriptionTextArea = new javax.swing.JTextArea();
        backToWorthToSeeButton5 = new javax.swing.JButton();
        topMainPanel = new javax.swing.JPanel();
        englishLanguage = new javax.swing.JToggleButton();
        polishLanguage = new javax.swing.JToggleButton();
        topMainPanelLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bottomMainPanel.setLayout(new java.awt.CardLayout());

        mainMenu.setLayout(new java.awt.GridLayout(2, 1));

        buyATicketButton.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        buyATicketButton.setText("jButton1");
        buyATicketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyATicketButtonActionPerformed(evt);
            }
        });
        mainMenu.add(buyATicketButton);

        touristInfoButton.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        touristInfoButton.setText("jButton1");
        touristInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                touristInfoButtonActionPerformed(evt);
            }
        });
        mainMenu.add(touristInfoButton);

        bottomMainPanel.add(mainMenu, "mainMenuCard");

        chooseTicket.setLayout(new java.awt.GridLayout(1, 2, 1, 0));

        studentTicketsPanel.setLayout(new java.awt.GridLayout(11, 1));

        studentTicketsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentTicketsLabel.setText("jLabel1");
        studentTicketsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        studentTicketsLabel.setMaximumSize(new java.awt.Dimension(34, 10));
        studentTicketsLabel.setMinimumSize(new java.awt.Dimension(34, 10));
        studentTicketsPanel.add(studentTicketsLabel);

        chooseTicket.add(studentTicketsPanel);

        normalTicketsPanel.setLayout(new java.awt.GridLayout(11, 1));

        normalTicketsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        normalTicketsLabel.setText("jLabel2");
        normalTicketsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        normalTicketsPanel.add(normalTicketsLabel);

        chooseTicket.add(normalTicketsPanel);

        bottomMainPanel.add(chooseTicket, "chooseTicketCard");

        selectedTicketsCard.setToolTipText("");

        amountPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amountPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amountPriceLabel.setText("jLabel1");
        amountPriceLabel.setToolTipText("");
        amountPriceLabel.setMaximumSize(new java.awt.Dimension(40, 15));

        amountPriceValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amountPriceValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountPriceValue.setText("jLabel2");
        amountPriceValue.setToolTipText("");

        addAnotherTicketButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        addAnotherTicketButton.setText("jButton1");
        addAnotherTicketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAnotherTicketButtonActionPerformed(evt);
            }
        });

        goToPayForTicketsCard.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        goToPayForTicketsCard.setText("jButton2");
        goToPayForTicketsCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToPayForTicketsCardActionPerformed(evt);
            }
        });

        cancelSelection.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cancelSelection.setText("jButton1");
        cancelSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelSelectionActionPerformed(evt);
            }
        });

        chosenTicketsConsoleOutput.setEditable(false);
        chosenTicketsConsoleOutput.setBackground(new java.awt.Color(240, 240, 240));
        chosenTicketsConsoleOutput.setColumns(20);
        chosenTicketsConsoleOutput.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chosenTicketsConsoleOutput.setRows(5);
        chosenTicketsConsoleOutput.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(chosenTicketsConsoleOutput);

        javax.swing.GroupLayout selectedTicketsCardLayout = new javax.swing.GroupLayout(selectedTicketsCard);
        selectedTicketsCard.setLayout(selectedTicketsCardLayout);
        selectedTicketsCardLayout.setHorizontalGroup(
            selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectedTicketsCardLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectedTicketsCardLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(amountPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(amountPriceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(selectedTicketsCardLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cancelSelection, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(goToPayForTicketsCard, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(addAnotherTicketButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        selectedTicketsCardLayout.setVerticalGroup(
            selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectedTicketsCardLayout.createSequentialGroup()
                .addGroup(selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(selectedTicketsCardLayout.createSequentialGroup()
                        .addContainerGap(89, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(selectedTicketsCardLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(selectedTicketsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(amountPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(amountPriceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addAnotherTicketButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(goToPayForTicketsCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addGap(21, 21, 21))
        );

        bottomMainPanel.add(selectedTicketsCard, "selectedTicketsCard");

        payForTickets.setLayout(new java.awt.GridLayout(5, 2));

        totalAmountLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmountLabel.setText("jLabel1");
        totalAmountLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        payForTickets.add(totalAmountLabel);

        totalAmountValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalAmountValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalAmountValue.setText("jLabel2");
        payForTickets.add(totalAmountValue);

        userBankLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userBankLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        userBankLabel.setText("jLabel2");
        payForTickets.add(userBankLabel);

        userBankValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userBankValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userBankValue.setText("jLabel1");
        payForTickets.add(userBankValue);

        amountLeftLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amountLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amountLeftLabel.setText("jLabel4");
        amountLeftLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        payForTickets.add(amountLeftLabel);

        amountLeftValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amountLeftValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountLeftValue.setText("jLabel3");
        payForTickets.add(amountLeftValue);

        changeAmountLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        changeAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        changeAmountLabel.setText("jLabel2");
        payForTickets.add(changeAmountLabel);

        changeAmountValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        changeAmountValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changeAmountValue.setText("jLabel1");
        payForTickets.add(changeAmountValue);

        cancelPaymentButton.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        cancelPaymentButton.setText("jButton1");
        cancelPaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelPaymentButtonActionPerformed(evt);
            }
        });
        payForTickets.add(cancelPaymentButton);

        goToPrintTicketsButton.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        goToPrintTicketsButton.setText("jButton1");
        goToPrintTicketsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToPrintTicketsButtonActionPerformed(evt);
            }
        });
        payForTickets.add(goToPrintTicketsButton);

        bottomMainPanel.add(payForTickets, "payForTicketsCard");

        summaryCard.setName("summaryCard"); // NOI18N

        goToMainMenuButton.setText("jButton1");
        goToMainMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToMainMenuButtonActionPerformed(evt);
            }
        });

        thanksLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        thanksLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        thanksLabel.setText("jLabel1");

        javax.swing.GroupLayout summaryCardLayout = new javax.swing.GroupLayout(summaryCard);
        summaryCard.setLayout(summaryCardLayout);
        summaryCardLayout.setHorizontalGroup(
            summaryCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(summaryCardLayout.createSequentialGroup()
                .addContainerGap(583, Short.MAX_VALUE)
                .addComponent(goToMainMenuButton)
                .addGap(63, 63, 63))
            .addComponent(thanksLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        summaryCardLayout.setVerticalGroup(
            summaryCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, summaryCardLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(thanksLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addGap(90, 90, 90)
                .addComponent(goToMainMenuButton)
                .addContainerGap())
        );

        bottomMainPanel.add(summaryCard, "summaryCard");

        touristInfoCard.setName("touristInfoCard"); // NOI18N

        barbakanButton.setText("jButton1");
        barbakanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barbakanButtonActionPerformed(evt);
            }
        });

        kopiecKrakusaButton.setText("jButton2");
        kopiecKrakusaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kopiecKrakusaButtonActionPerformed(evt);
            }
        });

        kosciolMariackiButton.setText("jButton3");
        kosciolMariackiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kosciolMariackiButtonActionPerformed(evt);
            }
        });

        rynekGlownyButton.setText("jButton4");
        rynekGlownyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rynekGlownyButtonActionPerformed(evt);
            }
        });

        sukienniceButton.setText("jButton5");
        sukienniceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sukienniceButtonActionPerformed(evt);
            }
        });

        wawelButton.setText("jButton6");
        wawelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wawelButtonActionPerformed(evt);
            }
        });

        mainMenuReturnButton.setText("jButton1");
        mainMenuReturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuReturnButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout touristInfoCardLayout = new javax.swing.GroupLayout(touristInfoCard);
        touristInfoCard.setLayout(touristInfoCardLayout);
        touristInfoCardLayout.setHorizontalGroup(
            touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, touristInfoCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kosciolMariackiButton, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(barbakanButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sukienniceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rynekGlownyButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kopiecKrakusaButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wawelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, touristInfoCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainMenuReturnButton)
                .addContainerGap())
        );
        touristInfoCardLayout.setVerticalGroup(
            touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(touristInfoCardLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(barbakanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kopiecKrakusaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kosciolMariackiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rynekGlownyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(touristInfoCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wawelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sukienniceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(mainMenuReturnButton)
                .addContainerGap())
        );

        bottomMainPanel.add(touristInfoCard, "touristInfoCard");

        barbakanCard.setName("barbakanCard"); // NOI18N

        barbakanImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/barbakan.jpg")));
        barbakanImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        barbakanImgLabel.setText("jLabel1");
        barbakanImgLabel.setName(""); // NOI18N

        barbakanDescriptionTextArea.setEditable(false);
        barbakanDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        barbakanDescriptionTextArea.setColumns(20);
        barbakanDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        barbakanDescriptionTextArea.setLineWrap(true);
        barbakanDescriptionTextArea.setRows(5);
        barbakanDescriptionTextArea.setWrapStyleWord(true);
        barbakanDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        barbakanDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane2.setViewportView(barbakanDescriptionTextArea);

        backToWorthToSeeButton.setText("jButton1");
        backToWorthToSeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout barbakanCardLayout = new javax.swing.GroupLayout(barbakanCard);
        barbakanCard.setLayout(barbakanCardLayout);
        barbakanCardLayout.setHorizontalGroup(
            barbakanCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barbakanCardLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(barbakanCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barbakanCardLayout.createSequentialGroup()
                        .addGroup(barbakanCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                            .addComponent(barbakanImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barbakanCardLayout.createSequentialGroup()
                        .addComponent(backToWorthToSeeButton)
                        .addContainerGap())))
        );
        barbakanCardLayout.setVerticalGroup(
            barbakanCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barbakanCardLayout.createSequentialGroup()
                .addComponent(barbakanImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backToWorthToSeeButton)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bottomMainPanel.add(barbakanCard, "barbakanCard");

        kopiecKrakaCard.setName("kopiecKrakusaCard"); // NOI18N

        kopiecKrakaImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/kopiecKrakusa.jpg")));
        kopiecKrakaImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kopiecKrakaImgLabel.setText("jLabel1");

        kopiecKrakusaDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        kopiecKrakusaDescriptionTextArea.setColumns(20);
        kopiecKrakusaDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kopiecKrakusaDescriptionTextArea.setLineWrap(true);
        kopiecKrakusaDescriptionTextArea.setRows(5);
        kopiecKrakusaDescriptionTextArea.setWrapStyleWord(true);
        kopiecKrakusaDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        kopiecKrakusaDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane3.setViewportView(kopiecKrakusaDescriptionTextArea);

        backToWorthToSeeButton1.setText("jButton1");
        backToWorthToSeeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kopiecKrakaCardLayout = new javax.swing.GroupLayout(kopiecKrakaCard);
        kopiecKrakaCard.setLayout(kopiecKrakaCardLayout);
        kopiecKrakaCardLayout.setHorizontalGroup(
            kopiecKrakaCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kopiecKrakaCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(kopiecKrakaCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(kopiecKrakaImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kopiecKrakaCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToWorthToSeeButton1)
                .addContainerGap())
        );
        kopiecKrakaCardLayout.setVerticalGroup(
            kopiecKrakaCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kopiecKrakaCardLayout.createSequentialGroup()
                .addComponent(kopiecKrakaImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(backToWorthToSeeButton1)
                .addContainerGap())
        );

        bottomMainPanel.add(kopiecKrakaCard, "kopiecKrakusaCard");

        kosciolMariackiImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/kosciolMariacki.jpg")));
        kosciolMariackiImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kosciolMariackiImgLabel.setText("jLabel1");

        kosciolMariackiDescriptionTextArea.setEditable(false);
        kosciolMariackiDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        kosciolMariackiDescriptionTextArea.setColumns(20);
        kosciolMariackiDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kosciolMariackiDescriptionTextArea.setLineWrap(true);
        kosciolMariackiDescriptionTextArea.setRows(5);
        kosciolMariackiDescriptionTextArea.setWrapStyleWord(true);
        kosciolMariackiDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        kosciolMariackiDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane4.setViewportView(kosciolMariackiDescriptionTextArea);

        backToWorthToSeeButton2.setText("jButton1");
        backToWorthToSeeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kosciolMariackiCardLayout = new javax.swing.GroupLayout(kosciolMariackiCard);
        kosciolMariackiCard.setLayout(kosciolMariackiCardLayout);
        kosciolMariackiCardLayout.setHorizontalGroup(
            kosciolMariackiCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kosciolMariackiCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(kosciolMariackiCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kosciolMariackiImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kosciolMariackiCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToWorthToSeeButton2)
                .addContainerGap())
        );
        kosciolMariackiCardLayout.setVerticalGroup(
            kosciolMariackiCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kosciolMariackiCardLayout.createSequentialGroup()
                .addComponent(kosciolMariackiImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(backToWorthToSeeButton2)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bottomMainPanel.add(kosciolMariackiCard, "kosciolMariackiCard");

        rynekGlownyImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/rynekGlowny.jpg")));
        rynekGlownyImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rynekGlownyImgLabel.setText("jLabel1");

        rynekGlownyDescriptionTextArea.setEditable(false);
        rynekGlownyDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        rynekGlownyDescriptionTextArea.setColumns(20);
        rynekGlownyDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rynekGlownyDescriptionTextArea.setLineWrap(true);
        rynekGlownyDescriptionTextArea.setRows(5);
        rynekGlownyDescriptionTextArea.setWrapStyleWord(true);
        rynekGlownyDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rynekGlownyDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane5.setViewportView(rynekGlownyDescriptionTextArea);

        backToWorthToSeeButton3.setText("jButton1");
        backToWorthToSeeButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rynekGlownyCardLayout = new javax.swing.GroupLayout(rynekGlownyCard);
        rynekGlownyCard.setLayout(rynekGlownyCardLayout);
        rynekGlownyCardLayout.setHorizontalGroup(
            rynekGlownyCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rynekGlownyCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(rynekGlownyCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rynekGlownyImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rynekGlownyCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToWorthToSeeButton3)
                .addContainerGap())
        );
        rynekGlownyCardLayout.setVerticalGroup(
            rynekGlownyCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rynekGlownyCardLayout.createSequentialGroup()
                .addComponent(rynekGlownyImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backToWorthToSeeButton3)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bottomMainPanel.add(rynekGlownyCard, "rynekGlownyCard");

        sukienniceImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/sukiennice.jpg")));
        sukienniceImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sukienniceImgLabel.setText("jLabel1");
        sukienniceImgLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        sukienniceDescriptionTextArea.setEditable(false);
        sukienniceDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        sukienniceDescriptionTextArea.setColumns(20);
        sukienniceDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sukienniceDescriptionTextArea.setLineWrap(true);
        sukienniceDescriptionTextArea.setRows(5);
        sukienniceDescriptionTextArea.setWrapStyleWord(true);
        sukienniceDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sukienniceDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane6.setViewportView(sukienniceDescriptionTextArea);

        backToWorthToSeeButton4.setText("jButton1");
        backToWorthToSeeButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sukienniceCardLayout = new javax.swing.GroupLayout(sukienniceCard);
        sukienniceCard.setLayout(sukienniceCardLayout);
        sukienniceCardLayout.setHorizontalGroup(
            sukienniceCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sukienniceCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(sukienniceCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sukienniceImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sukienniceCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToWorthToSeeButton4)
                .addContainerGap())
        );
        sukienniceCardLayout.setVerticalGroup(
            sukienniceCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sukienniceCardLayout.createSequentialGroup()
                .addComponent(sukienniceImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backToWorthToSeeButton4)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bottomMainPanel.add(sukienniceCard, "sukienniceCard");

        wawelImgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/wawel.jpg")));
        wawelImgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wawelImgLabel.setText("jLabel1");

        wawelDescriptionTextArea.setEditable(false);
        wawelDescriptionTextArea.setBackground(new java.awt.Color(240, 240, 240));
        wawelDescriptionTextArea.setColumns(20);
        wawelDescriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        wawelDescriptionTextArea.setLineWrap(true);
        wawelDescriptionTextArea.setRows(5);
        wawelDescriptionTextArea.setWrapStyleWord(true);
        wawelDescriptionTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        wawelDescriptionTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane7.setViewportView(wawelDescriptionTextArea);

        backToWorthToSeeButton5.setText("jButton1");
        backToWorthToSeeButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToWorthToSeeButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wawelCardLayout = new javax.swing.GroupLayout(wawelCard);
        wawelCard.setLayout(wawelCardLayout);
        wawelCardLayout.setHorizontalGroup(
            wawelCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wawelCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(wawelCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(wawelImgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wawelCardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToWorthToSeeButton5)
                .addContainerGap())
        );
        wawelCardLayout.setVerticalGroup(
            wawelCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wawelCardLayout.createSequentialGroup()
                .addComponent(wawelImgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backToWorthToSeeButton5)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bottomMainPanel.add(wawelCard, "wawelCard");

        getContentPane().add(bottomMainPanel, java.awt.BorderLayout.CENTER);

        topMainPanel.setMaximumSize(new java.awt.Dimension(32767, 60));
        topMainPanel.setMinimumSize(new java.awt.Dimension(100, 60));
        topMainPanel.setPreferredSize(new java.awt.Dimension(653, 60));
        topMainPanel.setRequestFocusEnabled(false);

        englishLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/en_disabled.png"))); // NOI18N
        englishLanguage.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/en.png"))); // NOI18N
        englishLanguage.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/en.png"))); // NOI18N
        englishLanguage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                englishLanguageItemStateChanged(evt);
            }
        });
        englishLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishLanguageActionPerformed(evt);
            }
        });

        polishLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/pl_disabled.png"))); // NOI18N
        polishLanguage.setSelected(true);
        polishLanguage.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/pl.png"))); // NOI18N
        polishLanguage.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/display/img/pl.png"))); // NOI18N
        polishLanguage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                polishLanguageItemStateChanged(evt);
            }
        });
        polishLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polishLanguageActionPerformed(evt);
            }
        });

        topMainPanelLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout topMainPanelLayout = new javax.swing.GroupLayout(topMainPanel);
        topMainPanel.setLayout(topMainPanelLayout);
        topMainPanelLayout.setHorizontalGroup(
            topMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topMainPanelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(polishLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(englishLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        topMainPanelLayout.setVerticalGroup(
            topMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(polishLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(englishLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(topMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topMainPanelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(topMainPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void polishLanguageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_polishLanguageItemStateChanged
        if (polishLanguage.isSelected()) {
            setLanguage(Language.POLISH);
        }
    }//GEN-LAST:event_polishLanguageItemStateChanged

    private void englishLanguageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_englishLanguageItemStateChanged
        if (englishLanguage.isSelected()) {
            setLanguage(Language.ENGLISH);
        }
    }//GEN-LAST:event_englishLanguageItemStateChanged

    private void buyATicketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyATicketButtonActionPerformed
        requestSwitchDispenserState(DispenserState.CHOOSE_TICKET);

    }//GEN-LAST:event_buyATicketButtonActionPerformed

    private void polishLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polishLanguageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_polishLanguageActionPerformed

    private void englishLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_englishLanguageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_englishLanguageActionPerformed

    private void cancelPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelPaymentButtonActionPerformed
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are You sure You want to stop the transaction? / Czy na pewno chcesz anulować transakcję?", "", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.clearTicketList();
                controller.emptyUserBank();
                requestSwitchDispenserState(DispenserState.MAIN_MENU);
            }
        }
    }//GEN-LAST:event_cancelPaymentButtonActionPerformed

    private void addAnotherTicketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAnotherTicketButtonActionPerformed
        requestSwitchDispenserState(DispenserState.CHOOSE_TICKET);
    }//GEN-LAST:event_addAnotherTicketButtonActionPerformed

    private void goToPayForTicketsCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPayForTicketsCardActionPerformed
        requestSwitchDispenserState(DispenserState.PAY_FOR_TICKETS);
    }//GEN-LAST:event_goToPayForTicketsCardActionPerformed

    private void cancelSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelSelectionActionPerformed
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are You sure You want to stop the transaction? / Czy na pewno chcesz anulować transakcję?", "", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            controller.clearTicketList();
            controller.emptyUserBank();
            requestSwitchDispenserState(DispenserState.MAIN_MENU);
        }
    }//GEN-LAST:event_cancelSelectionActionPerformed

    private void goToPrintTicketsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPrintTicketsButtonActionPerformed
        requestSwitchDispenserState(DispenserState.SUMMARY);
    }//GEN-LAST:event_goToPrintTicketsButtonActionPerformed

    private void goToMainMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToMainMenuButtonActionPerformed
        requestSwitchDispenserState(DispenserState.MAIN_MENU);
    }//GEN-LAST:event_goToMainMenuButtonActionPerformed

    private void touristInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_touristInfoButtonActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_touristInfoButtonActionPerformed

    private void mainMenuReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuReturnButtonActionPerformed
        requestSwitchDispenserState(DispenserState.MAIN_MENU);
    }//GEN-LAST:event_mainMenuReturnButtonActionPerformed

    private void barbakanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barbakanButtonActionPerformed
        requestSwitchDispenserState(DispenserState.BARBAKAN);
    }//GEN-LAST:event_barbakanButtonActionPerformed

    private void backToWorthToSeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButtonActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButtonActionPerformed

    private void kopiecKrakusaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kopiecKrakusaButtonActionPerformed
        requestSwitchDispenserState(DispenserState.KOPIEC_KRAKUSA);
    }//GEN-LAST:event_kopiecKrakusaButtonActionPerformed

    private void kosciolMariackiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kosciolMariackiButtonActionPerformed
        requestSwitchDispenserState(DispenserState.KOSCIOL_MARIACKI);
    }//GEN-LAST:event_kosciolMariackiButtonActionPerformed

    private void rynekGlownyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rynekGlownyButtonActionPerformed
        requestSwitchDispenserState(DispenserState.RYNEK_GLOWNY);
    }//GEN-LAST:event_rynekGlownyButtonActionPerformed

    private void sukienniceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sukienniceButtonActionPerformed
        requestSwitchDispenserState(DispenserState.SUKIENNICE);
    }//GEN-LAST:event_sukienniceButtonActionPerformed

    private void wawelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wawelButtonActionPerformed
        requestSwitchDispenserState(DispenserState.WAWEL);
    }//GEN-LAST:event_wawelButtonActionPerformed

    private void backToWorthToSeeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButton1ActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButton1ActionPerformed

    private void backToWorthToSeeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButton2ActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButton2ActionPerformed

    private void backToWorthToSeeButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButton3ActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButton3ActionPerformed

    private void backToWorthToSeeButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButton4ActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButton4ActionPerformed

    private void backToWorthToSeeButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToWorthToSeeButton5ActionPerformed
        requestSwitchDispenserState(DispenserState.TOURIST_INFO);
    }//GEN-LAST:event_backToWorthToSeeButton5ActionPerformed

    @Override
    public void showView() {
        this.setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAnotherTicketButton;
    private javax.swing.JLabel amountLeftLabel;
    private javax.swing.JLabel amountLeftValue;
    private javax.swing.JLabel amountPriceLabel;
    private javax.swing.JLabel amountPriceValue;
    private javax.swing.JButton backToWorthToSeeButton;
    private javax.swing.JButton backToWorthToSeeButton1;
    private javax.swing.JButton backToWorthToSeeButton2;
    private javax.swing.JButton backToWorthToSeeButton3;
    private javax.swing.JButton backToWorthToSeeButton4;
    private javax.swing.JButton backToWorthToSeeButton5;
    private javax.swing.JButton barbakanButton;
    private javax.swing.JPanel barbakanCard;
    private javax.swing.JTextArea barbakanDescriptionTextArea;
    private javax.swing.JLabel barbakanImgLabel;
    private javax.swing.JPanel bottomMainPanel;
    private javax.swing.JButton buyATicketButton;
    private javax.swing.JButton cancelPaymentButton;
    private javax.swing.JButton cancelSelection;
    private javax.swing.JLabel changeAmountLabel;
    private javax.swing.JLabel changeAmountValue;
    private javax.swing.JPanel chooseTicket;
    private javax.swing.JTextArea chosenTicketsConsoleOutput;
    private javax.swing.JToggleButton englishLanguage;
    private javax.swing.JButton goToMainMenuButton;
    private javax.swing.JButton goToPayForTicketsCard;
    private javax.swing.JButton goToPrintTicketsButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel kopiecKrakaCard;
    private javax.swing.JLabel kopiecKrakaImgLabel;
    private javax.swing.JButton kopiecKrakusaButton;
    private javax.swing.JTextArea kopiecKrakusaDescriptionTextArea;
    private javax.swing.JButton kosciolMariackiButton;
    private javax.swing.JPanel kosciolMariackiCard;
    private javax.swing.JTextArea kosciolMariackiDescriptionTextArea;
    private javax.swing.JLabel kosciolMariackiImgLabel;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JButton mainMenuReturnButton;
    private javax.swing.JLabel normalTicketsLabel;
    private javax.swing.JPanel normalTicketsPanel;
    private javax.swing.JPanel payForTickets;
    private javax.swing.JToggleButton polishLanguage;
    private javax.swing.JButton rynekGlownyButton;
    private javax.swing.JPanel rynekGlownyCard;
    private javax.swing.JTextArea rynekGlownyDescriptionTextArea;
    private javax.swing.JLabel rynekGlownyImgLabel;
    private javax.swing.JPanel selectedTicketsCard;
    private javax.swing.JLabel studentTicketsLabel;
    private javax.swing.JPanel studentTicketsPanel;
    private javax.swing.JButton sukienniceButton;
    private javax.swing.JPanel sukienniceCard;
    private javax.swing.JTextArea sukienniceDescriptionTextArea;
    private javax.swing.JLabel sukienniceImgLabel;
    private javax.swing.JPanel summaryCard;
    private javax.swing.JLabel thanksLabel;
    private javax.swing.JPanel topMainPanel;
    private javax.swing.JLabel topMainPanelLabel;
    private javax.swing.JLabel totalAmountLabel;
    private javax.swing.JLabel totalAmountValue;
    private javax.swing.JButton touristInfoButton;
    private javax.swing.JPanel touristInfoCard;
    private javax.swing.JLabel userBankLabel;
    private javax.swing.JLabel userBankValue;
    private javax.swing.JButton wawelButton;
    private javax.swing.JPanel wawelCard;
    private javax.swing.JTextArea wawelDescriptionTextArea;
    private javax.swing.JLabel wawelImgLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void showTicketChoice() {
        showMainPanelCard("chooseTicketCard");

    }

    private void showMainPanelCard(String cardName) {
        CardLayout layout = (CardLayout) bottomMainPanel.getLayout();
        layout.show(bottomMainPanel, cardName);
        translateTopMainLabel(getTranslationsForCurrentLanguage());

    }

    private void initTicketSelections() {
        initTicketsPanel(studentTicketsPanel, controller.getStudentTickets());
        initTicketsPanel(normalTicketsPanel, controller.getNormalTickets());
    }

    private void initTicketsPanel(JPanel panel, List<Ticket> tickets) {
        GridLayout layout = (GridLayout) panel.getLayout();
        layout.setColumns(1);
        layout.setRows(1 + tickets.size());
        for (Ticket ticket : tickets) {
            JButton button = new JButton();
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    controller.selectTicket(ticket);
                }
            });
            panel.add(button);
            ticketButtons.put(ticket, button);
        }
    }

    @Override
    public void showMainMenu() {
        showMainPanelCard("mainMenuCard");
    }

    @Override
    public void showTicketPayment() {
        showMainPanelCard("payForTicketsCard");

    }

    @Override
    public void updatePaymentAmounts(Long totalAmount, Long amountLeft, Long userBank, Long userRest) {
        totalAmountValue.setText(FormatUtils.formatMonetaryValue(totalAmount));
        amountLeftValue.setText(FormatUtils.formatMonetaryValue(amountLeft));
        userBankValue.setText(FormatUtils.formatMonetaryValue(userBank));
        changeAmountValue.setText(FormatUtils.formatMonetaryValue(userRest));
    }

    public String toString(List<Ticket> chosenTickets, Properties translations) {
        String fullList = "";
        getTranslationsForCurrentLanguage();
        for (Ticket ticket : chosenTickets) {
            String text = translations.getProperty("ticket." + ticket.toString() + ".label");
            String line = text + "     " + FormatUtils.formatMonetaryValue(ticket.getMonetaryValue());
            fullList = fullList + System.lineSeparator() + line;
        }
        return fullList;
    }

    @Override
    public void showSelectedTicketCard(Long totalAmount, List<Ticket> chosenTickets) {
        showMainPanelCard("selectedTicketsCard");
        chosenTicketsConsoleOutput.setText(toString(chosenTickets, getTranslationsForCurrentLanguage()));
        amountPriceValue.setText(FormatUtils.formatMonetaryValue(totalAmount));
    }

    @Override
    public void showTicketSummary() {
        showMainPanelCard("summaryCard");
    }

    @Override
    public void setLockUnlockPrint(boolean isEnabled) {
        goToPrintTicketsButton.setEnabled(isEnabled);
    }

    @Override
    public void showTouristInfo() {
        showMainPanelCard("touristInfoCard");
    }

    @Override
    public void showBarbakanInfo() {
        showMainPanelCard("barbakanCard");
    }

    @Override
    public void showKopiecKrakusaInfo() {
        showMainPanelCard("kopiecKrakusaCard");
    }

    @Override
    public void showKosciolMariackiInfo() {
        showMainPanelCard("kosciolMariackiCard");
    }

    @Override
    public void showRynekGlownyInfo() {
        showMainPanelCard("rynekGlownyCard");
    }

    @Override
    public void showSukienniceInfo() {
        showMainPanelCard("sukienniceCard");
    }

    @Override
    public void showWawelInfo() {
        showMainPanelCard("wawelCard");
    }

}
