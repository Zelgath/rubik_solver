package com.smse.rubik_solver.dto;

import com.smse.rubik_solver.model.Cube;

import lombok.Data;

@Data
public class CubeMoveRequest {
    private Cube cube;
    private String[] moves;
}
