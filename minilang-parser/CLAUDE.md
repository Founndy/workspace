# minilang-parser

Analisador sintático (parser) em Java para uma linguagem imperativa simplificada, a **MiniLang**. Trabalho da faculdade e **sessão de aprendizado**: siga as regras do `CLAUDE.md` da raiz.

**Ritmo lento (pedido dele em 14/10):** voltar ao modelo do começo do aeroporto — ele escreve o código; guiar com perguntas, explicar conceitos com exemplos fora do domínio e revisar. Sem esqueletos prontos do próprio parser. Continua valendo a exceção da raiz: erros de digitação e de sintaxe pequenos (chave, `;`, parêntese) podem ser corrigidos direto, só avisando.

## Enunciado da atividade (transcrição fiel do PDF, que não foi publicado)

**Analisador Sintático**

Você deverá implementar, em Java, um analisador sintático (parser) para uma linguagem imperativa simplificada, chamada aqui de MiniLang.

O objetivo é validar se um código-fonte está sintaticamente correto, de acordo com uma gramática pré-definida. Para isso, defina a estrutura da linguagem que deverá ser composta por uma sequência de declarações e comandos.

**Objetivos de Aprendizagem:**
- Compreender o papel do analisador sintático em um compilador.
- Ler e interpretar uma gramática.
- Tratar erros sintáticos de forma clara, informando linha/coluna e mensagem descritiva.

**Tokens básicos:**
- Palavras reservadas: `int`, `double`, `if`, `else`, `while`
- Símbolos: `(`, `)`, `{`, `}`, `;`, `=`, `+`, `-`, `*`, `/`, `==`, `!=`, `<`, `>`, `<=`, `>=`

**Estrutura de um Código:**
```
<Palavra chave de início>
    <n variáveis>
    <um único bloco if-else>
<Palavra chave de término do código>
```

**Nesse sentido, implemente uma classe Parser em Java que:**
- Receba como entrada uma lista de tokens ou um iterador de tokens.
- Implemente métodos seguindo a gramática.

## Arquitetura combinada

`texto → Lexer → List<Token> → Parser → "correto" ou erro com linha/coluna`

- `TipoToken` (enum), `Token` (tipo, texto, linha, coluna), `Lexer` (texto → `List<Token>`), `Parser` (descida recursiva: **cada regra da gramática vira um método**), `Main` com programas válidos e com erro.
- Ordem: gramática no papel → `TipoToken`/`Token` → `Lexer` → `Parser` → `Main`.

## Decisões tomadas

- **Com analisador léxico** (19/10): o programa é escrito como texto e linha/coluna saem de verdade.
- **Erros com exceção (`throw`)** (19/10): ele ainda não viu exceções e quer aprender aqui — explicar do zero quando chegar no parser.

- **Início/fim do programa: `start` e `end`** (19/10).
- **Declaração com ou sem valor**: `int x;` e `int x = 5;` (19/10). `double` aceita número com ponto (`3.5`).
- **Um tipo de token só para números** (`NUMERO` serve para `5` e `2.5`) (19/10).
- **Condição do `if`** com comparação (ex.: `x > 3`) e **atribuições** dentro dos blocos (ex.: `x = x + 1;`) (19/10).

## Gramática (escrita por ele em 19/10, revisada)

Notação: `"x"` = texto literal; nome sem aspas = outra regra; `[ ]` = opcional; `{ }` = zero ou mais; `|` = ou.

```
programa     = "start" { declaracao } ifElse "end"
declaracao   = tipo nome [ "=" valor ] ";"
tipo         = "int" | "double"
valor        = expressao
ifElse       = "if" "(" condicao ")" bloco "else" bloco
bloco        = "{" { atribuicao } "}"
atribuicao   = nome "=" expressao ";"
condicao     = expressao operador expressao
operador     = "==" | "!=" | "<" | ">" | "<=" | ">="
expressao    = termo { opAritmetico termo }
opAritmetico = "+" | "-" | "*" | "/"
termo        = nome | numero
```
Gramática sintática **completa**. Faltam só as regras **léxicas** de `nome` e `numero` (quais caracteres formam o token) — vão para o `Lexer`, não para o `Parser`.
- O parser não precisa de precedência (`*` antes de `+`): só valida a forma.
- Ele acertou o teste de leitura (programa sem `;` na declaração é inválido). Explicado que o erro é anunciado na posição do **próximo** token (o `if`), como no Java.
- `else` tratado como **obrigatório** (o enunciado fala em "bloco if-else").
- Dificuldades que apareceram: usou `[ ]` como "aqui vai algo" (é "opcional") e `{ }` como agrupador (é "zero ou mais" — `termo = {nome}|{numero}`, `expressao = {termo}`; ajudou testar a regra com textos que devem passar e falhar); escreveu `ifelse(condição)` (confusão com `else if` — o `else` não tem condição); pôs `"x"`/`"y"` entre aspas na condição (aspas = literal exato); `<>` e `||` não estão na lista de símbolos; misturou a regra `tipo` com a `declaracao`. A `atribuicao` saiu certa de primeira.

## Decisões em aberto (o enunciado deixa para o aluno)
- `while` está nas palavras reservadas, mas a estrutura só prevê um bloco `if-else`.

## Organização (`src/`)

- `TipoToken` (19/10) → enum com os 25 tipos: `START`, `END`, as 5 reservadas (`INT`, `DOUBLE`, `IF`, `ELSE`, `WHILE`), os 16 símbolos com nomes descritivos (`ABRE_PARENTESES`, `PONTO_VIRGULA`, `SINAL_RECEBE`, `COMPARATIVO_MAIOR_IGUAL`...), `NUMERO` e `IDENTIFICADOR`. Ele tinha posto `VIRGULA` (removida, não está no enunciado) e `VARIAVEIS` (virou `IDENTIFICADOR`: um token é um item, e serve para qualquer nome).
- `Token` (19/10) → `TipoToken tipo`, `String texto`, `int linha`, `int coluna`; construtor com os quatro, só getters (decisão dele: token não muda depois de criado). Primeira versão tinha `tipo` como `String` — trocado pelo enum, mesmo motivo do `StatusVoo`.
- Próximo: `Lexer` (texto → `List<Token>`). Pergunta deixada para o léxico: `int while = 5;` deve ser aceito? (palavra reservada não pode ser nome).

Didática: em 19/10 ele disse que os exercícios de "pensar" antes de ver o sistema funcionando estavam causando confusão. O que destravou foi mostrar o **sistema rodando de ponta a ponta** com um exemplo concreto (texto → tabela de tokens → parser andando com o "dedo" → `throw` → `catch` no `Main`). Antes de cada peça nova, mostrar onde ela entra nesse fluxo e um exemplo concreto do que ela produz.

## Como rodar

Da pasta `minilang-parser`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
