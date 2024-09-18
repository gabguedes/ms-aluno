package com.github.gabguedes.ms_aluno.repository;

import com.github.gabguedes.ms_aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
