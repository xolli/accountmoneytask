package ru.playtox.kazak.accountmoneytask;

public class TransactionInfo {
    private final String senderId;
    private final String receiverId;
    private final int senderSumBefore;
    private final int receiverSumBefore;
    private final int senderSumAfter;
    private final int receiverSumAfter;
    private final int transactionSum;

    public TransactionInfo(String senderId, String receiverId, int senderSumBefore,
                           int receiverSumBefore, int senderSumAfter, int receiverSumAfter, int transactionSum) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderSumBefore = senderSumBefore;
        this.receiverSumBefore = receiverSumBefore;
        this.senderSumAfter = senderSumAfter;
        this.receiverSumAfter = receiverSumAfter;
        this.transactionSum = transactionSum;
    }

    @Override
    public String toString() {
        return String.format("Receive %d from %s (%d -> %d) to %s (%d -> %d)",
                transactionSum, senderId, senderSumBefore, senderSumAfter,
                receiverId, receiverSumBefore, receiverSumAfter);
    }
}
