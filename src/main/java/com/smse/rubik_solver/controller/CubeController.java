package com.smse.rubik_solver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smse.rubik_solver.dto.CubeDto;
import com.smse.rubik_solver.dto.CubeMoveRequest;
import com.smse.rubik_solver.dto.InitResponseDto;
import com.smse.rubik_solver.model.Cube;
import com.smse.rubik_solver.model.UserSession;
import com.smse.rubik_solver.service.CubeService;
import com.smse.rubik_solver.service.SessionManager;
import com.smse.rubik_solver.service.ValidationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cube")
@RequiredArgsConstructor
public class CubeController {
    private final CubeService cubeService;
    private final SessionManager sessionManager;
    private final ValidationService validationService;

    @PostMapping("/init")
    public ResponseEntity<InitResponseDto> initCube(@RequestBody CubeDto cube) {
        if (!validationService.isCubeValid(cube)) {
            return ResponseEntity.badRequest().build();
        }

        String sessionId = sessionManager.createSession();
        UserSession session = sessionManager.getSession(sessionId);
        cubeService.initializeCube(session.getCube(), cube);

        InitResponseDto response = new InitResponseDto();
        response.setSessionId(sessionId);
        response.setCube(cube);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/move")
    public ResponseEntity<CubeDto> applyMoves(@RequestBody CubeMoveRequest request) {
        try {
            Cube cube = new Cube(request.getCube());
            cube.makeMovesFromList(request.getMoves());
            return ResponseEntity.ok(cube.toDto());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
