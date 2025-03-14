package com.smse.rubik_solver.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.model.Cube;
import com.smse.rubik_solver.model.UserSession;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SessionManager {
    private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();

    public String createSession() {
        String sessionId = java.util.UUID.randomUUID().toString();
        sessions.put(sessionId, new UserSession(sessionId, new Cube()));
        log.debug("Created new session with ID: {}", sessionId);
        log.debug("All session ids: {}", String.join(", ", sessions.keySet()));
        return sessionId;
    }

    public UserSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
