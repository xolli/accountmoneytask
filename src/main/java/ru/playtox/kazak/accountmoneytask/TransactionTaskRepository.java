package ru.playtox.kazak.accountmoneytask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionTaskRepository {
    private final Lock lock = new ReentrantLock();
    private final List<TransactionTask> tasks;

    public TransactionTaskRepository() {
        tasks = new ArrayList<>();
    }

    public void addTask(TransactionTask task) {
        lock.lock();
        try {
            tasks.add(task);
        } finally {
            lock.unlock();
        }
    }

    public TransactionTask getTask() {
        lock.lock();
        try {
            if (tasks.size() == 0) {
                return null;
            }
            return tasks.remove(tasks.size() - 1);
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
