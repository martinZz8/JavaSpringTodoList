package com.example.backend.enums;

import java.util.List;

public final class ActionCounterName {
    public static final String READ_ALL = "read_all";
    public static final String READ_WITH_FILTER = "read_with_filter";
    public static final String READ_ONE = "read_one";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    public static boolean isProperActionName(String name) {
        List<String> properNames = List.of(READ_ALL, READ_WITH_FILTER, READ_ONE, CREATE, UPDATE, DELETE);

        return properNames.contains(name);
    }
}
