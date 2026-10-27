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

**Alteração do enunciado feita pelo professor (informada por ele em 27/10):** o bloco `if`-`else` **deixou de ser obrigatório**. Um programa só com `start` ... `end` é válido. Continua valendo "um único" quando o `if`-`else` aparece.

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
- `Lexer`, pedaço 1 pronto: atributos `programa`, `pos`, `linha = 1`, `coluna = 1`; `List<Token> analisar()` com `while (pos < programa.length())`, `charAt` dentro do loop, `else if` para `\n` (`avancarLinha()`: linha+1, coluna=1), espaço/tab (`avancar()`: coluna+1) e os 9 símbolos de um caractere (token criado **antes** de avançar); `pos++` no fim do loop. Teste no `main` com `"( ;\n)"` → `1:1`, `1:3`, `2:1` ✅. Conceitos que apareceram: `char` e aspas simples, `'\n'` com barra invertida, `charAt` precisa estar dentro do loop, **passagem por valor** (método que recebe `int` mexe numa cópia — exemplo `dobrar(x)`), por isso o estado do léxico virou atributo; o `;` não muda de linha (só o `\n`). Caractere desconhecido ainda só faz `pos++` (coluna não anda) — resolver no próximo pedaço.
- `Lexer`, pedaço 2 pronto (19/10): `char espiar()` (olha `pos + 1` com guarda de limite, devolve `'\0'` no fim); `pos++` passou para dentro de `avancar()`/`avancarLinha()`; casos `=`, `<`, `>`, `!` (se o próximo é `=`, token de dois caracteres e **dois** `avancar()`); `!` sozinho só avança (vira erro com exceção depois); `else { avancar(); }` **provisório** no fim para caractere sem tratamento (sem ele o loop trava). Teste `"= == != < <= > >="` → 7 tokens nas colunas 1, 3, 6, 9, 11, 14, 16 ✅. Ele preferiu manter a escada de `else if` (oferecido auxiliar `adicionar(tipo, texto)` + `switch` de seta, recusado).
- `Lexer`, pedaço 3 pronto (20/10): `else if (Character.isLetter(c))` → "foto" do início (`cP`, `colunaCP`, locais), `while (pos < programa.length() && Character.isLetterOrDigit(programa.charAt(pos)))` com `avancar()`, `String palavra = programa.substring(cP, pos)`, escada `if/else if` com `palavra.equals(...)` para as 7 reservadas e `else` → `IDENTIFICADOR`; token com `linha` e `colunaCP`. Teste `"start int while taxa x1 end"` → 6 tokens certos ✅. Erros que apareceram no caminho: bloco das letras dentro do caso do `!`; `while` testando o `c` antigo (lia o texto todo); `substring` descartado; `programa.equals` em vez de `palavra.equals`; `cP` no lugar da linha; `pos += palavra.length()` avançando em dobro; bloco inteiro copiado para cada reservada (só a comparação se repete); `"START"` maiúsculo (`equals` diferencia maiúsculas).
- `Lexer`, pedaço 4 pronto (20/10): `else if (Character.isDigit(c))` → foto (`cP`, `colunaCP`) e `boolean temPonto = false` **antes** do `while`; um `while` com `char atual`: dígito → avança; `.` sem ponto anterior e com dígito depois (`espiar()`) → `temPonto = true` e avança; senão `break`. Depois: `substring` e um token `NUMERO`. Teste `"5 2.5 10 x1 3.14"` ✅. Conceito novo: `break`. Ele montou as peças certas, mas fora de ordem (foto e `temPonto` dentro do `while`, `while` das letras copiado) — reorganizado com as linhas dele. Main de teste agora imprime também o texto do token.
- `ErroLexico extends RuntimeException` (construtor com `super(mensagem)`) e `Lexer` com `throw` no `else` final e no `!` sozinho: `"linha X, coluna Y: caractere invalido 'c'"`. `main` testa o `programa3` com `try`/`catch (ErroLexico e)` imprimindo `e.getMessage()` → `linha 3, coluna 15: caractere invalido '@'` ✅ (20/10). Explicado: `return` só sai do método atual, exceção sobe até alguém pegar; sem `catch` ela derruba o programa (`Exception in thread "main"`); `getMessage()`; o `for` não roda porque o `analisar()` lança antes de devolver a lista. **`Lexer` completo.**
- `Parser`, pedaço 1 pronto (20/10): `ErroSintatico extends RuntimeException`; atributos `List<Token> tokens` e `int atual = 0` (o dedo); `void consumir(TipoToken esperado, String descricao)` = guarda 1 (`atual >= tokens.size()` → "esperado X, mas o programa terminou" — resolve o programa 9) + guarda 2 (tipo diferente → "linha L, coluna C: esperado X, encontrado 'texto'") + `atual++`; `programa()` só com `consumir(START)` e `consumir(END)`. `main` agora roda um `String[] testes` em for-each: `Lexer` → `List<Token>` → `Parser.programa()` → "Programa valido!", com `catch` para `ErroLexico` e `ErroSintatico`. Testes `"start end"`, `"end"`, `"start"` ✅. Erros dele no caminho: chamar `analisar()` duas vezes (a 2ª devolve lista vazia, `pos` já no fim) e não chamar `programa()`; parâmetro do `consumir` como `List<Token>`.
- `Parser`, pedaço 2 pronto (20/10): `boolean verificar(TipoToken)` (olha sem consumir; `false` se a lista acabou); `programa()` com `while (verificar(INT) || verificar(DOUBLE)) declaracao();`; `declaracao()` = `if/else` do tipo + `consumir(IDENTIFICADOR, "um nome de variavel")` + `if (verificar(SINAL_RECEBE)) { consumir; valor(); }` + `consumir(PONTO_VIRGULA)`; `valor()` **provisório** = um termo (`if` identificador `else` `consumir(NUMERO, "um nome ou numero")`). Tabela de tradução ensinada: literal → `consumir`, regra → método, `{ }` → `while(verificar)`, `[ ]` → `if(verificar)`, `|` → `if/else`. 4 testes no `main` ✅. Corrigido no caminho: `else if` no `valor()` deixava passar token inválido sem erro (virou `else`).
- `Parser`, pedaço 3 pronto (20/10): `valor()` = `expressao()`; `expressao()` = `termo()` + `while (verificar(MAIS) || MENOS || MULTIPLICA || DIVISAO) { atual++; termo(); }` (`atual++` direto porque o `while` já verificou); `termo()` = nome ou número (era o `valor()` provisório, renomeado). Testes `a + 1 * b - 2` ✅, `1 +;` (1:18) ✅, `1 2;` (1:17, quem reclama é o `declaracao`) ✅.
- `Parser`, pedaço 4 pronto (20/10), feito método a método: `atribuicao()` (nome, `=`, `expressao()`, `;`), `bloco()` (`{`, `while (verificar(IDENTIFICADOR)) atribuicao();`, `}`), `operador()` (6 `verificar` com `||` → `atual++`, senão `consumir(COMPARATIVO_IGUAL, "um operador de comparacao")` só para gerar o erro), `condicao()` (`expressao(); operador(); expressao();`), `ifElse()` (7 linhas na ordem da regra) e `ifElse()` chamado no `programa()` antes do `end`. O `main` roda os 9 programas e **todos batem com os comentários** ✅. **Parser completo — enunciado atendido.**
- Menu pronto (20/10, pedido dele para fazer): `static void analisar(String texto)` no `Main` (o `try`/`catch` do `Lexer` + `Parser`); `while (!opcao.equals("0"))` com o menu impresso e `opcao = Leitor.nextLine()` dentro; `switch` de seta com `String`: 1 = exemplo (`Integer.parseInt` - 1 no array `testes`, `catch (NumberFormatException)`, faixa validada), 2 = arquivo (`Files.readString(Path.of(caminho))`, `catch (IOException)` — exceção verificada, obrigatória), 3 = digitar (lê linhas até uma vazia, junta com `"\n"`), 0 = sair. Testado com entrada simulada nas 3 opções e nos erros ✅. Conceitos explicados: `static` (método da classe, chamado pelo `main` sem objeto), `nextLine()` em vez de `nextInt()`.

