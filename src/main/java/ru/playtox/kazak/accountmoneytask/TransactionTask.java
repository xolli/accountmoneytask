package ru.playtox.kazak.accountmoneytask;

public class TransactionTask {
    private final int receiverIndex;
    private final int senderIndex;

    public TransactionTask(int receiverIndex, int senderIndex) {
        this.receiverIndex = receiverIndex;
        this.senderIndex = senderIndex;
    }

    public int getReceiverIndex() {
        return receiverIndex;
    }

    public int getSenderIndex() {
        return senderIndex;
    }
}
