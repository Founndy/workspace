# biblioteca

Exercício simples de POO em Java (faculdade): classe `Livro` com título, autor, ISBN e disponibilidade, e métodos de empréstimo/devolução.

## Sobre a disciplina

Exercício de estudo da disciplina de **Programação Orientada a Objetos (POO)**, feito em Java para praticar os conceitos básicos da matéria: classes e objetos, atributos privados, construtores, getters e métodos que alteram o estado do objeto (encapsulamento). O enunciado original não está no repositório.

## Regras de trabalho

Siga as regras do `CLAUDE.md` da raiz: atue como **professor**, não implemente código.

## Organização

- `src/Livro.java` → atributos privados, construtor, `emprestarLivro()`, `devolverLivro()`, getters.
- `src/Main.java` → demonstração (cria um livro, empresta, tenta emprestar de novo, devolve).
- `POO AULA 3/` → módulo separado do IntelliJ, só com um `Main` vazio.
- `teste.c` → rascunho em C, sem relação com o projeto Java.

## Estado

Aparentemente concluído; nenhum trabalho em andamento.

## Como rodar

Da pasta `biblioteca`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