**Projeto concluído em 20/10.** Gramática, `Lexer`, `Parser` com erros de linha/coluna, os 9 programas de teste batendo com os comentários e o menu. `README.md` escrito para o GitHub (requisitos, gramática, arquitetura, métodos, testes, desafios, limitações).

**Pendências da auditoria de 20/10 (confirmadas em teste, ainda NÃO corrigidas — perguntar a ele antes):**
1. `\r` do Windows (`\r\n`) cai no erro léxico ("caractere invalido ''", invisível) → a opção 2 do menu falha com arquivo salvo no Bloco de Notas/IntelliJ. Correção: tratar `'\r'` como espaço.
2. Tokens depois do `end` são aceitos (`... end x = 1;` → válido). **Regra decidida por ele (reconfirmada em 27/10, não é ponto em aberto): o `end` marca o fim do arquivo. Qualquer token depois dele torna o programa inválido.** Correção: após `consumir(END)`, se `atual < tokens.size()`, lançar "esperado o fim do programa, encontrado '...'". Caso testado em 27/10: um `INT X = 0;` solto depois do `end` passa hoje — e repare que `INT` maiúsculo nem é palavra reservada (o léxico diferencia maiúsculas), vira identificador.
3. Caminho com aspas ("Copiar como caminho" do Windows) → `InvalidPathException` (não é `IOException`) derruba o menu. Correção: tirar as aspas do caminho ou pegar a exceção.

