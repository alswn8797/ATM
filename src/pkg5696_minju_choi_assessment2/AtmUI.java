/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5696_minju_choi_assessment2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *
 * @author 5696
 */
public class AtmUI {
    JFrame frame;
    JTextArea infoPanel;
    ArrayList<User> Users = new ArrayList<User>();
    User currentUser = new User();
    ArrayList<Account> accounts = new ArrayList<Account>();
    Object objAccount;
    
    ArrayList<JButton> btn_num = new ArrayList<JButton>();
    int[] btn_num_val = {7,8,9,4,5,6,1,2,3,0};
    
    JButton btnDot, btnClear;
    JButton btnWithdraw, btnDeposit, btnBalance, btnHelp;
    JButton btnCancel, btnEnter;
    
    JPanel middlePanel, middlePanelLeft, middlePanelRight, bottomPanel;
    
    String fixedStr = "Welcome to AIT Bank::::";
    String inputNum = "";
    String process = "";
    String preProcess = "";
    
    boolean account_function_YN = false;

    public void setupUI(){
        frame = new JFrame("AIT Bank");
        frame.setSize(480,370);
        
        //Add stuff to the north of the screen
        infoPanel = new JTextArea(12,40);
        infoPanel.setLineWrap(true);
        infoPanel.setEditable(false);
        JScrollPane scroller = new JScrollPane(infoPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        frame.add(scroller, BorderLayout.NORTH);
        
        setupButtons();
        setupPanels();
        
        setupUser();
        
        frame.setVisible(true);
    }
    
    
    //all for setup panels as layout
    private void setupPanels() {
        middlePanel = new JPanel(new GridLayout(1,2)); //1row, 2columns
        bottomPanel = new JPanel(new GridLayout(1,2)); //1row, 2columns
        
        middlePanelLeft = new JPanel(new GridLayout(4,3)); //Middle panel Left Part has 4rows, 3columns
        middlePanelRight = new JPanel(new GridLayout(4,1)); //Middle panel Left Part has 4rows, 1column
        middlePanel.add(middlePanelLeft);
        middlePanel.add(middlePanelRight);
        
        for(int i=0; i<btn_num.size(); i++){
            middlePanelLeft.add(btn_num.get(i));
        }

        middlePanelLeft.add(btnDot);
        middlePanelLeft.add(btnClear);
        
        middlePanelRight.add(btnWithdraw);
        middlePanelRight.add(btnDeposit);
        middlePanelRight.add(btnBalance);
        middlePanelRight.add(btnHelp);
        
        bottomPanel.add(btnCancel);
        bottomPanel.add(btnEnter);

        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }    

    private void setupButtons() {
        for(int i=0; i<btn_num_val.length; i++){
            btn_num.add(new JButton("" + btn_num_val[i]));
        }
        for(int i=0; i<btn_num_val.length; i++){
            btn_num.get(i).addActionListener(new numberButtonActionListener(btn_num_val[i], this));
        }
    
        btnDot = new JButton(".");
        btnClear = new JButton("Clr");
        
        btnWithdraw = new JButton("Withdraw");
        btnDeposit = new JButton("Deposit");
        btnBalance = new JButton("Balance Inquiry");
        btnHelp = new JButton("Help");
    
        btnCancel = new JButton("Cancel");
        btnEnter = new JButton("Enter");
        
        btnDot.addActionListener(new buttonActionListener("Dot", this));
        btnClear.addActionListener(new buttonActionListener("Clear", this));
        
        btnWithdraw.addActionListener(new buttonActionListener("Withdraw", this));
        btnDeposit.addActionListener(new buttonActionListener("Deposit", this));
        btnBalance.addActionListener(new buttonActionListener("Balance", this));
        btnHelp.addActionListener(new buttonActionListener("Help", this));
        
        btnCancel.addActionListener(new buttonActionListener("Cancel", this));
        
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!inputNum.isEmpty()){
                    if(process.equals("Login_Acn")){
                        boolean b = false;
                        for(int i=0; i<Users.size(); i++){
                            String acn = ""+Users.get(i).getAccountNumber();
                            if(inputNum.equals(acn)){
                                b = true;
                                fixedStr+=inputNum;
                                inputNum = "";
                                currentUser = Users.get(i);
                                login_pwd();
                            }
                        }
                        if (b==false){
                            fixedStr+=inputNum;
                            inputNum = "";
                            fixedStr+="\nInvalid account number. check your account number again.\n";
                            infoPanel.setText(fixedStr);
                            login();
                        }
                    } else if(process.equals("Login_Pwd")){
                        String pwd = ""+currentUser.getPassword();
                        //It was just going through Login_Pwd after press Enter at Login_Acn
                        // so I just added one more condition that inputNum is not Empty.
                        // cuz I made inputNum as an empty after check account number.
                        if(inputNum.equals(pwd)){
                            inputNum = "";
                            fixedStr="Welcome.\n"+currentUser.name;
                            infoPanel.setText(fixedStr);
                            printAccount();
                        } else {
                            fixedStr+=inputNum;
                            inputNum = "";
                            fixedStr+="\nIncorrect password. check your password again.\n";
                            infoPanel.setText(fixedStr);
                            login_pwd();
                        }
                    } else if(process.equals("Choose_acn")){
                        //boolean b is for checking right account number
                        // if the number through all condition and possible declare currentAcn,
                        // boolean b will contain true;
                        // otherwise leave as false and print error message and ask user input right account number
                        boolean b = false;
                        int intIndex = inputNum.indexOf(".");
                        //inputNumber for choosing account can not have dot.
                        if(intIndex == - 1){
                            //User could see 1,2,3,... but actual array address is 0,1,2,... so minus 1 from user's input
                            int accountNumber = (Integer.parseInt(inputNum))-1;

                            //User input number less than account list shows and greater than 0
                            if(accountNumber >= 0 && accountNumber < accounts.size()){
                                //Loop until accounts.size(), i will be increase by 1
                                for(int i=0; i<accounts.size(); i++){
                                    //if user's input-1 is same as i
                                    if(i==accountNumber){
                                        //get right object from Account accounts array[i] and put in Object objAccount
                                        objAccount = accounts.get(accountNumber);
                                        //login finished, and chose an account then it is true
                                        //use this variation for checking withdraw, deposit or balance button
                                        account_function_YN = true;
                                        fixedStr+=inputNum;
                                        inputNum = "";
                                        process = "";
                                        b = true;
                                        Balance();
                                    }
                                }
                            }
                        }
                        //fail to select account
                        if(b == false){
                            fixedStr+=inputNum;
                            inputNum = "";
                            fixedStr+="\nPress right account number, Please.\n";
                            infoPanel.setText(fixedStr);
                            ChooseAccount();
                        }
                    //must be chosen an account and withdraw or deposit button will work.
                    } else if (account_function_YN && (process.equals("Withdraw") || process.equals("Deposit"))){
                        String str = "";
                        //if object class which user chose as an using account same as SavingAccount
                        if(objAccount instanceof SavingAccount){
                            //declare SavingAccount type variable currentAcn.
                            SavingAccount currentAcn = (SavingAccount)objAccount;
                            if(process.equals("Withdraw"))
                                str = currentAcn.withdrawal(Double.parseDouble(inputNum), new Date());
                            else 
                                str = currentAcn.deposit(Double.parseDouble(inputNum), new Date());
                        //if object class same as ChequeAccount
                        } else if(objAccount instanceof ChequeAccount){
                            //declare ChequeAccount type variable currentAcn.
                            ChequeAccount currentAcn = (ChequeAccount)objAccount;
                            if(process.equals("Withdraw"))
                                str = currentAcn.withdrawal(Double.parseDouble(inputNum), new Date());
                            else
                                str = currentAcn.deposit(Double.parseDouble(inputNum), new Date());
                        //if object class same as FixedAccount
                        } else if(objAccount instanceof FixedAccount){
                            //declare FixedAccount type variable currentAcn.
                            FixedAccount currentAcn = (FixedAccount)objAccount;
                            if(process.equals("Withdraw"))
                                str = currentAcn.withdrawal(Double.parseDouble(inputNum), new Date());
                            else
                                str = currentAcn.deposit(Double.parseDouble(inputNum), new Date());
                        //if object class same as NetSaverAccount
                        } else if(objAccount instanceof NetSaverAccount){
                            //declare NetSaverAccount type variable currentAcn.
                            NetSaverAccount currentAcn = (NetSaverAccount)objAccount;
                            if(process.equals("Withdraw"))
                                str = currentAcn.withdrawal(Double.parseDouble(inputNum), new Date());
                            else
                                str = currentAcn.deposit(Double.parseDouble(inputNum), new Date());
                        } else {
                            fixedStr+="\nUnexpected Error: Contact to bank please.\n";
                            infoPanel.setText(fixedStr);
                        }
                        fixedStr += str;
                        infoPanel.setText(fixedStr);
                        inputNum="";
                        process="";
                    }
                }
            }
        });
    }
    
    public void updateGUI(){
        String output = inputNum;
        infoPanel.setText(fixedStr+output);
    }

    public void setupUser(){
        Users.add(new User("Minju Choi",5696,1234));
        Users.get(0).sAccounts.add(new SavingAccount(5000,"01-01-2016"));
        Users.get(0).sAccounts.get(0).deposit(200, new Date());
        Users.get(0).sAccounts.get(0).deposit(500, new Date());
        Users.get(0).sAccounts.get(0).deposit(200, new Date());
        Users.get(0).sAccounts.get(0).deposit(200, new Date());
        Users.get(0).sAccounts.add(new SavingAccount(500));
        Users.get(0).cAccounts.add(new ChequeAccount(3000));
        Users.get(0).cAccounts.add(new ChequeAccount(800));
        Users.get(0).fAccounts.add(new FixedAccount(200,3));
        Users.get(0).fAccounts.add(new FixedAccount(400,12));
        Users.get(0).nAccounts.add(new NetSaverAccount(1000));
        Users.get(0).nAccounts.add(new NetSaverAccount(1500));
    }

    public void login(){
        process = "Login_Acn";
        fixedStr+="\nPress your account number and press Enter Please.\n";
        
        infoPanel.setText(fixedStr);
    }
    
    public void login_pwd(){
        process = "Login_Pwd";
        fixedStr+="\nPress your password and press Enter Please.\n";
        infoPanel.setText(fixedStr);
    }
    
    public void printAccount(){
        int accountNumber = 0;
        for(int i=0; i<currentUser.sAccounts.size(); i++){
            fixedStr += "\n"+(accountNumber+1)+". "+currentUser.sAccounts.get(i).accountName+" A$"+(float)currentUser.sAccounts.get(i).getBalance();
            accounts.add(currentUser.sAccounts.get(i));
            accountNumber++;
        }
        for(int i=0; i<currentUser.cAccounts.size(); i++){
            fixedStr += "\n"+(accountNumber+1)+". "+currentUser.cAccounts.get(i).accountName+" A$"+(float)currentUser.cAccounts.get(i).getBalance();
            accounts.add(currentUser.cAccounts.get(i));
            accountNumber++;
        }
        for(int i=0; i<currentUser.fAccounts.size(); i++){
            fixedStr += "\n"+(accountNumber+1)+". "+currentUser.fAccounts.get(i).accountName+" A$"+(float)currentUser.fAccounts.get(i).getBalance();
            accounts.add(currentUser.fAccounts.get(i));
            accountNumber++;
        }
        for(int i=0; i<currentUser.nAccounts.size(); i++){
            fixedStr += "\n"+(accountNumber+1)+". "+currentUser.nAccounts.get(i).accountName+" A$"+(float)currentUser.nAccounts.get(i).getBalance();
            accounts.add(currentUser.nAccounts.get(i));
            accountNumber++;
        }
        
        fixedStr+="\n\nChoose an account and press Enter Please.\n";
        infoPanel.setText(fixedStr);
        ChooseAccount();
    }
    public void ChooseAccount(){
        process = "Choose_acn";
    }
    public void Help(){
        infoPanel.setText("==========================Help==========================\n\n"
                            + "Please input your account number and password to login.\n"
                            + "once you logged in, choose an account you try to use\n\n"
                            + "*Withdraw : Press Withdrawal button and press amount then press enter.\n"
                            + "*Deposit : Press Deposit button and press amount then press enter\n"
                            + "*Balance Inquiry : Press Balance Inquiry button :-)\n\n"
                            + "*Logout : Press Cancel button to go back to main during process\n\n"
                            + "Press Cancel button to go back");
    }
    public void Balance(){
        if(objAccount instanceof SavingAccount){
            //declare SavingAccount type variable currentAcn.
            SavingAccount currentAcn = (SavingAccount)objAccount;
            fixedStr = currentAcn.checkStatement();
        } else if(objAccount instanceof ChequeAccount){
            //declare ChequeAccount type variable currentAcn.
            ChequeAccount currentAcn = (ChequeAccount)objAccount;
            fixedStr = currentAcn.checkStatement();
        //if object class same as FixedAccount
        } else if(objAccount instanceof FixedAccount){
            //declare FixedAccount type variable currentAcn.
            FixedAccount currentAcn = (FixedAccount)objAccount;
            fixedStr = currentAcn.checkStatement();
        //if object class same as NetSaverAccount
        } else if(objAccount instanceof NetSaverAccount){
            //declare NetSaverAccount type variable currentAcn.
            NetSaverAccount currentAcn = (NetSaverAccount)objAccount;
            fixedStr = currentAcn.checkStatement();
        }
        infoPanel.setText(fixedStr);
    }
}


