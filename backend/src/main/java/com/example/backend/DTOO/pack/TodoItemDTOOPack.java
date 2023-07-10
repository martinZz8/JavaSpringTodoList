package com.example.backend.DTOO.pack;

import com.example.backend.DTOO.TodoItemDTOO;

public class TodoItemDTOOPack {
    private TodoItemDTOO todoItem;
    private String message;

    public TodoItemDTOOPack(){}

    public TodoItemDTOOPack(TodoItemDTOO todoItem, String message) {
        this.todoItem = todoItem;
        this.message = message;
    }

    public TodoItemDTOO getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItemDTOO todoItem) {
        this.todoItem = todoItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
