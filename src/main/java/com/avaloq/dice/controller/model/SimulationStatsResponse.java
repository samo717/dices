package com.avaloq.dice.controller.model;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimulationStatsResponse {

    private List<SimulationStats> stats;
}
