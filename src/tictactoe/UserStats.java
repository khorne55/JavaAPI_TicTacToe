/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author puser
 */
public class UserStats extends javax.swing.JFrame {

    private int uid;
    private String username;

    /**
     * Creates new form UserStats
     */
    public UserStats() {
        uid = LoginWindow.getUser();
        username = LoginWindow.getUsername();
        initComponents();
        getStats();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameHeader = new javax.swing.JLabel();
        winsLabel = new javax.swing.JLabel();
        lossesLabel = new javax.swing.JLabel();
        drawsLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        noWins = new javax.swing.JLabel();
        noLosses = new javax.swing.JLabel();
        noDraws = new javax.swing.JLabel();
        gameMenuButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gameHeader.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        gameHeader.setText("Tic-Tac-Toe");

        winsLabel.setText("Your Wins");

        lossesLabel.setText("Your Losses");

        drawsLabel.setText("Your Draws");

        noWins.setText("0");

        noLosses.setText("0");

        noDraws.setText("0");

        gameMenuButton.setText("Game Menu");
        gameMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameMenuButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(gameHeader))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lossesLabel)
                            .addComponent(winsLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drawsLabel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noWins)
                            .addComponent(noDraws)
                            .addComponent(noLosses)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(gameMenuButton)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(gameHeader)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winsLabel)
                    .addComponent(jLabel5)
                    .addComponent(noWins))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lossesLabel)
                    .addComponent(noLosses))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawsLabel)
                    .addComponent(noDraws))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(gameMenuButton)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gameMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameMenuButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ListGames games = new ListGames();
        games.getFrame().setVisible(true);
    }//GEN-LAST:event_gameMenuButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserStats().setVisible(true);
            }
        });
    }

    private void getStats() {
        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();
        int wins = 0;
        int losses = 0;
        int draws = 0;
        String myGames = myLink.showAllMyGames(uid);
        if (!myGames.equals("ERROR-NOGAMES") || !myGames.equals("ERROR-DB")) {
            String[] lines = myGames.split("\n");
            for (String line : lines) {
                String[] result = line.split(",");
                try {
                    int winner = Integer.parseInt(myLink.checkWin(Integer.parseInt(result[0])));
                    if (winner == 3) {
                        draws++;
                    } else if (winner == 2 && result[2].equals(username)) {
                        wins++;
                    } else if (winner == 1 && result[1].equals(username)) {
                        wins++;
                    } else if (winner > 0) { // 0 is incomplete game
                        System.out.println("Game Lost");
                        losses++;
                    }
                } catch (Exception e) {
                    System.out.println("Error checking win of game:");
                }

            }
            noWins.setText(String.valueOf(wins));
            noDraws.setText(String.valueOf(draws));
            noLosses.setText(String.valueOf(losses));
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel drawsLabel;
    private javax.swing.JLabel gameHeader;
    private javax.swing.JButton gameMenuButton;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lossesLabel;
    private javax.swing.JLabel noDraws;
    private javax.swing.JLabel noLosses;
    private javax.swing.JLabel noWins;
    private javax.swing.JLabel winsLabel;
    // End of variables declaration//GEN-END:variables
}
