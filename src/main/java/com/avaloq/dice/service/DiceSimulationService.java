package com.avaloq.dice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class DiceSimulationService {

    Map<Long, Long> runDiceSimulation(
        final long diceNumber,
        final long diceSides,
        final long rollsNumber
    ) {
        Map<Long, Long> sumCounts = new HashMap<>();

        for (long i = 0; i < rollsNumber; i++) {
            long rollSum = getOneDiceRollSum(diceNumber, diceSides);

            Long count = (sumCounts.containsKey(rollSum)) ? sumCounts.get(rollSum) : Long.valueOf(0);
            sumCounts.put(rollSum, ++count);
        }

        return sumCounts;
    }

    private long getOneDiceRollSum(final long dicesNumber, final long diceSides) {
        long sum = 0;
        for (long i = 0; i < dicesNumber; i++) {
            sum += getOneDiceRoll(diceSides);
        }

        return sum;
    }

    private long getOneDiceRoll(final long diceSides) {
        return ThreadLocalRandom.current().nextLong(1, diceSides + 1);
    }
}
