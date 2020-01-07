/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.util.ArrayList;

/**
 *
 * @author 5696
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try{
            AtmUI atm = new AtmUI();
            atm.setupUI();
            
            atm.infoPanel.setText(atm.fixedStr);
            atm.login();

        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
