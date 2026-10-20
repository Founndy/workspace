# MiniLang Parser

Analisador sintático (*parser*) em Java para a **MiniLang**, uma linguagem imperativa simplificada. Projeto desenvolvido como **trabalho acadêmico** de faculdade, com o objetivo de praticar o funcionamento das primeiras etapas de um compilador.

O programa recebe o código-fonte de um programa MiniLang e responde se ele está **sintaticamente correto**. Quando não está, informa **a linha, a coluna e o que era esperado** naquele ponto.

```
Programa 1 -> Programa valido!
Programa 2 -> linha 3, coluna 5: esperado ';', encontrado 'if'
Programa 3 -> linha 3, coluna 15: caractere invalido '@'
```

## Requisitos da atividade

- Implementar, em Java, um analisador sintático para uma linguagem imperativa simplificada.
- Definir a estrutura da linguagem como uma sequência de declarações e comandos, seguindo uma gramática.
- Tratar erros sintáticos de forma clara, informando **linha, coluna e uma mensagem descritiva**.
- Reconhecer os tokens básicos:
  - palavras reservadas: `int`, `double`, `if`, `else`, `while`;
  - símbolos: `(` `)` `{` `}` `;` `=` `+` `-` `*` `/` `==` `!=` `<` `>` `<=` `>=`.
- Seguir a estrutura de código:
  ```
  <palavra-chave de início>
      <n variáveis>
      <um único bloco if-else>
  <palavra-chave de término>
  ```
- Implementar uma classe `Parser` que receba uma lista de tokens e tenha métodos seguindo a gramática.

## A linguagem MiniLang

Exemplo de programa válido:

```
start
    int x = 5;
    int y;
    double taxa = 2.5;
    if (x > 3) {
        y = x + 1;
        taxa = taxa * 2 - 1;
    } else {
        y = 0;
    }
end
```

### Gramática

Notação: `"x"` é um texto literal; um nome sem aspas é outra regra; `[ ]` é opcional; `{ }` é zero ou mais vezes; `|` é "ou".

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

Decisões tomadas sobre o que o enunciado deixava em aberto:

- as palavras de início e término são `start` e `end`;
- a declaração aceita as duas formas, com e sem valor (`int x;` e `int x = 5;`);
- o `double` usa ponto como separador decimal (`2.5`);
- o `else` é obrigatório, porque a estrutura pede um bloco *if-else*;
- números inteiros e decimais são o mesmo tipo de token (`NUMERO`).

## Arquitetura

```
texto do programa → Lexer → lista de tokens → Parser → "Programa valido!" ou erro
```

O trabalho é dividido em duas etapas, cada uma com uma responsabilidade:

1. **Análise léxica (`Lexer`)**: lê o texto caractere por caractere e o transforma numa lista de *tokens* (palavras reservadas, nomes, números e símbolos), registrando a linha e a coluna de cada um. Ela não sabe se a ordem dos tokens faz sentido.
2. **Análise sintática (`Parser`)**: percorre a lista de tokens e confere se a sequência segue a gramática.

### Classes

| Classe | Responsabilidade |
|---|---|
| `TipoToken` | enum com os 25 tipos de token, agrupados por categoria |
| `Token` | um token encontrado no código: tipo, texto, linha e coluna (imutável, só getters) |
| `Lexer` | transforma o texto em `List<Token>` |
| `Parser` | valida a lista de tokens contra a gramática |
| `ErroLexico` | exceção para caractere inválido |
| `ErroSintatico` | exceção para token fora do lugar ou programa incompleto |
| `Main` | menu e os 9 programas de teste |

### Métodos e técnicas

**No `Lexer`:**
- um `while` percorre o texto com um cursor (`pos`), mantendo `linha` e `coluna` como **atributos**. Assim, os métodos auxiliares `avancar()` e `avancarLinha()` conseguem alterá-los;
- `espiar()` olha o próximo caractere sem avançar. É o que diferencia `=` de `==`, `<` de `<=`, `>` de `>=` e reconhece o `!=`;
- palavras e números são lidos com um laço interno e recortados com `substring`. As palavras são comparadas com `equals` para separar as reservadas dos nomes de variável (`IDENTIFICADOR`);
- um número aceita um único ponto decimal, e só se houver um dígito depois dele;
- qualquer caractere fora da linguagem lança `ErroLexico`.

**No `Parser`**, com a técnica de **descida recursiva**, em que cada regra da gramática vira um método (`programa()`, `declaracao()`, `ifElse()`, `condicao()`, `bloco()`, `atribuicao()`, `expressao()`, `termo()`, `operador()`):
- `consumir(tipo, descricao)` exige que o token atual seja de um tipo. Se for, avança. Se não for, lança `ErroSintatico` com a linha, a coluna e o texto encontrado. Também detecta quando a lista acaba antes da hora;
- `verificar(tipo)` olha o token atual sem consumi-lo, e é usado nas decisões;
- a tradução da notação para Java segue sempre o mesmo padrão:

