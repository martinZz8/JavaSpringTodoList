package com.example.backend.service;


import com.example.backend.DTOO.ActionCounterDTOO;
import com.example.backend.DTOO.pack.ActionCounterDTOOPack;
import com.example.backend.model.ActionCounter;
import com.example.backend.repository.ActionCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionCounterService {
    @Autowired
    private ActionCounterRepository actionCounterRepository;

    @Autowired
    @Qualifier("entityToDTOConversionService")
    private ConversionService conversionService;

    public List<ActionCounterDTOO> getAllActionCounters() {
        List<ActionCounter> actionCounters = actionCounterRepository.findAll();

        return conversionService.actionCounterListToDTOO(actionCounters);
    }

    public Optional<ActionCounterDTOO> getActionCounterByActionName(String actionName) {
        Optional<ActionCounter> o_entity = actionCounterRepository.findByActionName(actionName);
        ActionCounterDTOO dto;

        if (o_entity.isPresent()) {
            dto = conversionService.actionCounterToDTOO(o_entity.get());

            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<ActionCounterDTOO> increaseActionCounter(String actionName) {
        Optional<ActionCounter> o_entity = actionCounterRepository.findByActionName(actionName);
        ActionCounterDTOO dto;

        if (o_entity.isPresent()) {
            ActionCounter ac = o_entity.get();
            ac.setCounter(ac.getCounter()+1);
            actionCounterRepository.save(ac);
            dto = conversionService.actionCounterToDTOO(ac);

            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<ActionCounterDTOOPack> decreaseActionCounter(String actionName) {
        Optional<ActionCounter> o_entity = actionCounterRepository.findByActionName(actionName);
        ActionCounterDTOO dto;

        if (o_entity.isPresent()) {
            ActionCounter ac = o_entity.get();

            if (ac.getCounter() > 0) {
                ac.setCounter(ac.getCounter()-1);
                actionCounterRepository.save(ac);
            }
            else {
                return Optional.of(new ActionCounterDTOOPack(null, "Counter already reached 0, can't decrease it"));
            }

            dto = conversionService.actionCounterToDTOO(ac);
            return Optional.of(new ActionCounterDTOOPack(dto, ""));
        }
        return Optional.empty();
    }
}
