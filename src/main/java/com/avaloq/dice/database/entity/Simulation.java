package com.avaloq.dice.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "SIMULATION")
@Getter
@Setter
@Accessors(chain = true)
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIMULATION_ID", unique = true, nullable = false)
    private Long simulationId;

    @Column(name = "DICE_NUMBER", nullable = false)
    private Long diceNumber;

    @Column(name = "DICE_SIDES", nullable = false)
    private Long diceSides;

    @Column(name = "ROLLS_NUMBER", nullable = false)
    private Long rollsNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulation")
    private Set<SimulationResult> simulationResults = new HashSet<>(0);
}
