package com.avaloq.dice.controller;

import com.avaloq.dice.controller.model.SimulationResponse;
import com.avaloq.dice.service.DiceService;
import java.util.Map;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class DiceController {

    private final DiceService diceService;

    @PostMapping(
        value = "/dice/run-simulation",
        produces = {"application/json"}
    )
    public ResponseEntity<SimulationResponse> runSimulation(
        @RequestParam(name = "diceNumber", required = false, defaultValue = "3") @Min(1) final long dicesNumber,
        @RequestParam(name = "diceSides", required = false, defaultValue = "6") @Min(4) final long diceSides,
        @RequestParam(name = "rollsNumber", required = false, defaultValue = "100") @Min(1) final long rollsNumber
    ) {
        Map<Long, Long> sumCounts = diceService.runAndStoreDiceSimulation(dicesNumber, diceSides, rollsNumber);
        return new ResponseEntity<>(
            new SimulationResponse()
                .setDiceNumber(dicesNumber)
                .setDiceSides(diceSides)
                .setRollsNumber(rollsNumber)
                .setSumCounts(sumCounts),
            HttpStatus.OK
        );
    }
}
