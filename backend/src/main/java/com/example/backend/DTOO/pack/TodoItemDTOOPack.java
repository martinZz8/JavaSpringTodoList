package com.example.backend.DTOO.pack;

import com.example.backend.DTOO.TodoItemDTOO;

public class TodoItemDTOOPack {
    private TodoItemDTOO todoItem;
    private String message;
    private Boolean isAuthorized;

    public TodoItemDTOOPack(){}

    public TodoItemDTOOPack(TodoItemDTOO todoItem, String message, Boolean isAuthorized) {
        this.todoItem = todoItem;
        this.message = message;
        this.isAuthorized = isAuthorized;
    }

    public TodoItemDTOOPack(TodoItemDTOO todoItem, String message) {
        this.todoItem = todoItem;
        this.message = message;
        this.isAuthorized = true;
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

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }
}
