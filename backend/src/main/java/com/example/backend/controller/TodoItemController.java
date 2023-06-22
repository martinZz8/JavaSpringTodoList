package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/todo_item")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoItemController {
    @Autowired
    // get service

}
