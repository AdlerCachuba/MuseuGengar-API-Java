package com.obra.restAPI.agenda.obras.repository;

import com.obra.restAPI.agenda.obras.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
