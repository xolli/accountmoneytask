package ru.playtox.kazak.accountmoneytask;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    // https://stackoverflow.com/questions/6443176/how-can-i-generate-a-random-number-within-a-range-but-exclude-some
    public static int getRandomWithExclusion(int start, int end, int... exclude) {
        int random = start + ThreadLocalRandom.current().nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
}
