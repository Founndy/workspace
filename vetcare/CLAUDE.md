# vetcare

Trabalho final da disciplina de POO: **VetCare**, sistema de gestão de clínica veterinária em Java 17, de console, sem banco de dados. A proposta (apresentação de 16 slides) já foi aprovada.

**A especificação completa está em [`ESPECIFICACAO.md`](ESPECIFICACAO.md). Ler inteira antes de começar.** Ela descreve as classes, os relacionamentos, o UML corrigido, a demonstração, os testes, a documentação e o checklist final, e já corrige os erros que a apresentação da proposta tinha.

## Situação

- 13/11: pasta criada só com a especificação. Nenhum código ainda.
- O trabalho segue as **etapas da seção 9** da especificação, parando ao fim de cada uma até ele dizer "pode seguir".

## Modo de trabalho (decidido por ele em 13/11)

**Modo aprendizado**, como nos outros projetos: ele escreve o código, o Claude guia, explica e revisa (regras do `CLAUDE.md` da raiz). As etapas da seção 9 da especificação servem de roteiro, uma por vez; o Claude não implementa as etapas por ele.

## Estrutura prevista (seção 4)

```
vetcare/
├── README.md
├── docs/            UML (.mmd, .puml, .png), roteiro e ajustes nos slides
└── src/vetcare/
    ├── app/         Main, MenuConsole, TestesVetCare
    ├── contrato/    Pagavel, Agendavel
    ├── excecao/     DadoInvalidoException, OperacaoInvalidaException
    ├── modelo/      Pessoa, Tutor, Veterinario, Animal, Cachorro, Gato, Prontuario,
    │                Atendimento, Consulta, Vacinacao, Cirurgia, StatusAtendimento, VendaProduto
    ├── servico/     Clinica
    └── util/        Formatador
```

## Como compilar (da pasta `vetcare`)

```
javac -encoding UTF-8 -d out -sourcepath src src/vetcare/app/Main.java src/vetcare/app/TestesVetCare.java
java -cp out vetcare.app.Main
java -cp out vetcare.app.TestesVetCare
```
