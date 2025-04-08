package com.smse.rubik_solver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smse.rubik_solver.dto.CubeMoveRequest;
import com.smse.rubik_solver.dto.CubeScrambleRequest;
import com.smse.rubik_solver.dto.InitResponseDto;
import com.smse.rubik_solver.dto.RandomCubeRequest;
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
    public ResponseEntity<InitResponseDto> initCube(@RequestBody Cube c) {
        Cube cube = cubeService.initializeCube(c);
        if (!validationService.isCubeValid(cube)) {
            return ResponseEntity.badRequest().build();
        }

        String sessionId = sessionManager.createSession();
        UserSession session = sessionManager.getSession(sessionId);
        session.setCube(cube);

        InitResponseDto response = InitResponseDto.builder()
                .sessionId(sessionId)
                .cube(cube)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/move")
    public ResponseEntity<Cube> applyMoves(@RequestBody CubeMoveRequest request) {
        try {
            Cube cube = cubeService.initializeCube(request.getCube());
            cubeService.applyMoves(cube, request.getMoves());
            return ResponseEntity.ok(cube);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/scramble")
    public ResponseEntity<Cube> scramble(@RequestBody CubeScrambleRequest request) {
        try {
            Cube cube = cubeService.initializeCube(request.getCube());
            cubeService.getRandomScramble(cube, request.getN());
            return ResponseEntity.ok(cube);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/random")
    public ResponseEntity<Cube> random(@RequestBody RandomCubeRequest request) {
        try {
            Cube cube = cubeService.createSolvedCube();
            cubeService.getRandomScramble(cube, 20);
            return ResponseEntity.ok(cube);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
