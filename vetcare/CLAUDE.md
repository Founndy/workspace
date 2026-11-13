# vetcare

Trabalho final da disciplina de POO: **VetCare**, sistema de gestão de clínica veterinária em Java 17, de console, sem banco de dados. A proposta (apresentação de 16 slides) já foi aprovada.

**A especificação completa está em [`ESPECIFICACAO.md`](ESPECIFICACAO.md). Ler inteira antes de começar.** Ela descreve as classes, os relacionamentos, o UML corrigido, a demonstração, os testes, a documentação e o checklist final, e já corrige os erros que a apresentação da proposta tinha.

## Situação

- 13/11: pasta criada só com a especificação. Nenhum código ainda.
- O trabalho segue as **etapas da seção 9** da especificação, parando ao fim de cada uma até ele dizer "pode seguir".

## Decidir antes da Etapa 1

A especificação pede que o projeto seja implementado por etapas, mas a regra da raiz deste repositório é de sessão de aprendizado (ele codifica, o Claude guia). Perguntar a ele qual modo vale para este trabalho final antes de escrever qualquer código.

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
