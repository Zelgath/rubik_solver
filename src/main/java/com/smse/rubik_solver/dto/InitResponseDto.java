package com.smse.rubik_solver.dto;

import com.smse.rubik_solver.model.Cube;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitResponseDto {
    private String sessionId;
    private Cube cube;
}
