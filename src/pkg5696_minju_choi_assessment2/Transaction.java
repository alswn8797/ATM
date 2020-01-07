/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.util.Date;

/**
 *
 * @author 5696
 */
public class Transaction {
    String type;
    double amount;
    Date issueDate;
    double interestAmount;
    
    Transaction(String type, double amount, Date issueDate){
        this.type = type;
        this.amount = amount;
        this.issueDate = issueDate;
    }
}
