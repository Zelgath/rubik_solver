package com.smse.rubik_solver.dto;

import java.util.List;

import com.smse.rubik_solver.model.Cube;

import lombok.Data;

@Data
public class CubeMoveRequest {
    private Cube cube;
    private List<String> moves;
}
