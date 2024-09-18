package com.github.gabguedes.ms_aluno.controller;

import com.github.gabguedes.ms_aluno.dto.AlunoDTO;
import com.github.gabguedes.ms_aluno.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll(){
        List<AlunoDTO> alunoDTOList = service.findAll();
        return ResponseEntity.ok(alunoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable @NotNull Long id){
        AlunoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> insert(@RequestBody @Valid AlunoDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto)
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> update(@PathVariable @NotNull Long id,
                                           @RequestBody @Valid AlunoDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlunoDTO> delete(@PathVariable @NotNull Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
