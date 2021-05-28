package com.avaloq.dice.controller.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RollSumDistribution {

    private long rollSum;
    private BigDecimal percentage;
}
