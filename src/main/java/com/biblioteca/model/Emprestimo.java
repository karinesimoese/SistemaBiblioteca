package com.biblioteca.model;

import java.io.Serializable; // Import necessário para persistência
import java.time.LocalDate; // Import necessário para datas

public class Emprestimo implements Serializable {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(7);
    }

    // Getters
    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }

    @Override
    public String toString() {
        return "Empréstimo{" +
                "Livro=" + livro.getTitulo() +
                ", Usuário=" + usuario.getNome() +
                ", Data Empréstimo=" + dataEmprestimo +
                ", Devolução Prevista=" + dataPrevistaDevolucao +
                '}';
    }
}
