package com.avaloq.dice;

import static org.assertj.core.api.Assertions.assertThat;

import com.avaloq.dice.controller.model.SimulationStats;
import com.avaloq.dice.database.entity.Simulation;
import com.avaloq.dice.database.entity.SimulationResult;
import com.avaloq.dice.database.repository.SimulationRepository;
import com.avaloq.dice.database.repository.StatsRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class StatsRepositoryTest {

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Before
    public void initDb() {
        Simulation simulation1 = new Simulation()
            .setDiceNumber(3L)
            .setDiceSides(6L)
            .setRollsNumber(10L);
        simulation1.setSimulationResults(
            Set.of(
                new SimulationResult()
                    .setSimulation(simulation1)
                    .setRollSum(7L)
                    .setRollsCount(5L),
                new SimulationResult()
                    .setSimulation(simulation1)
                    .setRollSum(10L)
                    .setRollsCount(3L),
                new SimulationResult()
                    .setSimulation(simulation1)
                    .setRollSum(18L)
                    .setRollsCount(2L)
            )
        );

        Simulation simulation2 = new Simulation()
            .setDiceNumber(3L)
            .setDiceSides(6L)
            .setRollsNumber(20L);
        simulation2.setSimulationResults(
            Set.of(
                new SimulationResult()
                    .setSimulation(simulation2)
                    .setRollSum(7L)
                    .setRollsCount(15L),
                new SimulationResult()
                    .setSimulation(simulation2)
                    .setRollSum(8L)
                    .setRollsCount(1L),
                new SimulationResult()
                    .setSimulation(simulation2)
                    .setRollSum(9L)
                    .setRollsCount(4L)
            )
        );

        Simulation simulation3 = new Simulation()
            .setDiceNumber(2L)
            .setDiceSides(4L)
            .setRollsNumber(5L);
        simulation3.setSimulationResults(
            Set.of(
                new SimulationResult()
                    .setSimulation(simulation3)
                    .setRollSum(2L)
                    .setRollsCount(2L),
                new SimulationResult()
                    .setSimulation(simulation3)
                    .setRollSum(3L)
                    .setRollsCount(2L),
                new SimulationResult()
                    .setSimulation(simulation3)
                    .setRollSum(5L)
                    .setRollsCount(1L)
            )
        );

        simulationRepository.save(simulation1);
        simulationRepository.save(simulation2);
        simulationRepository.save(simulation3);
    }

    @Test
    public void testSimulationStats() {
        List<SimulationStats> stats = statsRepository.getSimulationStats();

        assertThat(stats).isNotNull();
        assertThat(stats).hasSize(2);

        SimulationStats simulationStats1 = stats.get(0);
        assertThat(simulationStats1.getDiceNumber()).isEqualTo(2);
        assertThat(simulationStats1.getDiceSides()).isEqualTo(4);
        assertThat(simulationStats1.getSimulationsNumber()).isEqualTo(1);
        assertThat(simulationStats1.getRollsNumber()).isEqualTo(5);
        assertThat(simulationStats1.getRollSumDistribution()).hasSize(3);
        assertThat(simulationStats1.getRollSumDistribution().get(0).getRollSum()).isEqualTo(2);
        assertThat(simulationStats1.getRollSumDistribution().get(0).getPercentage())
            .isEqualTo(BigDecimal.valueOf(4000, 2));
        assertThat(simulationStats1.getRollSumDistribution().get(1).getRollSum()).isEqualTo(3);
        assertThat(simulationStats1.getRollSumDistribution().get(1).getPercentage())
            .isEqualTo(BigDecimal.valueOf(4000, 2));
        assertThat(simulationStats1.getRollSumDistribution().get(2).getRollSum()).isEqualTo(5);
        assertThat(simulationStats1.getRollSumDistribution().get(2).getPercentage())
            .isEqualTo(BigDecimal.valueOf(2000, 2));

        SimulationStats simulationStats2 = stats.get(1);
        assertThat(simulationStats2.getDiceNumber()).isEqualTo(3);
        assertThat(simulationStats2.getDiceSides()).isEqualTo(6);
        assertThat(simulationStats2.getSimulationsNumber()).isEqualTo(2);
        assertThat(simulationStats2.getRollsNumber()).isEqualTo(30);
        assertThat(simulationStats2.getRollSumDistribution()).hasSize(5);
        assertThat(simulationStats2.getRollSumDistribution().get(0).getRollSum()).isEqualTo(7);
        assertThat(simulationStats2.getRollSumDistribution().get(0).getPercentage())
            .isEqualTo(BigDecimal.valueOf(6667, 2));
        assertThat(simulationStats2.getRollSumDistribution().get(1).getRollSum()).isEqualTo(8);
        assertThat(simulationStats2.getRollSumDistribution().get(1).getPercentage())
            .isEqualTo(BigDecimal.valueOf(333, 2));
        assertThat(simulationStats2.getRollSumDistribution().get(2).getRollSum()).isEqualTo(9);
        assertThat(simulationStats2.getRollSumDistribution().get(2).getPercentage())
            .isEqualTo(BigDecimal.valueOf(1333, 2));
        assertThat(simulationStats2.getRollSumDistribution().get(3).getRollSum()).isEqualTo(10);
        assertThat(simulationStats2.getRollSumDistribution().get(3).getPercentage())
            .isEqualTo(BigDecimal.valueOf(1000, 2));
        assertThat(simulationStats2.getRollSumDistribution().get(4).getRollSum()).isEqualTo(18);
        assertThat(simulationStats2.getRollSumDistribution().get(4).getPercentage())
            .isEqualTo(BigDecimal.valueOf(667, 2));
    }
}
