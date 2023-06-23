package com.example.backend.controller;

import com.example.backend.DTO.ActionCounterDTOO;
import com.example.backend.enums.ActionCounterName;
import com.example.backend.service.ActionCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/action_counter")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActionCounterController {
    @Autowired
    private ActionCounterService actionCounterService;

    @GetMapping(path = "/all", produces = "application/json")
    public List<ActionCounterDTOO> getAllActionCounters() {
        return actionCounterService.getAllActionCounters();
    }

    @GetMapping(path = "/get/{actionName}", produces = "application/json")
    public ResponseEntity<ActionCounterDTOO> getActionCounterByActionName(@PathVariable String actionName) {
        Optional<ActionCounterDTOO> o_action_counter = actionCounterService.getActionCounterByActionName(actionName);

        if (o_action_counter.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o_action_counter.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/increase/{name}", produces = "application/json")
    public ResponseEntity<ActionCounterDTOO> increaseActionCounter(@PathVariable String name) {
        if (ActionCounterName.isProperActionName(name)) {
            Optional<ActionCounterDTOO> o_action_counter = actionCounterService.increaseActionCounter(name);

            if (o_action_counter.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(o_action_counter.get());
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PostMapping(path = "/decrease/{name}", produces = "application/json")
    public ResponseEntity<ActionCounterDTOO> decreaseActionCounter(@PathVariable String name) {
        if (ActionCounterName.isProperActionName(name)) {
            Optional<ActionCounterDTOO> o_action_counter = actionCounterService.decreaseActionCounter(name);

            if (o_action_counter.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(o_action_counter.get());
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
