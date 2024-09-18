package com.github.gabguedes.ms_aluno.service;

import com.github.gabguedes.ms_aluno.dto.AlunoDTO;
import com.github.gabguedes.ms_aluno.model.Aluno;
import com.github.gabguedes.ms_aluno.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Transactional(readOnly = true)
    public List<AlunoDTO> findAll(){
        return repository.findAll().stream().map(AlunoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlunoDTO findById(Long id){
        Aluno aluno = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Recurso não encontrado -> id: " + id)
        );

        return new AlunoDTO(aluno);
    }

    @Transactional
    public AlunoDTO insert(AlunoDTO dto){
        Aluno aluno = new Aluno();
        copyDtoToEntity(dto, aluno);
        aluno = repository.save(aluno);
        return new AlunoDTO(aluno);
    }

    @Transactional
    public AlunoDTO update(Long id, AlunoDTO dto){
        Aluno aluno = repository.getReferenceById(id);
        copyDtoToEntity(dto, aluno);
        aluno= repository.save(aluno);
        return new AlunoDTO(aluno);
    }

    @Transactional
    public void delete(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Recurso não encontrado id: " + id);
        }
    }

    private void copyDtoToEntity(AlunoDTO dto, Aluno aluno) {
        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setPassword(dto.getPassword());
        aluno.setRm(dto.getRm());
        aluno.setStatus(dto.getStatus());
        aluno.setTurma(dto.getTurma());
    }
}
