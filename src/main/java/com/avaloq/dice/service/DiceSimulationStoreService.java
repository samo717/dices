package com.avaloq.dice.service;

import com.avaloq.dice.database.entity.Simulation;
import com.avaloq.dice.database.entity.SimulationResult;
import com.avaloq.dice.database.repository.SimulationRepository;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiceSimulationStoreService {

    private final SimulationRepository simulationRepository;

    void storeSimulation(
        final long diceNumber,
        final long diceSides,
        final long rollsNumber,
        final Map<Long, Long> results
    ) {
        Simulation simulation = new Simulation();
        simulation.setDiceNumber(diceNumber);
        simulation.setDiceSides(diceSides);
        simulation.setRollsNumber(rollsNumber);

        Set<SimulationResult> simulationResults = results
            .entrySet()
            .stream()
            .map(resultEntry -> {
                SimulationResult simulationResult = new SimulationResult();
                simulationResult.setRollSum(resultEntry.getKey());
                simulationResult.setRollsCount(resultEntry.getValue());
                simulationResult.setSimulation(simulation);
                return simulationResult;
            })
            .collect(Collectors.toSet());

        simulation.setSimulationResults(simulationResults);

        simulationRepository.save(simulation);
    }
}
