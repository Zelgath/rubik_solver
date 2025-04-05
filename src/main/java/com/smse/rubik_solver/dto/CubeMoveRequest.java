package com.smse.rubik_solver.dto;

import lombok.Data;

@Data
public class CubeMoveRequest {
    private CubeDto cube;
    private String[] moves;
}