**Levantado por ele em 27/10 (confirmado em teste, ainda NÃO corrigido):**
4. **Mudança de requisito, pedida pelo professor:** o programa deve ser válido **só com `start` ... `end`**; o bloco `if`-`else` deixa de ser obrigatório (já anotado na seção do enunciado). Hoje `"start end"` e `"start int y; end"` são recusados com "esperado 'if', encontrado 'end'". Correção: no `programa()`, trocar a chamada fixa de `ifElse()` por `if (verificar(TipoToken.IF)) ifElse();` — na tabela de tradução dele, `[ ]` (opcional) vira `if(verificar)`. Atualizar junto a linha `programa` da gramática escrita neste arquivo, para `[ ifElse ]`.
5. **`if (x > 3)` com `x` nunca declarado é aceito** (ele classificou como erro, com o programa de exemplo das declarações `int y; double taxa = 2.5;`). Ressalva a discutir **antes** de corrigir: verificar se uma variável foi declarada é **análise semântica**, não sintática — exigiria uma tabela de símbolos (guardar os nomes declarados e consultar em cada uso). O enunciado pede "analisador sintático (parser)" e "validar se um código-fonte está sintaticamente correto", então isso **extrapola o escopo pedido**. Decisão dele: implementar mesmo assim (lista de nomes declarados + erro no uso de nome desconhecido) ou registrar como limitação conhecida, junto das outras já listadas.

Menores corrigidos em 20/10: `analisar()` do `Lexer` agora zera `pos`/`linha`/`coluna` no início (antes, a 2ª chamada devolvia lista vazia); removidos o `= new ArrayList<>()` e o import sem uso do `Parser`. Fica como está (decisão): nomes com acento são aceitos (`Character.isLetter` é Unicode). Limites da gramática (decisão, não bug): sem negativos, sem parênteses em expressão, sem `if` aninhado, `while` não aceito como comando.
- Histórico do pedaço 3 — antes: **Parou aqui em 19/10 — próximo: letras.** Já explicado a ele: guardar `pos`/`coluna` do início, `while` interno com `pos < length()` antes do `charAt` e `Character.isLetterOrDigit`, recortar com `substring(inicio, pos)`, comparar `String` com `.equals()` (não `==`), reservadas × `IDENTIFICADOR`, token com a coluna do início. Teste combinado: `"start int while taxa x1 end"` → `START 1:1`, `INT 1:7`, `WHILE 1:11`, `IDENTIFICADOR 1:17`, `IDENTIFICADOR 1:22`, `END 1:25`. Depois: números (com ponto), aí o erro léxico com exceção.
- Histórico: o `Lexer` começou só com `private String programa` + construtor. Ele tinha posto um `Scanner(System.in)` com `nextLine()` como inicializador de atributo — removido: rodava antes do construtor (travava esperando teclado), lia uma linha só, e ler a entrada não é responsabilidade do léxico (quem decide a origem do texto é o `Main`).
- `Main` (19/10, pedido dele) → 9 programas de teste em **bloco de texto** (`"""`), cada um com comentário do resultado esperado (linha/coluna conferidas): 1 e 8 passam; 2 falta `;` (3:5, no `if`); 3 erro léxico `@` (3:15); 4 falta `else` (6:1, no `end`); 5 `while` como nome (2:9); 6 condição sem operador (3:10, no `)`); 7 expressão terminando em `+` (4:17, no `;`); 9 falta `end` (fim dos tokens). Hoje ficam no array `testes`, usado pela opção 1 do menu.
- **Último passo do projeto (pedido dele):** menu simples no `Main` para escolher entre usar um dos exemplos, ler de um arquivo ou digitar o programa na mão. Só no final.
- Próximo: `Lexer` (texto → `List<Token>`), em pedaços: 1º espaços/quebras de linha e símbolos de um caractere, contando linha/coluna; depois símbolos de dois caracteres, palavras (reservadas × `IDENTIFICADOR`) e números. O programa 9 vai exigir decidir como o parser percebe o fim dos tokens. Pergunta deixada para o léxico: `int while = 5;` deve ser aceito? (palavra reservada não pode ser nome).

Didática: em 19/10 ele disse que os exercícios de "pensar" antes de ver o sistema funcionando estavam causando confusão. O que destravou foi mostrar o **sistema rodando de ponta a ponta** com um exemplo concreto (texto → tabela de tokens → parser andando com o "dedo" → `throw` → `catch` no `Main`). Antes de cada peça nova, mostrar onde ela entra nesse fluxo e um exemplo concreto do que ela produz.

## Como rodar

Da pasta `minilang-parser`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
