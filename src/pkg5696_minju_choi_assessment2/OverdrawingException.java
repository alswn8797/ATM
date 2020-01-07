/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

/**
 *
 * @author 5696
 */
public class OverdrawingException extends Exception {
    OverdrawingException(double balance, double amount){
        super("\nYour balance is not enough to process this tracsaction\nBalance: "+balance+", Withdraw Amount: "+amount);
    }
}
