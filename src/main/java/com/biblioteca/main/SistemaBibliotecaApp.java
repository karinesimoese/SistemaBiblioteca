package com.biblioteca.main;

import com.biblioteca.model.Aluno;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Professor;
import com.biblioteca.service.GerenciadorBiblioteca;

import java.util.Scanner; // Import necessário para a leitura do teclado

public class SistemaBibliotecaApp {

    // Objetos estáticos para uso em todos os métodos
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorBiblioteca gerenciador = new GerenciadorBiblioteca();

    public static void main(String[] args) {

        // Carrega dados salvos ao iniciar o sistema (Persistência)
        gerenciador.carregarDados();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();

            try {
                // Lê a opção e trata entradas que não são números
                String input = scanner.nextLine();
                if (input.isEmpty()) continue; // Ignora ENTER vazio

                opcao = Integer.parseInt(input);
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Digite um número correspondente à opção.");
            }
        }

        // Salva todos os dados antes de encerrar o sistema (Persistência)
        gerenciador.salvarDados();
        System.out.println("Sistema encerrado. Obrigado!");
        scanner.close();
    }

    // --- Métodos de Interface (Menu) ---

    private static void exibirMenu() {
        System.out.println("\n--- 📚 MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar Livro");
        System.out.println("2. Cadastrar Usuário");
        System.out.println("3. Realizar Empréstimo");
        System.out.println("4. Realizar Devolução");
        System.out.println("5. Listar Empréstimos Ativos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarLivro();
                break;
            case 2:
                cadastrarUsuario();
                break;
            case 3:
                fazerEmprestimo();
                break;
            case 4:
                fazerDevolucao();
                break;
            case 5:
                gerenciador.listarEmprestimosAtivos();
                break;
            case 0:
                // Sai do loop
                break;
            default:
                System.out.println("Opção desconhecida. Tente novamente.");
        }
    }

    // --- Métodos de Interação de Cadastro ---

    private static void cadastrarLivro() {
        System.out.println("\n--- Cadastro de Livro ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        Livro novoLivro = new Livro(titulo, autor, isbn);
        gerenciador.cadastrarLivro(novoLivro);
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de Usuário ---");
        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Tipo (1 - Aluno, 2 - Professor): ");
        String tipoStr = scanner.nextLine();

        if (tipoStr.equals("1")) {
            gerenciador.cadastrarUsuario(new Aluno(nome, matricula));
        } else if (tipoStr.equals("2")) {
            gerenciador.cadastrarUsuario(new Professor(nome, matricula));
        } else {
            System.out.println("❌ Tipo de usuário inválido.");
        }
    }

    private static void fazerEmprestimo() {
        System.out.println("\n--- Realizar Empréstimo ---");
        System.out.print("ISBN do Livro: ");
        String isbn = scanner.nextLine();
        System.out.print("Matrícula do Usuário: ");
        String matricula = scanner.nextLine();

        gerenciador.realizarEmprestimo(isbn, matricula);
    }

    private static void fazerDevolucao() {
        System.out.println("\n--- Realizar Devolução ---");
        System.out.print("ISBN do Livro para Devolução: ");
        String isbn = scanner.nextLine();
        System.out.print("Matrícula do Usuário que Devolveu: ");
        String matricula = scanner.nextLine();

        gerenciador.realizarDevolucao(isbn, matricula);
    }
}