package com.avaloq.dice.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "SIMULATION_RESULT")
@Getter
@Setter
@Accessors(chain = true)
public class SimulationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIMULATION_RESULT_ID", unique = true, nullable = false)
    private Long simulationResultId;

    @ManyToOne
    @JoinColumn(name = "FK_SIMULATION_ID", nullable = false)
    private Simulation simulation;

    @Column(name = "ROLL_SUM", nullable = false)
    private Long rollSum;

    @Column(name = "ROLLS_COUNT", nullable = false)
    private Long rollsCount;
}
