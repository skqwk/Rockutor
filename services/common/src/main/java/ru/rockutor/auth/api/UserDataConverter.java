package ru.rockutor.auth.api;

import ru.rockutor.auth.dto.UserData;

import java.util.Map;

public class UserDataConverter {
    static UserData fromMap(Map<?, ?> response) {
        String username = (String) response.get("username");
        String role = (String) response.get("role");

        return new UserData(username, role);
    }
}
