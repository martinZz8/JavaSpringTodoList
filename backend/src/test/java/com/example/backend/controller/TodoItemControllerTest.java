package com.example.backend.controller;

import com.example.backend.DTO.ActionCounterDTOO;
import com.example.backend.DTO.TodoItemDTOI;
import com.example.backend.DTO.TodoItemDTOO;
import com.example.backend.enums.ActionCounterName;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

// Used assertion methods:
// - Assertions.fail(),
// - Assertions.assertNull(),
// - Assertions.assertTrue(), (as Assertions.assertEqual()),
// - return (as proper pass of specified test)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class TodoItemControllerTest {
    // Global urls
    private static final String TODO_ITEM_URL = "/api/todo_item/";
    private static final String ACTION_COUNTER_URL = "/api/action_counter/";

    // Urls for TodoItem
    private static final String GET_TODO_ITEM_BY_ID = TODO_ITEM_URL + "get/%s";
    private static final String CREATE_TODO_ITEM = TODO_ITEM_URL + "create";
    private static final String DELETE_TODO_ITEM = TODO_ITEM_URL + "delete/%s";
    private static final String UPDATE_TODO_ITEM = TODO_ITEM_URL + "update/%s";

    // Urls for ActionCounter
    private static final String GET_ACTION_COUNTER_BY_NAME = ACTION_COUNTER_URL + "get/%s";

    // Ids and objects created before tests
    private static Long OBJECT_TO_DELETE_ID = null;
    private static Long OBJECT_TO_UPDATE_ID = null;

    private static TodoItemDTOO createdObjectToGet = new TodoItemDTOO();

    @Autowired
    private TestRestTemplate restTemplate;

    // -- Useful methods --
    private TodoItemDTOO locallyCreateRandomTodoItem() {
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        Boolean isDone = false;

        TodoItemDTOO todoItem = new TodoItemDTOO();
        todoItem.setName(name);
        todoItem.setDescription(description);
        todoItem.setDone(isDone);

        return todoItem;
    }

    private TodoItemDTOO createSampleTodoItem(String name, String description, Boolean isDone) {
        TodoItemDTOO todoItem = new TodoItemDTOO();
        todoItem.setName(name);
        todoItem.setDescription(description);
        todoItem.setDone(isDone);

        ResponseEntity<TodoItemDTOO> result = restTemplate.postForEntity(CREATE_TODO_ITEM, todoItem, TodoItemDTOO.class);

        return result.getBody();
    }

    private TodoItemDTOO createSampleTodoItemFromObject(TodoItemDTOO item) {
        return createSampleTodoItem(item.getName(), item.getDescription(), item.getDone());
    }

    private void checkTodoItemFields(TodoItemDTOO todoItem, String name, String description, Boolean isDone) {
        boolean assertion;

        assertion = todoItem.getName().equals(name);
        if (!assertion)
            Assertions.fail();
        assertion = todoItem.getDescription().equals(description);
        if (!assertion)
            Assertions.fail();
        assertion = todoItem.getDone().equals(isDone);
        if (!assertion)
            Assertions.fail();
    }

    private void checkTodoItemAllFields(TodoItemDTOO todoItem, Long id, String name, String description, Boolean isDone) {
        boolean assertion;

        assertion = todoItem.getId().equals(id);
        if (!assertion)
            Assertions.fail();
        assertion = todoItem.getName().equals(name);
        if (!assertion)
            Assertions.fail();
        assertion = todoItem.getDescription().equals(description);
        if (!assertion)
            Assertions.fail();
        assertion = todoItem.getDone().equals(isDone);
        if (!assertion)
            Assertions.fail();
    }

    @BeforeAll
    void setUpBeforeClass() throws Exception {
        createdObjectToGet = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());

        TodoItemDTOO objectToUpdate = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());
        OBJECT_TO_UPDATE_ID = objectToUpdate.getId();

        TodoItemDTOO objectToDelete = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());
        OBJECT_TO_DELETE_ID = objectToDelete.getId();
    }

    @AfterAll
    void tearDownAfterClass() throws Exception {
        String url;

        // Delete item with id from previouslyCreatedTodoItem
        if (createdObjectToGet.getId() != null) {
            url = String.format(DELETE_TODO_ITEM, createdObjectToGet.getId());
            restTemplate.delete(url);
        }

        // Delete todoItem with OBJECT_TO_UPDATE_ID
        if (OBJECT_TO_UPDATE_ID != null) {
            url = String.format(DELETE_TODO_ITEM, OBJECT_TO_UPDATE_ID);
            restTemplate.delete(url);
        }

        // Delete todoItem with OBJECT_TO_DELETE_ID
        try {
            if (OBJECT_TO_DELETE_ID != null) {
                url = String.format(DELETE_TODO_ITEM, OBJECT_TO_DELETE_ID);
                restTemplate.delete(url);
            }
        }
        catch (Exception e) {

        }
    }

    // -- Tests --
    @Test
    void testGetTodoItem() {
        String url = String.format(GET_TODO_ITEM_BY_ID, createdObjectToGet.getId());
        TodoItemDTOO retrievedTodoItem = restTemplate.getForObject(url, TodoItemDTOO.class);

        checkTodoItemAllFields(retrievedTodoItem, createdObjectToGet.getId(), createdObjectToGet.getName(), createdObjectToGet.getDescription(), createdObjectToGet.getDone());
    }

    @Test
    void testCreateTodoItem() {
        TodoItemDTOO todoItemToCreate = locallyCreateRandomTodoItem();
        TodoItemDTOO createdTodoItem = createSampleTodoItemFromObject(todoItemToCreate);

        checkTodoItemFields(createdTodoItem, todoItemToCreate.getName(), todoItemToCreate.getDescription(), todoItemToCreate.getDone());

        String url = String.format(DELETE_TODO_ITEM, createdTodoItem.getId());
        restTemplate.delete(url);
    }

    @Test
    void testUpdateTodoItem() {
        TodoItemDTOI inputTodoItem = new TodoItemDTOI(UUID.randomUUID().toString(), UUID.randomUUID().toString(), false);
        String url = String.format(UPDATE_TODO_ITEM, OBJECT_TO_UPDATE_ID);
        restTemplate.put(url, inputTodoItem);

        url = String.format(GET_TODO_ITEM_BY_ID, OBJECT_TO_UPDATE_ID);
        TodoItemDTOO retrievedTodoItem = restTemplate.getForObject(url, TodoItemDTOO.class);

        checkTodoItemFields(retrievedTodoItem, inputTodoItem.getName(), inputTodoItem.getDescription(), inputTodoItem.getIsDone());
    }

    @Test
    void testDeleteTodoItem() {
        String url = String.format(DELETE_TODO_ITEM, OBJECT_TO_DELETE_ID);
        restTemplate.delete(url);

        url = String.format(GET_TODO_ITEM_BY_ID, OBJECT_TO_DELETE_ID);
        TodoItemDTOO retrievedTodoItem = restTemplate.getForObject(url, TodoItemDTOO.class);

        Assertions.assertNull(retrievedTodoItem);
    }

    @Test
    void testWhenCreatedTodoItemActionCounterIncreases() {
        // Get current state of creation action counter before creation of todoItem
        String urlToGetCreateActionCounter = String.format(GET_ACTION_COUNTER_BY_NAME, ActionCounterName.CREATE);
        ActionCounterDTOO prevCreateActionCounter = restTemplate.getForObject(urlToGetCreateActionCounter, ActionCounterDTOO.class);

        // Create todoItem
        TodoItemDTOO todoItemToCreate = locallyCreateRandomTodoItem();
        TodoItemDTOO createdTodoItem = createSampleTodoItemFromObject(todoItemToCreate);

        // Delete todoItem
        String urlToDeleteTodoItem = String.format(DELETE_TODO_ITEM, createdTodoItem.getId());
        restTemplate.delete(urlToDeleteTodoItem);

        // Get current state of creation action counter after creation of todoItem
        ActionCounterDTOO afterCreateActionCounter = restTemplate.getForObject(urlToGetCreateActionCounter, ActionCounterDTOO.class);

        // Check if creation action counter increased by 1
        Assertions.assertTrue((prevCreateActionCounter.getCounter() + 1) == afterCreateActionCounter.getCounter()); // can be also used "Assertions.assertEquals"
    }
}
