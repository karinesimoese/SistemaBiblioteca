package com.biblioteca.model;

// Professor herda de Usuario
public class Professor extends Usuario {
    private static final int LIMITE_PROFESSOR = 5;

    public Professor(String nome, String matricula) {
        super(nome, matricula);
    }

    // Implementação obrigatória do método abstrato (Polimorfismo)
    @Override
    public int getLimiteEmprestimos() {
        return LIMITE_PROFESSOR;
    }
}