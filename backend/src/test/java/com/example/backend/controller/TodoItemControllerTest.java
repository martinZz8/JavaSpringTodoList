// Form:
// 1) https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate
// 2) https://stackoverflow.com/questions/19238715/how-to-set-an-accept-header-on-spring-resttemplate-request
package com.example.backend.controller;

import com.example.backend.DTOI.LoginUserDTOI;
import com.example.backend.DTOI.RegisterUserDTOI;
import com.example.backend.DTOO.ActionCounterDTOO;
import com.example.backend.DTOI.TodoItemDTOI;
import com.example.backend.DTOO.LoginUserDTOO;
import com.example.backend.DTOO.RegisterUserDTOO;
import com.example.backend.DTOO.TodoItemDTOO;
import com.example.backend.DTOO.pack.TodoItemDTOOPack;
import com.example.backend.enums.ActionCounterName;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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
    private static final String AUTH_URL = "/api/auth/";

    // Urls for TodoItem
    private static final String GET_TODO_ITEM_BY_ID = TODO_ITEM_URL + "get/%s";
    private static final String CREATE_TODO_ITEM = TODO_ITEM_URL + "create";
    private static final String DELETE_TODO_ITEM = TODO_ITEM_URL + "delete/%s";
    private static final String UPDATE_TODO_ITEM = TODO_ITEM_URL + "update/%s";

    // Urls for ActionCounter
    private static final String GET_ACTION_COUNTER_BY_NAME = ACTION_COUNTER_URL + "get/%s";

    // Urls for Auth
    private static final String REGISTER_USER = AUTH_URL + "register";
    private static final String LOGIN_USER = AUTH_URL + "login";
    private static final String DELETE_USER = AUTH_URL + "delete/%s";

    // Ids and objects created before tests
    private static Long OBJECT_TO_DELETE_ID = null;
    private static Long OBJECT_TO_UPDATE_ID = null;

    private static TodoItemDTOO createdObjectToGet = new TodoItemDTOO();

    // Id and bearer token for logged user
    private static Long ID_USER = null;
    private static String BEARER_TOKEN_USER = null;

    // Credentials and token for admin (has to be previously created)
    private static final String USERNAME_ADMIN = "adm";
    private static final String PASSWORD_ADMIN = "test";
    private static String BEARER_TOKEN_ADMIN = null;

    @Autowired
    private TestRestTemplate restTemplate;

    // -- Useful methods --
    private <T> HttpEntity<T> createAuthorizedHttpEntity(T body, String bearerToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+bearerToken);

        return new HttpEntity<T>(body, headers);
    }

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
        TodoItemDTOI todoItem = new TodoItemDTOI();
        todoItem.setName(name);
        todoItem.setDescription(description);
        todoItem.setIsDone(isDone);

        HttpEntity<TodoItemDTOI> entity = createAuthorizedHttpEntity(todoItem, BEARER_TOKEN_USER);
        ResponseEntity<TodoItemDTOO> result = restTemplate.postForEntity(CREATE_TODO_ITEM, entity, TodoItemDTOO.class);

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

    private void checkTodoItemMoreFields(TodoItemDTOO todoItem, Long id, String name, String description, Boolean isDone) {
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
        // Register user
        String user_username = UUID.randomUUID().toString();
        String user_email = UUID.randomUUID().toString();
        String user_password = UUID.randomUUID().toString();
        String user_firstName = UUID.randomUUID().toString();
        String user_lastName = UUID.randomUUID().toString();

        RegisterUserDTOI userDTOI = new RegisterUserDTOI(
            user_username,
            user_email,
            user_password,
            user_firstName,
            user_lastName
        );
        restTemplate.postForEntity(REGISTER_USER, userDTOI, RegisterUserDTOO.class);

        // Login user and admin
        // 1) user
        LoginUserDTOI loginUserDTOI = new LoginUserDTOI(
            user_username,
            user_password
        );

        ResponseEntity<LoginUserDTOO> loggedUser = restTemplate.postForEntity(LOGIN_USER, loginUserDTOI, LoginUserDTOO.class);
        ID_USER = loggedUser.getBody().getId();
        BEARER_TOKEN_USER = loggedUser.getBody().getJwt();

        // 2) admin
        LoginUserDTOI loginAdminDTOI = new LoginUserDTOI(
                USERNAME_ADMIN,
                PASSWORD_ADMIN
        );

        ResponseEntity<LoginUserDTOO> loggedAdmin = restTemplate.postForEntity(LOGIN_USER, loginAdminDTOI, LoginUserDTOO.class);
        BEARER_TOKEN_ADMIN = loggedAdmin.getBody().getJwt();

        // Create required objects
        createdObjectToGet = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());

        TodoItemDTOO objectToUpdate = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());
        OBJECT_TO_UPDATE_ID = objectToUpdate.getId();

        TodoItemDTOO objectToDelete = createSampleTodoItemFromObject(locallyCreateRandomTodoItem());
        OBJECT_TO_DELETE_ID = objectToDelete.getId();
    }

    @AfterAll
    void tearDownAfterClass() throws Exception {
        String url;
        HttpEntity<String> entity = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);

        // Delete item with id from previouslyCreatedTodoItem
        if (createdObjectToGet.getId() != null) {
            url = String.format(DELETE_TODO_ITEM, createdObjectToGet.getId());
            restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        }

        // Delete todoItem with OBJECT_TO_UPDATE_ID
        if (OBJECT_TO_UPDATE_ID != null) {
            url = String.format(DELETE_TODO_ITEM, OBJECT_TO_UPDATE_ID);
            restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        }

        // Delete todoItem with OBJECT_TO_DELETE_ID
        try {
            if (OBJECT_TO_DELETE_ID != null) {
                url = String.format(DELETE_TODO_ITEM, OBJECT_TO_DELETE_ID);
                restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
            }
        }
        catch (Exception e) {

        }

        // Delete user with ID_USER
        entity = createAuthorizedHttpEntity("", BEARER_TOKEN_ADMIN);
        url = String.format(DELETE_USER, ID_USER);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    // -- Tests --
    @Test
    void testGetTodoItem() {
        String url = String.format(GET_TODO_ITEM_BY_ID, createdObjectToGet.getId());
        HttpEntity<String> entity = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);
        ResponseEntity<TodoItemDTOO> retrievedTodoItem = restTemplate.exchange(url, HttpMethod.GET, entity, TodoItemDTOO.class);

        checkTodoItemMoreFields(retrievedTodoItem.getBody(), createdObjectToGet.getId(), createdObjectToGet.getName(), createdObjectToGet.getDescription(), createdObjectToGet.getDone());
    }

    @Test
    void testCreateTodoItem() {
        TodoItemDTOO todoItemToCreate = locallyCreateRandomTodoItem();
        TodoItemDTOO createdTodoItem = createSampleTodoItemFromObject(todoItemToCreate);

        checkTodoItemFields(createdTodoItem, todoItemToCreate.getName(), todoItemToCreate.getDescription(), todoItemToCreate.getDone());

        String url = String.format(DELETE_TODO_ITEM, createdTodoItem.getId());
        HttpEntity<String> entity = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    @Test
    void testUpdateTodoItem() {
        TodoItemDTOI inputTodoItem = new TodoItemDTOI(UUID.randomUUID().toString(), UUID.randomUUID().toString(), false);
        String url = String.format(UPDATE_TODO_ITEM, OBJECT_TO_UPDATE_ID);
        HttpEntity<TodoItemDTOI> entity = createAuthorizedHttpEntity(inputTodoItem, BEARER_TOKEN_USER);
        restTemplate.exchange(url, HttpMethod.PUT, entity, TodoItemDTOOPack.class);

        url = String.format(GET_TODO_ITEM_BY_ID, OBJECT_TO_UPDATE_ID);
        HttpEntity<String> entity2 = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);
        ResponseEntity<TodoItemDTOO> retrievedTodoItem = restTemplate.exchange(url, HttpMethod.GET, entity2, TodoItemDTOO.class);

        checkTodoItemFields(retrievedTodoItem.getBody(), inputTodoItem.getName(), inputTodoItem.getDescription(), inputTodoItem.getIsDone());
    }

    @Test
    void testDeleteTodoItem() {
        String url = String.format(DELETE_TODO_ITEM, OBJECT_TO_DELETE_ID);
        HttpEntity<String> entity = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        url = String.format(GET_TODO_ITEM_BY_ID, OBJECT_TO_DELETE_ID);
        ResponseEntity<TodoItemDTOO> retrievedTodoItem = restTemplate.exchange(url, HttpMethod.GET, entity, TodoItemDTOO.class);

        Assertions.assertNull(retrievedTodoItem.getBody());
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
        HttpEntity<String> entity = createAuthorizedHttpEntity("", BEARER_TOKEN_USER);
        restTemplate.exchange(urlToDeleteTodoItem, HttpMethod.DELETE, entity, Void.class);

        // Get current state of creation action counter after creation of todoItem
        ActionCounterDTOO afterCreateActionCounter = restTemplate.getForObject(urlToGetCreateActionCounter, ActionCounterDTOO.class);

        // Check if creation action counter increased by 1
        Assertions.assertTrue((prevCreateActionCounter.getCounter() + 1) == afterCreateActionCounter.getCounter()); // can be also used "Assertions.assertEquals(prevCreateActionCounter.getCounter()+1, afterCreateActionCounter.getCounter())"
    }
}
