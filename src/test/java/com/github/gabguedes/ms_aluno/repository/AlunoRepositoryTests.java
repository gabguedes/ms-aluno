package com.github.gabguedes.ms_aluno.repository;

import com.github.gabguedes.ms_aluno.model.Aluno;
import com.github.gabguedes.ms_aluno.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class AlunoRepositoryTests {

    @Autowired
    private AlunoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalAlunos;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        countTotalAlunos = 3L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){


        repository.deleteById(existingId);

        Optional<Aluno> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){

        Aluno aluno = Factory.createAluno();

        aluno.setId(null);
        aluno = repository.save(aluno);
        Assertions.assertNotNull(aluno.getId());
        Assertions.assertEquals(countTotalAlunos + 1, aluno.getId());

    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){

        Optional<Aluno> query = repository.findById(existingId);

        Assertions.assertTrue(query.isPresent());

    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists(){

        Optional<Aluno> query = repository.findById(nonExistingId);

        Assertions.assertFalse(query.isPresent());

    }
//    @Test
//    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
//
//        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
//            repository.deleteById(nonExistingId);
//        });
//    }
}
