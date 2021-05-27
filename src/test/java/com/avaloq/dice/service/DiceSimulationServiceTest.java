package com.avaloq.dice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.Test;

public class DiceSimulationServiceTest {

    private final DiceSimulationService diceSimulationService = new DiceSimulationService();

    @Test
    public void testDiceSimulation() {
        Map<Long, Long> simulationResults = diceSimulationService.runDiceSimulation(3, 6, 10);

        assertThat(simulationResults).isNotEmpty();
        long simulationSum = simulationResults.values().stream().mapToLong(Long::valueOf).sum();
        assertThat(simulationSum).isEqualTo(10);
    }
}
