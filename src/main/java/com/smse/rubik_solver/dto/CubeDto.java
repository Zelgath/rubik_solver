package com.smse.rubik_solver.dto;

import com.smse.rubik_solver.model.Color;

import lombok.Data;

@Data
public class CubeDto {
    private Color[][] front;
    private Color[][] top;
    private Color[][] left;
    private Color[][] right;
    private Color[][] bottom;
    private Color[][] back;
}
