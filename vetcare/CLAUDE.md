# vetcare

Trabalho final da disciplina de POO: **VetCare**, sistema de gestão de clínica veterinária em Java 17, de console, sem banco de dados. A proposta (apresentação de 16 slides) já foi aprovada.

**A especificação completa está em [`ESPECIFICACAO.md`](ESPECIFICACAO.md). Ler inteira antes de começar.** Ela descreve as classes, os relacionamentos, o UML corrigido, a demonstração, os testes, a documentação e o checklist final, e já corrige os erros que a apresentação da proposta tinha.

## Situação

- 13/11: pasta criada com a especificação; etapa 1 começada (ver "Andamento").
- O trabalho segue as **etapas da seção 9** da especificação, parando ao fim de cada uma até ele dizer "pode seguir".

## Modo de trabalho (decidido por ele em 13/11)

**Modo aprendizado**, como nos outros projetos: ele escreve o código, o Claude guia, explica e revisa (regras do `CLAUDE.md` da raiz). As etapas da seção 9 da especificação servem de roteiro, uma por vez; o Claude não implementa as etapas por ele.

**Regra da especificação que ele RETIROU (13/11) — não cobrar:** comentários marcados `// [POO] ...` (seção 2). Vale também para o checklist da seção 12 e para o README.

**Javadoc: VALE** (ele chegou a retirar e voltou atrás no mesmo dia, depois de ver o que é). Javadoc curto em classes e métodos públicos, como a seção 2 pede.

## Andamento

- **Etapa 1 CONCLUÍDA (13/11):** `StatusAtendimento` (enum), `DadoInvalidoException` e `OperacaoInvalidaException` (herdam de `RuntimeException`), interfaces `Pagavel` e `Agendavel`, `Formatador` (construtor `private`, métodos `static`; `moeda` → "R$ 150,00", `dataHora` → "dd/MM/yyyy HH:mm", `data` → "dd/MM/yyyy", nulo → "sem data"). Compila e foi testado. **Próximo: etapa 2 (Pessoa, Tutor, Veterinario).**
- Conceitos já explicados na etapa 1: pacote (`package` + pasta) e `import`; por que as exceções são *unchecked* (quem chama não é obrigado a `try/catch`; só o `Main`/menu captura); como escrever uma interface (métodos sem corpo, já `public` e abstratos; quem implementa usa `implements` + `@Override`, e esquecer um método não compila); `static` (método da classe, chamado sem objeto) e construtor `private` em classe utilitária; `NumberFormat`/`Locale`; `DateTimeFormatter` e as armadilhas `MM` × `mm` e `dd` × `DD` (dia do ano: 317); `throw` precisa de `new`; texto entre aspas × código (`pt-BR` sem aspas virou subtração).
- **Bug escondido para a etapa 7 (testes):** o `NumberFormat` pt-BR põe um espaço inquebrável (caractere 160) depois do `R$`. Comparar com `"R$ 150,00"` digitado dá `false`. Já avisado a ele.
- Ambiente: módulo `vetcare.iml` com `src` como fonte. O pacote foi criado por engano como `vercare` e corrigido para `vetcare`.

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
