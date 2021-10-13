package com.obra.restAPI.agenda.obras.repository;

import com.obra.restAPI.agenda.obras.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra, Long> {
}
