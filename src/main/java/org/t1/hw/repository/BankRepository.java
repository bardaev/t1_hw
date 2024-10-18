package org.t1.hw.repository;

import org.springframework.stereotype.Repository;
import org.t1.hw.aspect.BalanceOperationAspect;
import org.t1.hw.aspect.LogBalance;

import java.util.Random;

@Repository
public class BankRepository {

    private int balance = new Random().nextInt(100);

    @LogBalance
    public int getBalance() {
        return balance;
    }

    @BalanceOperationAspect
    public void spendMoney(int sum) {
        if (balance - sum < 0) {
            throw new RuntimeException("sum: " + sum + " greater than balance: " + balance);
        }
        balance = balance - sum;
    }

    @BalanceOperationAspect
    public void takeMoney(int sum) {
        balance = balance + sum;
    }

    public int notAspectGetBalance() {
        return balance;
    }
}
