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
public class User {
    public String name;
    private int accountNumber;
    private int password;
    ArrayList<SavingAccount> sAccounts = new ArrayList<SavingAccount>();
    ArrayList<ChequeAccount> cAccounts = new ArrayList<ChequeAccount>();
    ArrayList<FixedAccount> fAccounts = new ArrayList<FixedAccount>();
    ArrayList<NetSaverAccount> nAccounts = new ArrayList<NetSaverAccount>();
    
    User(){
        name = "";
        accountNumber = 0;
        password = 0;
    }
    
    User(String name, int accountNumber, int password){
        this.name = name;
        this.accountNumber = accountNumber;
        this.password = password;
    }
    
    public void setAccountNumber(int accountNumber){
       this.accountNumber = accountNumber;
    }
    
    public void setPassword (int password){
        this.password = password;
    }
    
    public int getAccountNumber(){
       return accountNumber;
    }
    
    public int getPassword (){
        return password;
    }
}