| Gramática | Java |
|---|---|
| texto literal | `consumir(...)` |
| outra regra | chamada do método da regra |
| `{ ... }` | `while (verificar(...))` |
| `[ ... ]` | `if (verificar(...))` |
| `a \| b` | `if / else` |

**No `Main`:**
- o método `analisar(String texto)` junta as duas etapas dentro de um `try` / `catch`, com um `catch` para cada tipo de erro;
- o menu oferece três origens para o código: um dos exemplos, um arquivo ou o programa digitado.

### Por que exceções
Um erro pode ser descoberto num método bem "fundo", como `termo()`, chamado por `expressao()`, chamado por `atribuicao()`, e assim por diante. Um `return` sairia só do método atual, e os outros continuariam analisando. A exceção interrompe toda a análise de uma vez e sobe até o `main`, levando a mensagem com a posição do erro.

## Como executar

Requer JDK 21 ou mais recente. Da pasta `minilang-parser`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

```
=== Analisador MiniLang ===
1 - Testar um dos exemplos
2 - Ler de um arquivo
3 - Digitar o programa
0 - Sair
```

Na opção 3, o programa é digitado linha por linha, e uma **linha vazia** encerra a digitação.

## Testes

O `Main` traz 9 programas de exemplo, cada um com um comentário descrevendo o resultado esperado. Os de erro quebram, cada um, uma regra diferente da gramática:

| # | Resultado esperado |
|---|---|
| 1 | válido: usa tudo o que a gramática permite |
| 2 | linha 3, coluna 5: esperado `;`, encontrado `if` |
| 3 | linha 3, coluna 15: caractere inválido `@` (erro léxico) |
| 4 | linha 6, coluna 1: esperado `else`, encontrado `end` |
| 5 | linha 2, coluna 9: esperado um nome de variável, encontrado `while` |
| 6 | linha 3, coluna 10: esperado um operador de comparação, encontrado `)` |
| 7 | linha 4, coluna 17: esperado um nome ou número, encontrado `;` |
| 8 | válido: o menor programa possível (sem declarações e com blocos vazios) |
| 9 | esperado `end`, mas o programa terminou |

## Desafios enfrentados

- **Ler e escrever a gramática.** A notação parece simples, mas os símbolos têm significados precisos. Foi fácil usar `[ ]` como "aqui vai algo" (ele significa "opcional") e `{ }` como agrupador (ele significa "zero ou mais vezes"). O que ajudou foi **testar cada regra** com um texto que deveria passar e outro que deveria falhar.
- **Contar linha e coluna.** Só o `\n` muda de linha; o `;` termina uma instrução, mas não a linha. O token precisa guardar a coluna de onde **começa**, então ela é registrada antes de avançar.
- **Passagem por valor.** Os primeiros métodos auxiliares recebiam `linha` e `coluna` como parâmetro e alteravam apenas cópias. A solução foi tornar o estado do léxico (`pos`, `linha`, `coluna`) **atributos** da classe.
- **Símbolos de dois caracteres.** Exigiram olhar o próximo caractere antes de decidir e avançar duas posições, senão o segundo `=` era lido de novo como um token separado.
- **Laços que não terminam.** Como o cursor só anda dentro de `avancar()`, todo caractere precisa cair em algum caso. Um caractere sem tratamento fazia o laço girar para sempre no mesmo lugar.
- **Comparar textos.** `String` se compara com `equals`, e não com `==`, que só verifica se é o mesmo objeto.
- **Fim inesperado do programa.** Quando falta o `end`, não existe token para apontar o erro. O `consumir` verifica primeiro se a lista acabou e gera uma mensagem própria para esse caso.
- **Exceções.** Foi o primeiro contato com `throw`, `try` e `catch`, e com a diferença entre exceções que o Java obriga a tratar (como `IOException`, na leitura de arquivo) e as que não obriga (`RuntimeException`, base de `ErroLexico` e `ErroSintatico`).

## Limitações

- O analisador faz apenas a verificação **sintática**: não confere tipos (como `double` recebendo um inteiro), nem se uma variável foi declarada antes de ser usada.
- A gramática não prevê números negativos (`-5`) nem parênteses dentro de expressões (`(1 + 2) * 3`).
- `while` é reconhecido como palavra reservada, mas a estrutura definida no enunciado tem apenas um bloco `if-else`, então ele não é aceito como comando.
- Por seguir o enunciado, o programa tem um único `if-else`, sem `if` aninhado.
