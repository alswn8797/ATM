/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author 5696
 */
public abstract class Account {
    //attributes for account
    public String accountName;
    public String clientName;
    public Date openDate;
    private double balance;
    private float interest;
    private double dailyLimit;
    public double usedDailyLimit;
    public double duration;
    public Date issueDate;
    public Date dueDate;
    public Date currentDate = new Date();

    //methods
    public abstract String deposit(double amount, Date _issueDate);
    public abstract String withdrawal(double amount, Date _issueDate);
    public abstract void checkBalance();
    public abstract String displayBalance();
    public abstract String checkStatement();
    public abstract void calcInterest();

    //Setters and Getters for private attributes
    public void setBalance(double _balance){
        balance = _balance;
    }
    public double getBalance(){
        return balance;
    }
    
    public void setInterest(float _interest){
        interest = _interest;
    }
    public float getInterest(){
        return interest;
    }
    
    public void setDailyLimit(double _dailyLimit){
        dailyLimit = _dailyLimit;
    }
    public double getDailyLimit(){
        return dailyLimit;
    }
    
    public Date setDate(String strDate){
        //Declaring new SimpleDateFormat ft "31-01-2016"
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        //Declaring variation Date t
        Date t;
        
        //try to replace String type parameter to Date type return
        //for parse try-catch needed, try to parse string to date
        //if it could not be processed, return new Date;
        try {
            t = ft.parse(strDate);
            return t;
        } catch (ParseException ex) {
            return new Date();
        }
    }
    
    public boolean checkDeposit(double amount){
        //If amount is less than or equal to 0
        //print Error message to user and return false;
        if(amount<=0){
            System.out.println("Requested amount: "+amount);
            System.out.println("Deposit amount can not be less than or equal to zero");
            return false;
        }
        //amount is greater than 0
        else {
            return true;
        }
    }
    
    //Implements Exception for overdrawing, negativeAmount, overDailyLimit
    public void checkWithdrawal(double amount) throws OverdrawingException, NegativeAmountException, DailyLimitException{
        
        if(dailyLimit>0 && usedDailyLimit+amount > dailyLimit){
            throw new DailyLimitException(dailyLimit,usedDailyLimit,amount);
        }
        //if balance is less than amount
        else if (balance<amount){
            throw new OverdrawingException(balance, amount);
        }
        //if requested amount is less than or equal to 0
        else if(amount<=0){
            throw new NegativeAmountException(amount,"Withdraw");
        }
    }

    public boolean checkWithdrawalAmount(double amount){
        boolean b = false;
        int intAmount = (int)amount;
        //When user put any number to last digit eg.103, 237 .. or even rational numbers
        //any coins or 5 notes are not unable to withdraw
        if(amount%10!=0){
            b = false;
        }
        else {
            //loop int i contain Amount/100 which how many hundred notes needs to paid
            //used loop for example withdrawal amount is $430
            //i has 4 but remainder is 30 which can not be paid with $50 and $20 notes.
            //so number of payable $100 notes has to be -1 which is 3.
            //now remainder is $130 but still need other loop for handling remainder with $50 and $20 notes.
            //130/50 is 2 but remainder will be 30 which can not be handled with $20 notes.
            //now payable $50 notes has to be 1 and make remainder 80.
            //80 can be handled with $20 notes.

            //$100*3, $50*1, $20*4

            if(intAmount%100 == 0){
                b=true;
            }else{
                //give break point just a line before the loop that I want it to get out of
                breakLoop:
                for(int i=intAmount/100; i>=0; i--){
                    int remainder100 = intAmount - (100*i);
                    if(remainder100%50==0){
                        b=true;
                    }else{
                        for(int j = remainder100/50; j>=0; j--){
                            int remainder50 = remainder100 - (50*j);
                            if(remainder50%20 == 0){
                                b=true;
                                //if use only break, it only gets out of current loop where that's in
                                //use break point to make it get out of loop that I want.
                                break breakLoop;
                            } else {
                                b=false;
                            }
                        }
                    }
                }
            }
        }
        return b;
    }
}
