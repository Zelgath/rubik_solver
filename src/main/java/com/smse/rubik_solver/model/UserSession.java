package com.smse.rubik_solver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSession {
    private String sessionId;
    private Cube cube;
}
