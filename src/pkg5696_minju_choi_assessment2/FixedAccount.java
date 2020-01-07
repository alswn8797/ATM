/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 5696
 */
public class FixedAccount extends Account {

    
    //Declaring Array for transaction(diposit, withdrawal)
    ArrayList<Transaction> t = new ArrayList<Transaction>();
    
    //Constructor with no parameter
    FixedAccount(){
        //call other constructor which take amount and duration
        //without setting amount and duration call another constructor with
        //the default which is amount 0 and 3months contract period
        this(0,3,new Date());
    }
    
    //Constructor overwriting
    FixedAccount(double amount, double _duration){
        this(amount, _duration, new Date());
    }
    
    //Constructor overwriting
    FixedAccount(double amount, double _duration, String _openDate){
        //set account name
        accountName = "Fixed Account";
        //Decalre variable for temporal interest and set value as
        //return value from calcInterestRate function with duration
        double _interestRate = calcInterestRate(_duration);
        
        //calcInterestRate return interest value or -1 for unsettable contract period
        if(_interestRate==-1){
            //when return value is -1 print error message for user
            System.out.println("Fixed Account -\nContract period for Fixed Account must be\none of following (3, 6, 12, 24 and 36 months)");
        } else {
            //set interest with changing datatype of _interestRate double to float
            setInterest((float)_interestRate);
            //set openDate and issue date as parameter
            //setDate() does changing datatype from String("01-04-2016") to Date
            openDate = setDate(_openDate);
            issueDate = openDate;
            //set dueDate
            calcDueDate();
            //if open amount is negative. print error message for user
            if(amount<0){
                System.out.println("Account opening amount can not be less than 0");
            }
            //if open amount is 0 deposit function will not be called.
            else if (amount!=0) {
                //deposit amount as parameter value
                deposit(amount,issueDate);
            }
        }
    }
    
    //Constructor overwriting
    FixedAccount(double amount, double _duration, Date _openDate){
        accountName = "Fixed Account";
        double _interestRate = calcInterestRate(_duration);

        if(_interestRate==-1){
            System.out.println("Fixed Account -\nContract period for Fixed Account must be\none of following (3, 6, 12, 24 and 36 months)");
        } else {
            setInterest((float)_interestRate);
            openDate = _openDate;
            issueDate = openDate;
            calcDueDate();
            if(amount<0){
                System.out.println("Account opening amount can not be less than 0");
            }
            else if (amount!=0) {
                deposit(amount,issueDate);
            }
        }
    }
    
    @Override

    //Deposit function overwriting with only amount and date type issueDate
    public String deposit(double amount, Date _issueDate) {
        String str = "";
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

    @Override

    public String withdrawal(double amount, Date _issueDate) {
        String str = "";
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
                //Once user make withdrawal before duedate, set interestRate as 0%
                if (issueDate.before(dueDate)){
                    setInterest(0);
                }
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
                //For Fixed account we don't calculate interest for day or month the money has been,
                //we only calculate with fixed interest rate for contract period. so there is not - t.get(i).interestAmount for balance
                setBalance(getBalance()-t.get(i).amount);
            }
        }    
    }
    
    public String displayBalance() {
        String str = "";
        checkBalance();
        str += ("Current Balance: A$"+(float)getBalance());
        return str;
    }
    
    @Override
    public String checkStatement() {
        String str = "";
        //Declaring new SimpleDateFormat ft "31-01-2016"
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        
        str += ("Account Name: "+accountName)+"\n";
        //interest set as 0.024 if it is 2.4%p.a.
        //muliply 0.024 * 100 to show users right interest rate
        str += ("Interest Rate: "+getInterest()*100+"%p.a.\n");
        str += ("Open Date: "+ft.format(openDate)+"\n");
        str += ("Due Date: "+ft.format(dueDate)+"\n");
        str += ("Contract Period: "+(int)duration+" Months\n");
        str += displayBalance()+"\n";
        str += ("-----------------------------\n");
        
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
        //loop until length of trasantions
        for (int i=0; i<(t.size()); i++) {
            //set ith of transaction's interestAmount
            //base on deposit or withdrawal amount x interest
            t.get(i).interestAmount = t.get(i).amount * getInterest();
        }
    }
    
    //function to calculate interest rate which get contract period from 
    public double calcInterestRate(double _duration) {
        //Declare array for interests with contract period
        double[][] interestRates = {{3,0.0235},{6,0.024},{12,0.0245},{24,0.027},{36,0.029}};
        //Decalre variable for temporal interest to return
        double _interestRate = -1;
        
        //_duration must be 3,6,12,24 and 36months
        //otherwise return -1 to constructor to show error message to user
        if(_duration==3||_duration==6||_duration==12||_duration==24||_duration==36){
            //loop for check same duration in the array interestRates
            for(int i=0; i<interestRates.length; i++){
                //if _duration value is same as the value of interestRates[i][0] which is duration
                if(interestRates[i][0]==_duration){
                    //set duration as a parameter
                    duration = _duration;
                    //set _interestRate value same as the value of interestRates[i][1] which is interest
                    _interestRate = interestRates[i][1];
                }
            }
        }
        return _interestRate;
    }
    public void calcDueDate(){
        //declaring new Calendar
        Calendar cal = Calendar.getInstance();
        //set cal as account openDate
        cal.setTime(openDate);
        //add duration on openDate's month
        cal.add(Calendar.MONTH, (int)duration);
        
        //set dueDate as calendar value
        dueDate = cal.getTime();
    }
}
