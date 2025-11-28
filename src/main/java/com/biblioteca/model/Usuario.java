package com.biblioteca.model;

import java.io.Serializable; // Import necessário para persistência

// Classe base abstrata (Polimorfismo e Herança)
public abstract class Usuario implements Serializable {
    protected String nome;
    protected String matricula;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    // Getters
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }

    // Método abstrato: Força Aluno e Professor a definir seus limites
    public abstract int getLimiteEmprestimos();

    @Override
    public String toString() {
        return "Usuário [Nome=" + nome + ", Matrícula=" + matricula + "]";
    }
}