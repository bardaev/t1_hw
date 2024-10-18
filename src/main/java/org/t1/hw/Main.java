package org.t1.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.t1.hw.repository.BankRepository;

@ComponentScan(basePackages = "org.t1.hw")
@EnableAspectJAutoProxy
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        BankRepository bankRepository = context.getBean(BankRepository.class);
        bankRepository.getBalance();
        bankRepository.takeMoney(10);
        bankRepository.spendMoney(10);
        try {
            bankRepository.spendMoney(bankRepository.notAspectGetBalance() + 1);
        } catch (RuntimeException e) {
        }
    }
}
