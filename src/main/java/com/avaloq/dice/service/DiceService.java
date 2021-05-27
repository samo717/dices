package com.avaloq.dice.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiceService {

    private final DiceSimulationService diceSimulationService;

    public Map<Long, Long> runAndStoreDiceSimulation(
        final long diceNumber,
        final long diceSides,
        final long rollsNumber
    ) {
        return diceSimulationService.runDiceSimulation(diceNumber, diceSides, rollsNumber);
    }
}
