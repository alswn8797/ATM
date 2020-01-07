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
public class NegativeAmountException extends Exception {
    NegativeAmountException(double amount, String str){
        super("\n"+str+" amount can not be less than or equal to zero\nRequested Amount: A$"+amount);
    }
}
