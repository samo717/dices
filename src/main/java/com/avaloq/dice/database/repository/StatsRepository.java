package com.avaloq.dice.database.repository;

import com.avaloq.dice.controller.model.RollSumDistribution;
import com.avaloq.dice.controller.model.SimulationStats;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatsRepository {

    private final EntityManager entityManager;

    public List<SimulationStats> getSimulationStats() {
        List<SimulationStats> stats = new ArrayList<>();

        final String sqlQuery =
            "SELECT\n"
                + "    DICE_NUMBER_SIDES_TABLE.DICE_NUMBER,\n"
                + "    DICE_NUMBER_SIDES_TABLE.DICE_SIDES,\n"
                + "    DICE_NUMBER_SIDES_TABLE.ROLL_SUM,\n"
                + "    TOTAL_NUMBERS_TABLE.TOTAL_SIMULATION_NUMBER,\n"
                + "    TOTAL_NUMBERS_TABLE.TOTAL_ROLLS_NUMBER,\n"
                + "    (DICE_NUMBER_SIDES_TABLE.ALL_ROLLS_COUNT / TOTAL_NUMBERS_TABLE.TOTAL_ROLLS_NUMBER) * 100\n"
                + "FROM\n"
                + "    (\n"
                + "        SELECT\n"
                + "            SIMULATION.DICE_NUMBER as DICE_NUMBER,\n"
                + "            SIMULATION.DICE_SIDES as DICE_SIDES,\n"
                + "            SIMULATION_RESULT.ROLL_SUM as ROLL_SUM,\n"
                + "            sum(SIMULATION_RESULT.ROLLS_COUNT) as ALL_ROLLS_COUNT\n"
                + "        FROM\n"
                + "            SIMULATION_RESULT,\n"
                + "            SIMULATION\n"
                + "        WHERE\n"
                + "            SIMULATION.SIMULATION_ID = SIMULATION_RESULT.FK_SIMULATION_ID\n"
                + "        GROUP BY\n"
                + "            DICE_NUMBER, DICE_SIDES, ROLL_SUM\n"
                + "    ) DICE_NUMBER_SIDES_TABLE,\n"
                + "    (\n"
                + "        SELECT\n"
                + "            SIMULATION.DICE_NUMBER as DICE_NUMBER,\n"
                + "            SIMULATION.DICE_SIDES as DICE_SIDES,\n"
                + "            count(*) as TOTAL_SIMULATION_NUMBER,\n"
                + "            sum(SIMULATION.ROLLS_NUMBER) as TOTAL_ROLLS_NUMBER\n"
                + "        FROM\n"
                + "            SIMULATION\n"
                + "        GROUP BY\n"
                + "            DICE_NUMBER, DICE_SIDES\n"
                + "    ) TOTAL_NUMBERS_TABLE\n"
                + "WHERE\n"
                + "        DICE_NUMBER_SIDES_TABLE.DICE_SIDES = TOTAL_NUMBERS_TABLE.DICE_SIDES AND\n"
                + "        DICE_NUMBER_SIDES_TABLE.DICE_NUMBER = TOTAL_NUMBERS_TABLE.DICE_NUMBER;";

        Query q = entityManager.createNativeQuery(sqlQuery);
        List<Object[]> dbResultRows = q.getResultList();
        for (Object[] o : dbResultRows) {
            long diceNumber = ((BigInteger) o[0]).longValue();
            long diceSides = ((BigInteger) o[1]).longValue();
            long rollSum = ((BigInteger) o[2]).longValue();
            long simulationsNumber = ((BigInteger) o[3]).longValue();
            long rollsNumber = ((BigDecimal) o[4]).longValue();
            BigDecimal percentage = ((BigDecimal) o[5]).setScale(2, RoundingMode.HALF_UP);

            appendToResult(
                stats,
                diceNumber,
                diceSides,
                simulationsNumber,
                rollsNumber,
                rollSum,
                percentage
            );
        }

        return stats;
    }

    private void appendToResult(
        final List<SimulationStats> statsList,
        final long diceNumber,
        final long diceSides,
        final long simulationsNumber,
        final long rollsNumber,
        final long rollSum,
        final BigDecimal percentage
    ) {
        Optional<SimulationStats> statsOptional = statsList
            .stream()
            .filter(simulationStats -> simulationStats.getDiceNumber() == diceNumber)
            .filter(simulationStats -> simulationStats.getDiceSides() == diceSides)
            .findFirst();

        SimulationStats stats;
        if (statsOptional.isPresent()) {
            stats = statsOptional.get();
        } else {
            stats = new SimulationStats()
                .setDiceNumber(diceNumber)
                .setDiceSides(diceSides)
                .setSimulationsNumber(simulationsNumber)
                .setRollsNumber(rollsNumber);
            statsList.add(stats);
        }

        stats.getRollSumDistribution()
            .add(
                new RollSumDistribution()
                    .setRollSum(rollSum)
                    .setPercentage(percentage)
            );
    }
}
