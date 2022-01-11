package ru.playtox.kazak.accountmoneytask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


import static java.util.UUID.randomUUID;

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);
    private static final int COUNT_ACCOUNTS = 4;
    private static final int COUNT_THREADS = 2;
    private static final int COUNT_MONEY = 10000;
    private static final int COUNT_TRANSACTION = 30;

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>(COUNT_ACCOUNTS);
        for (int i = 0; i < COUNT_ACCOUNTS; ++i) {
            accounts.add(new Account(randomUUID().toString(), COUNT_MONEY));
        }
        List<Thread> moneyTransferThreads = new ArrayList<>(COUNT_THREADS);
        Runnable moneyTransferWorker = new MoneyTransferWorker(accounts, initTasks());
        for (int i = 0; i < COUNT_THREADS; ++i) {
            moneyTransferThreads.add(new Thread(moneyTransferWorker));
        }
        for (int i = 0; i < COUNT_THREADS; ++i) {
            moneyTransferThreads.get(i).start();
        }
        for (int i = 0; i < COUNT_THREADS; ++i) {
            try {
                moneyTransferThreads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                moneyTransferThreads.get(i).interrupt();
            }
        }
    }

    private static TransactionTaskRepository initTasks() {
        TransactionTaskRepository repository = new TransactionTaskRepository();
        for (int i = 0; i < COUNT_TRANSACTION; ++i) {
            int senderIndex = Util.getRandomWithExclusion(0, COUNT_ACCOUNTS - 1);
            int receiverIndex = Util.getRandomWithExclusion(0, COUNT_ACCOUNTS - 1, senderIndex);
            repository.addTask(new TransactionTask(senderIndex, receiverIndex));
        }
        return repository;
    }
}
