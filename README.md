# 📚 Sistema de Gerenciamento de Biblioteca (Java + Maven)

Projeto desenvolvido como parte da disciplina de Programação II. 

---

## ✨ Requisitos!

Este projeto demonstra a aplicação dos seguintes conceitos fundamentais:

| Requisito | Status | Observações |
| :--- | :--- | :--- |
| **POO: Herança** | ✅ | Classes `Aluno` e `Professor` herdam da classe base `Usuario`. |
| **POO: Polimorfismo** | ✅ | Implementação polimórfica do método `getLimiteEmprestimos()` em `Aluno` e `Professor`. |
| **POO: Encapsulamento**| ✅ | Uso de atributos `private` e métodos `public` (Getters/Setters) em todas as classes de modelo. |
| **Funcionalidade** | ✅ | Gerenciamento de cadastro, empréstimo e devolução de Livros. |
| **Persistência** | ✅ | Os dados são salvos em arquivo (`dados_biblioteca.ser`) usando **Serialização de Objetos** e são carregados ao iniciar. |

---

## 🛠️ Como Executar o Sistema

O sistema possui uma interface de linha de comando (CLI) interativa.

1.  **Requisitos:** Java (JDK 18+) e Apache Maven.
2.  **IDE:** Desenvolvido e testado no **IntelliJ IDEA**.
3.  **Execução:**
    * Abra o projeto no IntelliJ.
    * Execute o método `main` na classe: `com.biblioteca.main.SistemaBibliotecaApp`.

**Instruções de Uso:**
Ao iniciar, o sistema exibirá um menu. O usuário pode digitar as opções (1 a 5) para interagir com o cadastro de livros, usuários, e o controle de empréstimos.

---

## 📂 Estrutura do Código

* **`com.biblioteca.model`**: Contém as classes de domínio (`Livro`, `Usuario`, `Aluno`, `Professor`, `Emprestimo`).
* **`com.biblioteca.service`**: Contém a lógica de negócio principal (`GerenciadorBiblioteca`), responsável por todas as regras e persistência de dados.
* **`com.biblioteca.main`**: Contém a classe principal (`SistemaBibliotecaApp`) que gerencia a interface do menu (CLI).

**Autor:** Karine Simões, A linda.
