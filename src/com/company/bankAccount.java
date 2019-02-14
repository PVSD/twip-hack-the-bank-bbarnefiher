package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by dpennebacker on 2/13/17.
 */
public class bankAccount implements Comparable{
    public bankAccount(String nm, double amt) {
        name = nm;
        balance = amt;
    }

    public static String getTime(){
        LocalDateTime ldt = LocalDateTime.now();
        return ldt.toString().substring(11,19);
    }
    public int compareTo(Object otherObject) {
        bankAccount otherAccount = (bankAccount) otherObject;
        int retValue;
        if (balance < otherAccount.balance) {
            retValue = -1;
        } else {
            if (balance > otherAccount.balance) {
                retValue = 1;
            } else {
                retValue = 0;
            }
        }
        return retValue;
    }

    public void deposit(double dp) {

        balance = balance + dp;
    }



    public void withdraw(double wd) {
        balance = balance - wd;
    }

    public String name;
    public double balance;

}
