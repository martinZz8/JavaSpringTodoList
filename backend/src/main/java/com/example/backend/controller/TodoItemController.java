package com.example.backend.controller;

import com.example.backend.DTOI.TodoItemDTOI;
import com.example.backend.DTOO.DeleteTodoItemDTOO;
import com.example.backend.DTOO.TodoItemDTOO;
import com.example.backend.DTOI.TodoItemIsDoneDTOI;
import com.example.backend.DTOO.UpdateStatusTodoItemDTOO;
import com.example.backend.DTOO.pack.TodoItemDTOOPack;
import com.example.backend.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/todo_item")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoItemController {
    @Autowired
    private TodoItemService todoItemService;

    @GetMapping(path = "/all", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public List<TodoItemDTOO> getAllTodoItems() {
        return todoItemService.getAllTodoItems();
    }

    @GetMapping(path = "/all_filtered", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public List<TodoItemDTOO> getAllFilteredTodoItems(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "done", required = false) Boolean isDone,
            @RequestParam(name = "asc", defaultValue = "false") Boolean isAscending
    ) {
        return todoItemService.getAllTodoItemsWithFiltering(name, isDone, isAscending);
    }

    @GetMapping(path = "/get/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TodoItemDTOO> getTodoItemById(@PathVariable String id) {
        try {
            Optional<TodoItemDTOOPack> o_todo_item = todoItemService.getTodoItemById(Long.parseLong(id));

            if (o_todo_item.isPresent()) {
                if (o_todo_item.get().getTodoItem() != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(o_todo_item.get().getTodoItem());
                }

                if (o_todo_item.get().getAuthorized()) {
                    // There is no case to enter here
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    // Required parameters: "name" (String), "description" (String)
    // Optional parameters: "isDone" (Boolean)
    @PostMapping(path = "/create", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TodoItemDTOO> createTodoItem(@RequestBody TodoItemDTOI dtoi) {
        // If dtoi has null "isDone" parameter, set it to default value - false
        if (dtoi.getIsDone() == null) {
            dtoi.setIsDone(false);
        }

        Optional<TodoItemDTOO> o_todo_item = todoItemService.createTodoItem(dtoi);

        if (o_todo_item.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o_todo_item.get());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TodoItemDTOOPack> updateTodoItem(@PathVariable String id, @RequestBody TodoItemDTOI dtoi) {
        try {
            Optional<TodoItemDTOOPack> o_todo_item = todoItemService.updateTodoItem(Long.parseLong(id), dtoi);

            if (o_todo_item.isPresent()) {
                if (o_todo_item.get().getTodoItem() != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(o_todo_item.get());
                }

                if (o_todo_item.get().getAuthorized()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(o_todo_item.get());
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(o_todo_item.get());
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("/update_status/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateStatusOfTodoItem(@PathVariable String id, @RequestBody TodoItemIsDoneDTOI dtoi) {
        try {
            Optional<UpdateStatusTodoItemDTOO> o_item = todoItemService.updateStatusOfTodoItem(Long.parseLong(id), dtoi);

            if (o_item.isPresent()) {
                if (o_item.get().getAuthorized()) {
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable String id) {
        try
        {
            Optional<DeleteTodoItemDTOO> o_item = todoItemService.deleteTodoItem(Long.parseLong(id));

            if(o_item.isPresent()) {
                if (o_item.get().getAuthorized()) {
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(NumberFormatException e)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
