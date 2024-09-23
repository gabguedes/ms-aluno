package com.github.gabguedes.ms_aluno.service;

import com.github.gabguedes.ms_aluno.dto.AlunoDTO;
import com.github.gabguedes.ms_aluno.model.Aluno;
import com.github.gabguedes.ms_aluno.repository.AlunoRepository;
import com.github.gabguedes.ms_aluno.service.exception.ResourceNotFoundException;
import com.github.gabguedes.ms_aluno.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class AlunoServiceTests {

    @InjectMocks
    private AlunoService service;

    @Mock
    private AlunoRepository repository;

    private Long existingId;
    private Long nonExistingId;

    private Aluno aluno;
    private AlunoDTO alunoDTO;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(existingId);

        aluno = Factory.createAluno();
        alunoDTO = new AlunoDTO(aluno);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(aluno));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.save(any())).thenReturn(aluno);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(aluno);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){

        Optional<Aluno> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists(){

        Optional<Aluno> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void insertShouldReturnAlunoDTO(){

        AlunoDTO result = service.insert(alunoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), aluno.getId());
    }

    @Test
    public void updateShouldReturnAlunoDTOWhenIdExists(){
        AlunoDTO result = service.update(aluno.getId(), alunoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getNome(), aluno.getNome());
    }

    @Test
    public void updateShouldReturnResourceNotFoundWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, alunoDTO);
        });
    }
}
