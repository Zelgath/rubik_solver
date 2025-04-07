package com.smse.rubik_solver.dto;

import com.smse.rubik_solver.model.Cube;

import lombok.Data;

@Data
public class InitResponseDto {
    private String sessionId;
    private Cube cube;
}
