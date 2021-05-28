package com.avaloq.dice.controller.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimulationStats {

    private long diceNumber;
    private long diceSides;

    private long simulationsNumber;
    private long rollsNumber;

    private List<RollSumDistribution> rollSumDistribution = new ArrayList<>();
}
