package com.example.backend.service;

import com.example.backend.DTOI.TodoItemDTOI;
import com.example.backend.DTOO.DeleteTodoItemDTOO;
import com.example.backend.DTOO.TodoItemDTOO;
import com.example.backend.DTOI.TodoItemIsDoneDTOI;
import com.example.backend.DTOO.UpdateStatusTodoItemDTOO;
import com.example.backend.DTOO.pack.TodoItemDTOOPack;
import com.example.backend.enums.ActionCounterName;
import com.example.backend.enums.ERole;
import com.example.backend.model.TodoItem;
import com.example.backend.model.User;
import com.example.backend.repository.TodoItemRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("entityToDTOConversionService")
    private ConversionService conversionService;

    @Autowired
    private ActionCounterService actionCounterService;

    public List<TodoItemDTOO> getAllTodoItems() {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_ALL);

        // Get logged user details
        UserDetails ui = getLoggedUserDetails();

        // Get all todoItems, which userId belongs to logged user
        List<TodoItem> todoItems = todoItemRepository.findAllByUserName(ui.getUsername()); // old: findAll()

        List<TodoItemDTOO> ti = conversionService.todoItemListToDTOO(todoItems);

        return ti;
    }

    public List<TodoItemDTOO> getAllTodoItemsWithFiltering(String name, Boolean isDone, Boolean isAscending) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_WITH_FILTER);

        // Get logged user details
        UserDetails ui = getLoggedUserDetails();

        // Perform standard search
        List<TodoItem> allTodoItems = todoItemRepository.findAllByUserName(ui.getUsername()); // old: findAll()
        List<TodoItem> todoItemsToRet = new ArrayList<>(allTodoItems);

        // Search for name: case-insensitive, contains (optionally)
        if (name != null) {
            todoItemsToRet = todoItemsToRet.stream()
                            .filter(it -> it.getName().toLowerCase().contains(name.toLowerCase()))
                            .collect(Collectors.toList());
        }

        // Search for isDone (optionally)
        if (isDone != null) {
            Integer isDoneInt = isDone ? 1 : 0;
            todoItemsToRet = todoItemsToRet.stream()
                    .filter(it -> {
                        Integer isDoneIntFromDb = it.getIsDone() != null ? it.getIsDone() : 0;
                        return isDoneIntFromDb.equals(isDoneInt);
                    })
                    .collect(Collectors.toList());
        }

        // Order by createdOn date
        if (isAscending != null) {
            Collections.sort(
                    todoItemsToRet,
                    (i1, i2) -> {
                        Date d1 = i1.getCreatedOn();
                        Date d2 = i2.getCreatedOn();

                        return isAscending ? d1.compareTo(d2) : d2.compareTo(d1);
                    }
            );
        }

        // Transform filtered items to DTOO and return them
        return conversionService.todoItemListToDTOO(todoItemsToRet);
    }

    public Optional<TodoItemDTOOPack> getTodoItemById(Long id) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_ONE);

        // Find todoItem by id
        Optional<TodoItem> o_entity = todoItemRepository.findById(id);

        if (o_entity.isPresent()) {
            // Check if todoItem belongs to logged user
            // ** Add it to "if" statement, when you want to let admin have access to others items (or write new endpoint for this purpose): **
            // "|| doesLoggedUserHasAuthority(ERole.ROLE_ADMIN)"
            // or when you want to let multiple roles to have extra access:
            // "|| doesLoggedUserHasAuthorities(Arrays.asList(ERole.ROLE_MODERATOR, ERole.ROLE_ADMIN))"
            if (doesEntityBelongsToLoggedUser(o_entity.get())) {
                TodoItemDTOO dto = conversionService.todoItemToDTOO(o_entity.get());

                return Optional.of(new TodoItemDTOOPack(dto, "Successful request"));
            }
            return Optional.of(new TodoItemDTOOPack(null, "Requested item doesn't belong to logged user", false));
        }
        return Optional.empty();
    }

    public Optional<TodoItemDTOO> createTodoItem(TodoItemDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.CREATE);

        // Find User by logged username
        Optional<User> o_user = userRepository.findByUsername(getLoggedUserDetails().getUsername());

        // Find todoItem by name
        Optional<TodoItem> o_entity = todoItemRepository.findByName(dtoi.getName());

        if (o_user.isPresent() && !o_entity.isPresent()) {
            TodoItem entity = conversionService.todoItemDTOIToEntity(dtoi);
            entity.setUser(o_user.get());

            entity = todoItemRepository.save(entity);
            TodoItemDTOO result = conversionService.todoItemToDTOO(entity);

            return Optional.of(result);
        }
        return Optional.empty();
    }

    public Optional<TodoItemDTOOPack> updateTodoItem(Long id, TodoItemDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.UPDATE);

        // Get todoItems by id and name
        Optional<TodoItem> o_entity = todoItemRepository.findById(id);
        Optional<TodoItem> o_entity_new_name = todoItemRepository.findByName(dtoi.getName());

        if (o_entity.isPresent()) {
            // Check if todoItem belongs to logged user
            if (doesEntityBelongsToLoggedUser(o_entity.get())) {
                // Check if item with new name (o_entity_new_name) has id same as old item (o_entity).
                // If not - don't update that item (since names need to be unique, we can update only item with old name)
                if (o_entity_new_name.isPresent()) {
                    if (o_entity_new_name.get().getId() != o_entity.get().getId()) {
                        return Optional.of(new TodoItemDTOOPack(null, "Given name is already occupied by other element - try another one"));
                    }
                }

                TodoItem entity = conversionService.todoItemDTOIToEntity(dtoi);
                entity.setId(o_entity.get().getId());
                entity.setCreatedOn(o_entity.get().getCreatedOn());
                entity.setUser(o_entity.get().getUser());

                // If new entity (and dtoi) has null "isDone" parameter, set it to previous one
                if (entity.getIsDone() == null) {
                    entity.setIsDone(o_entity.get().getIsDone());
                }
                todoItemRepository.save(entity);

                return Optional.of(new TodoItemDTOOPack(conversionService.todoItemToDTOO(entity), ""));
            }
            return Optional.of(new TodoItemDTOOPack(null, "Requested item doesn't belong to logged user", false));
        }
        return Optional.empty();
    }

    public Optional<UpdateStatusTodoItemDTOO> updateStatusOfTodoItem(Long id, TodoItemIsDoneDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.UPDATE);

        // Get todoItem by id
        Optional<TodoItem> o_entity = todoItemRepository.findById(id);

        if (o_entity.isPresent()) {
            // Check if todoItem belongs to logged user
            if (doesEntityBelongsToLoggedUser(o_entity.get())) {
                TodoItem entity = o_entity.get();
                entity.setIsDone(dtoi.getIsDone() ? 1 : 0);
                todoItemRepository.save(entity);

                return Optional.of(new UpdateStatusTodoItemDTOO(true, true));
            }
            return Optional.of(new UpdateStatusTodoItemDTOO(true, false));
        }
        return Optional.empty();
    }

    public Optional<DeleteTodoItemDTOO> deleteTodoItem(Long id) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.DELETE);

        Optional<TodoItem> o_entity = todoItemRepository.findById(id);
        if (o_entity.isPresent()) {
            // Check if todoItem belongs to logged user
            if (doesEntityBelongsToLoggedUser(o_entity.get())) {
                TodoItem entity = o_entity.get();
                todoItemRepository.delete(entity);

                return Optional.of(new DeleteTodoItemDTOO(true, true));
            }
            return Optional.of(new DeleteTodoItemDTOO(true, false));
        }

        return Optional.empty();
    }

    // -- ADDITIONAL METHODS --
    private UserDetails getLoggedUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean doesEntityBelongsToLoggedUser(TodoItem entity) {
        UserDetails userDetails = getLoggedUserDetails();

        return entity.getUser().getUsername().equals(userDetails.getUsername());
    }

    // Can be used when defining special behaviour, when specific user role is present
    private boolean doesLoggedUserHasAuthority(ERole authority) {
        String authorityName = authority.name();
        UserDetails userDetails = getLoggedUserDetails();
        List<String> userAuthorities = userDetails.getAuthorities()
                .stream()
                .map(it -> it.getAuthority())
                .collect(Collectors.toList());

        return userAuthorities.contains(authorityName);
    }

    // Additional method - to check if at least one role is present
    private boolean doesLoggedUserHasAuthorities(List<ERole> authorities) {
        List<String> authoritiesNames = authorities.stream().map(it -> it.name()).collect(Collectors.toList());
        UserDetails userDetails = getLoggedUserDetails();
        List<String> userAuthorities = userDetails.getAuthorities()
                .stream()
                .map(it -> it.getAuthority())
                .collect(Collectors.toList());

        boolean doesContainOne = false;
        for (String authorityName: authoritiesNames) {
            if (userAuthorities.contains(authorityName)) {
                doesContainOne = true;
                break;
            }
        }

        return doesContainOne;
    }
}
