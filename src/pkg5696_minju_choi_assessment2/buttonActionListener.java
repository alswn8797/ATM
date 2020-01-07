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
public class buttonActionListener implements ActionListener{

    public String btn; //the text on the button
    public AtmUI ui;

    public buttonActionListener(String btn, AtmUI ui) {
        this.btn = btn;
        this.ui = ui;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(btn.equals("Clear")){
           ui.inputNum = "";
           ui.updateGUI();
        }
        else if(btn.equals("Dot")){
            int intIndex = ui.inputNum.indexOf(".");
            if(intIndex == - 1){
                if(ui.inputNum.isEmpty()){
                    ui.inputNum += "0.";
                } else {
                    ui.inputNum += ".";
                }
                ui.updateGUI();
            }
        }
        else if(btn.equals("Withdraw")){
            //make inputNum as the empty to avoid some number stored before pressing withdraw button
            ui.inputNum = "";
            //logged in and chose an account has to be done to use this button
            if(ui.account_function_YN){
                ui.process = "Withdraw";
                ui.fixedStr += "\nInput amount that you want to withdraw and press Enter Please.\n";
                ui.infoPanel.setText(ui.fixedStr);
            } else {
                ui.fixedStr += "To use Withdraw button you must be logged in and account has to be chosen.\n";
                ui.infoPanel.setText(ui.fixedStr);
            }
        }
        else if(btn.equals("Deposit")){
            //make inputNum as the empty to avoid some number stored before pressing withdraw button
            ui.inputNum = "";
            if(ui.account_function_YN){
                ui.process = "Deposit";
                ui.fixedStr += "\nInput amount that you want to deposit and press Enter Please.\n";
                ui.infoPanel.setText(ui.fixedStr);
            } else {
                ui.fixedStr += "To use Deposit button you must be logged in and account has to be chosen.\n";
                ui.infoPanel.setText(ui.fixedStr);
            }
        }
        else if(btn.equals("Balance")){
            if(ui.account_function_YN){
                ui.process = "Balance";
                ui.Balance();
            } else {
                ui.fixedStr += "To use Balance button you must be logged in and account has to be chosen.\n";
                ui.infoPanel.setText(ui.fixedStr);
            }
        }
        else if(btn.equals("Help")){
            if(ui.process!="Help"){
                ui.preProcess = ui.process;
            }
            ui.process = "Help";
            ui.Help();
        }
        else if(btn.equals("Cancel")){
            if(ui.process == "Help"){
                ui.process = ui.preProcess;
                ui.infoPanel.setText(ui.fixedStr);
                ui.inputNum = "";
            } else {
                ui.fixedStr = "";
                ui.currentUser = new User();
                ui.account_function_YN = false;
                ui.login();
            }
        }
    }
}
