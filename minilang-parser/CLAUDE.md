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

## Decisões em aberto (o enunciado deixa para o aluno)

- Quais são as **palavras-chave de início e de término** (não estão na lista de reservadas).
- A **gramática** em si: forma de uma declaração de variável (com ou sem atribuição?), o que pode ir na condição do `if`, o que pode ir dentro dos blocos, se o `else` é obrigatório.
- De onde vêm os tokens: o enunciado pede que o `Parser` **receba** a lista de tokens. Decidir se haverá também um analisador léxico (que transforma o texto em tokens) ou se os tokens serão montados à mão no `Main`. Para informar linha/coluna, cada token precisa carregar a sua posição.
- `while` está nas palavras reservadas, mas a estrutura só prevê um bloco `if-else`.
- Como sinalizar o erro (ele ainda não viu exceções).

## Organização (`src/`)

Vazio — projeto começando em 14/10.

## Como rodar

Da pasta `minilang-parser`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
