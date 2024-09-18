package com.github.gabguedes.ms_aluno.dto;

import com.github.gabguedes.ms_aluno.model.Aluno;
import com.github.gabguedes.ms_aluno.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlunoDTO {

    private Long id;
    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caractéres.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "Senha é obrigatório.")
    @Size(min = 6, message = "A senha deve ter no minímo 6 caractéres.")
    private String password;

    @NotBlank(message = "RM é obrigatório.")
    @Size(min = 5, max = 6, message = "A senha deve ter entre 5 e 6 caractéres.")
    private String rm;

    @NotNull(message = "Status é obrigatório.")
    private Status status;

    @NotBlank(message = "Turma é obrigatório.")
    private String turma;

    public AlunoDTO(Aluno entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.rm = entity.getRm();
        this.status = entity.getStatus();
        this.turma = entity.getTurma();
    }
}
