package com.biblioteca.model;

// Aluno herda de Usuario
public class Aluno extends Usuario {
    private static final int LIMITE_ALUNO = 3;

    public Aluno(String nome, String matricula) {
        super(nome, matricula);
    }

    // Implementação obrigatória do método abstrato (Polimorfismo)
    @Override
    public int getLimiteEmprestimos() {
        return LIMITE_ALUNO;
    }
}
