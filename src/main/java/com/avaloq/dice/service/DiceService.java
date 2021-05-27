package com.avaloq.dice.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiceService {

    private final DiceSimulationService diceSimulationService;
    private final DiceSimulationStoreService diceSimulationStoreService;

    public Map<Long, Long> runAndStoreDiceSimulation(
        final long diceNumber,
        final long diceSides,
        final long rollsNumber
    ) {
        Map<Long, Long> simulationResults = diceSimulationService.runDiceSimulation(diceNumber, diceSides, rollsNumber);
        diceSimulationStoreService.storeSimulation(diceNumber, diceSides, rollsNumber, simulationResults);

        return simulationResults;
    }
}
