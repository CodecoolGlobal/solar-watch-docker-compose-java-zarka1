package com.codecool.solarwatch.model.user;

import java.util.Set;

public record UserDTO(String username, String password, Set<String> roles) {}
