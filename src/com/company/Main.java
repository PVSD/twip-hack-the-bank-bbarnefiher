package com.company;

import java.io.*;
import java.util.*;
import java.text.*;
import java.time.*;
public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        File bankLog = new File("Bank Logs.txt");
        FileWriter fw = new FileWriter(bankLog);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("Start of log [" + bankAccount.getTime() + "].");

        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
        String name;
        ArrayList aryLst = new ArrayList();
        ArrayList<bankAccount> listOfAccounts = new ArrayList<>();
        ArrayList listOfAccountNames = new ArrayList();
        ArrayList<Double> allDeposits = new ArrayList<>();
        ListIterator iter = aryLst.listIterator();
        bankAccount theAccount = null;
        do {
            Scanner kbReader = new Scanner(System.in);

            System.out.print("Please enter the name to whom the account belongs. (\"Exit\" to abort.) (\"Deposit\" to deposit money to an existing account.) (\"Withdraw\" to withdraw money from an existing account.) (\"Debug\") to enter debug mode. ");
            name = kbReader.nextLine();

            if (!name.equalsIgnoreCase("EXIT")) {
                if (name.equalsIgnoreCase("Debug")){

                    while (true){
                        System.out.println("You are now in debug mode. (\"Exit\" to abort.) (\"Deposits\" to list all deposits made.) (\"Balances\" to list all account balances.) (\"Drain\") to drain.");
                        String debugNav = kbReader.nextLine();
                        if (debugNav.equalsIgnoreCase("Exit")){
                            break;
                        }

                        if (debugNav.equalsIgnoreCase("Balances")){
                            Collections.sort(listOfAccounts, new comp());
                            System.out.println("These are all of the account balances in order:");
                            for (int i = 0; i < listOfAccounts.size(); i++) {
                                System.out.println("Name:" + listOfAccounts.get(i).name + "Balance: " + listOfAccounts.get(i).balance);
                            }

                        }
                        if (debugNav.equalsIgnoreCase("Deposits")){
                            Collections.sort(allDeposits, new compButForDoubles());
                            System.out.println("These are all of the deposits made:");
                            for (int i = 0; i < allDeposits.size(); i++) {
                                System.out.println("Deposit " + i + ": " + allDeposits.get(i));
                            }
                        }
                        if (debugNav.equalsIgnoreCase("Drain")){
                            System.out.println("Enter your main bank account.");
                            String mainBankAccount = kbReader.nextLine();
                            System.out.println("Enter the victim bank account.");
                            String victimAccount = kbReader.nextLine();

                            int mainBankIndex = listOfAccountNames.indexOf(mainBankAccount);
                            int victimAccountIndex = listOfAccountNames.indexOf(victimAccount);
                            double victimBal = listOfAccounts.get(victimAccountIndex).balance;
                            listOfAccounts.get(mainBankIndex).deposit(victimBal);
                            listOfAccounts.get(victimAccountIndex).withdraw(victimBal);
                            System.out.println("Balances drained.");

                        }

                    }
                }
                if (name.equalsIgnoreCase("Deposit")){
                    System.out.println("Enter the name of the account in which you want to deposit to.");
                    name = kbReader.nextLine();
                    if (listOfAccountNames.contains(name)){
                        int bankIndex = listOfAccountNames.indexOf(name);
                        System.out.println("How much money would you like to deposit into bank account " + name + "?");
                        double userDepositAmount = kbReader.nextInt();
                        listOfAccounts.get(bankIndex).deposit(userDepositAmount);
                        System.out.println("Deposited $" + userDepositAmount + " to bank account " + name);
                        allDeposits.add(userDepositAmount);
                        pw.println("[" + bankAccount.getTime() + "]: User deposited $" + userDepositAmount + " to account " + name + ".");
                    }
                    else {
                        System.out.println("That is not an existing bank account name.");
                    }

                    continue;
                }

                if (name.equalsIgnoreCase("Withdraw")){
                    System.out.println("Enter the name of the account in which you want to withdraw from.");
                    name = kbReader.nextLine();
                    if (listOfAccountNames.contains(name)){
                        int bankIndex = listOfAccountNames.indexOf(name);
                        System.out.println("How much money would you like to withdraw from bank account " + name + "?");
                        double userDepositAmount = kbReader.nextInt();
                        listOfAccounts.get(bankIndex).withdraw(userDepositAmount);
                        System.out.println("Withdrew $" + userDepositAmount + " to bank account " + name);
                        pw.println("[" + bankAccount.getTime() + "]: User withdrew $" + userDepositAmount + " from account " + name + ".");
                    }
                    else {
                        System.out.println("That is not an existing bank account name.");
                    }

                    continue;
                }

                System.out.print("Please enter the amount of the deposit.");
                double amount = kbReader.nextDouble();
                System.out.println(" "); // gives an eye pleasing blank line
                // between accounts
                theAccount = new bankAccount(name, amount);
                listOfAccounts.add(theAccount);
                listOfAccountNames.add(theAccount.name);
                iter.add(theAccount);
                pw.println("[" + bankAccount.getTime() + "]: New Bank Account created: Name = " + name + " / Balance = " + amount + ".");
            }


        } while (!name.equalsIgnoreCase("EXIT"));

        // Search aryLst and print out the name and amount of the largest bank
        // account
        bankAccount ba = (bankAccount) iter.previous();
        double maxBalance = ba.balance; // set last account as the winner so far
        String maxName = ba.name;
        while (iter.hasPrevious()) {
            ba = (bankAccount) iter.previous();
            if (ba.balance > maxBalance) {
                // We have a new winner, chicken dinner
                maxBalance = ba.balance;
                maxName = ba.name;
            }
        }
        System.out.println(" ");
        System.out.println("The account with the largest balance belongs to "
                + maxName + ".");
        System.out.println("The amount is $" + fmt.format(maxBalance) + ".");
        pw.close();
    }
    static class comp implements Comparator<bankAccount>
    {
        public int compare(bankAccount b1, bankAccount b2)
        {
            return (b1.balance < b2.balance) ? 1 : -1;
        }
    }
    static class compButForDoubles implements Comparator<Double>
    {
        public int compare(Double b1, Double b2)
        {
            return (b1 < b2) ? 1 : -1;
        }
    }
}
