package com.github.gabguedes.ms_aluno.tests;

import com.github.gabguedes.ms_aluno.model.Aluno;
import com.github.gabguedes.ms_aluno.model.Status;

import java.math.BigDecimal;

public class Factory {

    public static Aluno createAluno() {

        Aluno aluno = new Aluno(1L, "Jobin", "jobin@fiap.com.br",
                "123456", "78996", Status.FORMADO, "3SIPG");

        return aluno;
    }
}
