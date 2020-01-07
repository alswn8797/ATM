/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author 5696
 */
public class numberButtonActionListener implements ActionListener{

    public int number; //the number on the button
    public AtmUI ui;

    public numberButtonActionListener(int number, AtmUI ui) {
        this.number = number;
        this.ui = ui;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(ui.process=="Withdraw" || ui.process=="Deposit" || ui.process == "Login_Acn" || ui.process == "Login_Pwd" || ui.process == "Choose_acn"){
            ui.inputNum += number; //adds number to string
            ui.updateGUI();
        }
    }
}
