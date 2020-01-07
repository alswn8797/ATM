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
public class DailyLimitException extends Exception {
    DailyLimitException(double dailyLimit, double usedDailyLimit, double amount){
        super("\nRequested amount: A$"+amount+"\nRejected: Daily withdrawal limit(A$"+dailyLimit+") has been reached\nAvailable amount of your daily withdrawal limit is A$"+(dailyLimit-usedDailyLimit));
    }
}
