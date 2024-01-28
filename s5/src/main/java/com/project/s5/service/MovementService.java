package com.project.s5.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.s5.models.Materiaux;
import com.project.s5.models.Movement;
import com.project.s5.repository.MovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;

    public Movement addMovement(Materiaux materiaux, Double quantity, String status_movement) {
        Timestamp now = Timestamp.from(Instant.now());
        Movement movement = Movement.builder().materiaux(materiaux).quantity(quantity).status_movement(status_movement)
                .date(now).build();
        movementRepository.save(movement);
        return movementRepository.save(movement);
    }

    public List<Movement> getAll() {

        return movementRepository.findAll();
    }
}
