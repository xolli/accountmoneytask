package ru.playtox.kazak.accountmoneytask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.playtox.kazak.accountmoneytask.exceptions.NegativeMoney;

import java.util.List;

public class MoneyTransferWorker implements Runnable {
    private static final Logger logger = LogManager.getLogger(MoneyTransferWorker.class);
    private static final int MIN_SLEEP_TIME = 1000;
    private static final int MAX_SLEEP_TIME = 2000;

    private final List<Account> accounts;
    private final TransactionTaskRepository tasks;

    public MoneyTransferWorker(List<Account> accounts, TransactionTaskRepository tasks) {
        this.accounts = accounts;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!tasks.isEmpty()) {
            TransactionTask task = tasks.getTask();
            if (task == null) {
                break;
            }
            int senderIndex = task.getSenderIndex();
            int receiverIndex = task.getReceiverIndex();
            try {
                doTransaction(receiverIndex, senderIndex);
                Thread.sleep(Util.getRandomWithExclusion(MIN_SLEEP_TIME, MAX_SLEEP_TIME));
            } catch (NegativeMoney | InterruptedException ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    // Accounts lock from the lowest to the highest index to prevent deadlock
    private void doTransaction(int receiverIndex, int senderIndex) throws NegativeMoney {
        int minIndex = Integer.min(receiverIndex, senderIndex);
        int maxIndex = Integer.max(receiverIndex, senderIndex);
        synchronized (accounts.get(minIndex)) {
            synchronized (accounts.get(maxIndex)) {
                int senderSumBefore = accounts.get(senderIndex).getMoney();
                int receiverSumBefore = accounts.get(receiverIndex).getMoney();
                int transactionSum = Util.getRandomWithExclusion(0, accounts.get(senderIndex).getMoney());
                accounts.get(senderIndex).decreaseMoney(transactionSum);
                accounts.get(receiverIndex).increaseMoney(transactionSum);
                logger.info(new TransactionInfo(
                        accounts.get(senderIndex).getId(), accounts.get(receiverIndex).getId(),
                        senderSumBefore, receiverSumBefore,
                        accounts.get(senderIndex).getMoney(), accounts.get(receiverIndex).getMoney(),
                        transactionSum));
            }
        }
    }
}
