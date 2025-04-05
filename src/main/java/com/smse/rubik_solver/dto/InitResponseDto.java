package com.smse.rubik_solver.dto;

import lombok.Data;

@Data
public class InitResponseDto {
    private String sessionId;
    private CubeDto cube;
}
