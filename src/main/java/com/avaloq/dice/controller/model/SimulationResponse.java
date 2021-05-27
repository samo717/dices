package com.avaloq.dice.controller.model;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimulationResponse {

    private long diceNumber;
    private long diceSides;
    private long rollsNumber;

    private Map<Long, Long> sumCounts;
}
