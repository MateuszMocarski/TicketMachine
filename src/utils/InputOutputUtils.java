package utils;

import tickets.Coin;
import tickets.TicketsController;

public class InputOutputUtils extends javax.swing.JFrame {

    private final TicketsController controller;

    public InputOutputUtils(TicketsController controller) {
        this.controller = controller;
        initComponents();
    }

    public void appendString(String info) {
        consoleOutput.setText(consoleOutput.getText() + "\n" + info);
    }

    public void showUtils() {
        this.setVisible(true);
    }

    public void setLockUnlockPayment(boolean isEnabled) {

        tenCents.setEnabled(isEnabled);
        twentyCents.setEnabled(isEnabled);
        fiftyCents.setEnabled(isEnabled);
        onePLN.setEnabled(isEnabled);
        twoPLN.setEnabled(isEnabled);
        fivePLN.setEnabled(isEnabled);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tenCents = new javax.swing.JButton();
        twentyCents = new javax.swing.JButton();
        fiftyCents = new javax.swing.JButton();
        onePLN = new javax.swing.JButton();
        twoPLN = new javax.swing.JButton();
        fivePLN = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleOutput = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        tenCents.setText("10GR");
        tenCents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenCentsActionPerformed(evt);
            }
        });
        tenCents.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tenCentsPropertyChange(evt);
            }
        });
        jPanel1.add(tenCents);

        twentyCents.setText("20GR");
        twentyCents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twentyCentsActionPerformed(evt);
            }
        });
        jPanel1.add(twentyCents);

        fiftyCents.setText("50GR");
        fiftyCents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiftyCentsActionPerformed(evt);
            }
        });
        jPanel1.add(fiftyCents);

        onePLN.setText("1PLN");
        onePLN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onePLNActionPerformed(evt);
            }
        });
        jPanel1.add(onePLN);

        twoPLN.setText("2PLN");
        twoPLN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoPLNActionPerformed(evt);
            }
        });
        jPanel1.add(twoPLN);

        fivePLN.setText("5PLN");
        fivePLN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fivePLNActionPerformed(evt);
            }
        });
        jPanel1.add(fivePLN);

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        consoleOutput.setColumns(20);
        consoleOutput.setRows(5);
        jScrollPane1.setViewportView(consoleOutput);

        jPanel2.add(jScrollPane1);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fiftyCentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiftyCentsActionPerformed
        controller.addCoin(Coin.FIFTY_CENTS);
    }//GEN-LAST:event_fiftyCentsActionPerformed

    private void twentyCentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twentyCentsActionPerformed
        controller.addCoin(Coin.TWENTY_CENTS);
    }//GEN-LAST:event_twentyCentsActionPerformed

    private void onePLNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onePLNActionPerformed
        controller.addCoin(Coin.ONE_PLN);
    }//GEN-LAST:event_onePLNActionPerformed

    private void twoPLNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoPLNActionPerformed
        controller.addCoin(Coin.TWO_PLN);
    }//GEN-LAST:event_twoPLNActionPerformed

    private void fivePLNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fivePLNActionPerformed
        controller.addCoin(Coin.FIVE_PLN);
    }//GEN-LAST:event_fivePLNActionPerformed

    private void tenCentsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tenCentsPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tenCentsPropertyChange

    private void tenCentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenCentsActionPerformed
        controller.addCoin(Coin.TEN_CENTS);
    }//GEN-LAST:event_tenCentsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consoleOutput;
    private javax.swing.JButton fiftyCents;
    private javax.swing.JButton fivePLN;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton onePLN;
    private javax.swing.JButton tenCents;
    private javax.swing.JButton twentyCents;
    private javax.swing.JButton twoPLN;
    // End of variables declaration//GEN-END:variables
}
