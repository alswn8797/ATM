/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 5696
 */
public class ChequeAccount extends Account {
    //Declaring temporal interest to set interest one time and use in all constructors
    //Check acount does not offer any interest
    float interestRate = 0;
    //Declaring Array for transaction(diposit, withdrawal)
    ArrayList<Transaction> t = new ArrayList<Transaction>();
    
    //Constructor with no parameter
    ChequeAccount(){
        //call other constructor which take amount and openDate
        //call another constructor with the default which is amount 0, openDate now
        this(0,new Date());
    }
    
    //Constructor overwriting
    ChequeAccount(double amount){
        //call other constructor which take amount and openDate
        //call another constructor with recieved amount and the default openDate now
        this(amount,new Date());
    }
    
    //Constructor overwriting
    ChequeAccount(double amount, String _openDate){
        //set account name
        accountName = "Cheque Account";
        //set interest
        setInterest(interestRate);
        //set openDate and issue date as parameter
        //setDate() does changing datatype from String("01-04-2016") to Date
        openDate = setDate(_openDate);
        issueDate = openDate;
        //if open amount is negative. print error message for user
        if(amount<0){
            System.out.println("Account opening amount can not be less than 0");
        }
        //if open amount is 0 deposit function will not be called.
        else if (amount!=0) {
            //deposit amount as parameter value
            deposit(amount, issueDate);
        }
    }
    
    //Constructor overwriting
    ChequeAccount(double amount, Date _openDate){
        //set account name
        accountName = "Cheque Account";
        //set interest
        setInterest(interestRate);
        //set openDate and issue date as parameter
        //setDate() does changing datatype from String("01-04-2016") to Date
        openDate = _openDate;
        issueDate = openDate;
        //if open amount is negative. print error message for user
        if(amount<0){
            System.out.println("Account opening amount can not be less than 0");
        }
        //if open amount is 0 deposit function will not be called.
        else if (amount!=0) {
            //deposit amount as parameter value
            deposit(amount, issueDate);
        }
    }
    
    public String deposit(double amount, Date _issueDate) {
        String str="";
        //checkDeposit(), if amount is not less than or equal to zero return true
        if(checkDeposit(amount)){
            issueDate = _issueDate;
            //Adding transaction array
            t.add(new Transaction("Deposit",amount,issueDate));
            //Print transaction for user
            str += "A$"+amount+" Deposit Complete!\n";
            //calculate the balance
            checkBalance();
            str += "Current Balance is A$"+(float)getBalance()+"\n";
        }
        return str;
    }

    public String withdrawal(double amount, Date _issueDate) {
        String str="";
        //Try checkWithdrawal(amount); and catch exceptions that overdrawing, negativeAmount, dailyLimit
        // None of exceptions catched then process next code
        try{
            checkWithdrawal(amount);
            if(checkWithdrawalAmount(amount)){
                //setDate() does changing datatype from String("01-04-2016") to Date
                issueDate = _issueDate;
                //Adding transaction array
                t.add(new Transaction("Withdrawal",amount,issueDate));
                //Print transaction for user
                str += "A$"+amount+" Withdrawal Complete!\n";
                //calculate the balance
                checkBalance();
                str += "Current Balance is A$"+(float)getBalance()+"\n";
            } else {
                str += "Requested amount:"+amount+"\nWithdrawal amount only can be multiples of $20, $50 and $100\n";
            }
        } catch (DailyLimitException dlx){
            str += (dlx.toString());
        } catch (OverdrawingException oe){
            str += (oe.toString());
        } catch (NegativeAmountException nae){
            str += (nae.toString());
        }
        return str;
    }
    
    @Override
    public void checkBalance() {
        //calcInterest() will set interestAmount for each transaction
        calcInterest();
        //set balance as 0. To avoid whenever check the balance and deposit, withdrawal transactions will be done over and over on previous balance
        setBalance(0);
        
        //loop until length of trasantions
        for (int i=0; i<(t.size()); i++) {
            if(t.get(i).type=="Deposit"){
                //set balance = previous balance + trassaction's amount + interest amount
                setBalance(getBalance()+t.get(i).amount+t.get(i).interestAmount);
            } else if(t.get(i).type=="Withdrawal"){
                //set balance = previous balance - trassaction's amount - interest amount
                setBalance(getBalance()-t.get(i).amount-t.get(i).interestAmount);
            }
        }    
    }
    
    public String displayBalance(){
        String str="";
        checkBalance();
        str += "Current Balance: A$"+(float)getBalance();
        return str;
    }
    
    @Override
    public String checkStatement(){
        String str="";
        //Declaring new SimpleDateFormat ft "31-01-2016"
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        str += ("Account Name: "+accountName+"\n");
        //interest set as 0.024 if it is 2.4%p.a.
        //muliply 0.024 * 100 to show users right interest rate
        str += ("Interest Rate: "+interestRate*100+"%p.a."+"\n");
        str += ("Open Date: "+ft.format(openDate)+"\n");
        str += displayBalance();
        str += ("-----------------------------"+"\n");

        //loop until length of trasantions
        for (int i=0; i<(t.size()); i++) {
            //declaring temporal variable for issueDate
            //set value as ith transaction's issueDate with SimpleDateFormat ft
            String _issueDate = ft.format(t.get(i).issueDate);
            //print i+1 because i started from 0, it will show to user as 1,2,3... so on.
            //print ith transaction's type, amount and issuedate
            str += ((i+1)+" "+t.get(i).type+" A$"+t.get(i).amount+" "+_issueDate+"\n");
        }
        
        return str;
    }
    @Override
    public void calcInterest() {
        //Saving Account calulated daily
        for (int i=0; i<(t.size()); i++) {
            //set ith of transaction's interestAmount
            //base on deposit or withdrawal amount x interest
            t.get(i).interestAmount = t.get(i).amount * getInterest();
        }
    }
}
