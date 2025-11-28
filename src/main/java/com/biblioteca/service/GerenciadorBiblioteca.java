package com.biblioteca.service;

import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.Emprestimo;

import java.io.*; // Import necessário para persistência (File, Stream)
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GerenciadorBiblioteca {

    // Atributos de armazenamento
    private List<Livro> livros = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Emprestimo> emprestimosAtivos = new ArrayList<>();

    // Nome do arquivo onde os dados serão salvos
    private final String ARQUIVO_DADOS = "dados_biblioteca.ser";

    // --- Métodos de Persistência ---

    public void salvarDados() {
        try (
                FileOutputStream fileOut = new FileOutputStream(ARQUIVO_DADOS);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        ) {
            // Salva as três listas no arquivo
            objOut.writeObject(this.livros);
            objOut.writeObject(this.usuarios);
            objOut.writeObject(this.emprestimosAtivos);
            System.out.println("✅ Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de dados não encontrado. Iniciando com dados vazios.");
            return;
        }

        try (
                FileInputStream fileIn = new FileInputStream(ARQUIVO_DADOS);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
        ) {
            // Carrega as três listas na ordem correta
            this.livros = (List<Livro>) objIn.readObject();
            this.usuarios = (List<Usuario>) objIn.readObject();
            this.emprestimosAtivos = (List<Emprestimo>) objIn.readObject();
            System.out.println("✅ Dados carregados com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao carregar dados. Iniciando com listas vazias.");
            this.livros = new ArrayList<>();
            this.usuarios = new ArrayList<>();
            this.emprestimosAtivos = new ArrayList<>();
        }
    }

    // --- Métodos de Cadastro ---

    public void cadastrarLivro(Livro livro) {
        boolean existe = livros.stream().anyMatch(l -> l.getIsbn().equals(livro.getIsbn()));
        if (!existe) {
            livros.add(livro);
            System.out.println("✅ Livro cadastrado: " + livro.getTitulo());
        } else {
            System.out.println("❌ Livro com ISBN " + livro.getIsbn() + " já cadastrado.");
        }
    }

    public void cadastrarUsuario(Usuario usuario) {
        boolean existe = usuarios.stream().anyMatch(u -> u.getMatricula().equals(usuario.getMatricula()));
        if (!existe) {
            usuarios.add(usuario);
            System.out.println("✅ Usuário cadastrado: " + usuario.getNome());
        } else {
            System.out.println("❌ Usuário com Matrícula " + usuario.getMatricula() + " já cadastrado.");
        }
    }

    // --- Métodos de Busca (Helpers) ---

    public Optional<Livro> buscarLivroPorIsbn(String isbn) {
        return livros.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }

    public Optional<Usuario> buscarUsuarioPorMatricula(String matricula) {
        return usuarios.stream()
                .filter(u -> u.getMatricula().equals(matricula))
                .findFirst();
    }

    // --- Métodos de Empréstimo e Devolução ---

    public boolean realizarEmprestimo(String isbnLivro, String matriculaUsuario) {
        Optional<Livro> optLivro = buscarLivroPorIsbn(isbnLivro);
        Optional<Usuario> optUsuario = buscarUsuarioPorMatricula(matriculaUsuario);

        if (optLivro.isEmpty() || optUsuario.isEmpty()) {
            System.out.println("❌ Livro ou Usuário não encontrado.");
            return false;
        }

        Livro livro = optLivro.get();
        Usuario usuario = optUsuario.get();

        if (!livro.isDisponivel()) {
            System.out.println("❌ Livro: " + livro.getTitulo() + " está indisponível.");
            return false;
        }

        long qtdEmprestimos = emprestimosAtivos.stream()
                .filter(e -> e.getUsuario().getMatricula().equals(matriculaUsuario))
                .count();

        if (qtdEmprestimos >= usuario.getLimiteEmprestimos()) {
            System.out.println("❌ Usuário " + usuario.getNome() + " atingiu o limite de " + usuario.getLimiteEmprestimos() + " empréstimos.");
            return false;
        }

        Emprestimo novoEmprestimo = new Emprestimo(livro, usuario, LocalDate.now());
        emprestimosAtivos.add(novoEmprestimo);
        livro.setDisponivel(false);

        System.out.println("🎉 Empréstimo realizado com sucesso! Devolução prevista: " + novoEmprestimo.getDataPrevistaDevolucao());
        return true;
    }

    public boolean realizarDevolucao(String isbnLivro, String matriculaUsuario) {
        Optional<Livro> optLivro = buscarLivroPorIsbn(isbnLivro);
        if (optLivro.isEmpty()) {
            System.out.println("❌ Livro não encontrado.");
            return false;
        }

        Livro livro = optLivro.get();

        // Busca o empréstimo ativo
        Optional<Emprestimo> optEmprestimo = emprestimosAtivos.stream()
                .filter(e -> e.getLivro().getIsbn().equals(isbnLivro) && e.getUsuario().getMatricula().equals(matriculaUsuario))
                .findFirst();

        if (optEmprestimo.isEmpty()) {
            System.out.println("❌ Empréstimo ativo não encontrado para este livro e usuário.");
            return false;
        }

        emprestimosAtivos.remove(optEmprestimo.get());
        livro.setDisponivel(true);

        LocalDate hoje = LocalDate.now();
        if (hoje.isAfter(optEmprestimo.get().getDataPrevistaDevolucao())) {
            System.out.println("⚠️ Devolução com Atraso! Data prevista: " + optEmprestimo.get().getDataPrevistaDevolucao());
        } else {
            System.out.println("✅ Devolução realizada com sucesso.");
        }

        return true;
    }

    public void listarEmprestimosAtivos() {
        System.out.println("\n--- 📖 Empréstimos Ativos (" + emprestimosAtivos.size() + ") ---");
        emprestimosAtivos.forEach(System.out::println);
        if (emprestimosAtivos.isEmpty()) {
            System.out.println("Nenhum empréstimo ativo.");
        }
        System.out.println("----------------------------------------");
    }
}