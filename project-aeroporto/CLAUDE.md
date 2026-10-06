# project-aeroporto

Exercício de POO em Java: sistema de operação de voos em um aeroporto. É trabalho da faculdade e **sessão de aprendizado**: siga as regras de trabalho do `CLAUDE.md` da raiz (atuar como professor, não implementar código).

**Ritmo acelerado (pedido dele em 01/10):** pode dar dicas mais diretas e mostrar estruturas/esqueletos funcionais do próprio domínio (assinaturas, `switch`, interface da estratégia, ordem das guardas), deixando o preenchimento das regras para ele. Continua sem implementar o método inteiro por ele.

## Enunciado da atividade (transcrição fiel do PDF, que não foi publicado)

**Tema:** Operação de voos em um aeroporto
**Pilares-alvo:** Abstração, Encapsulamento, Herança e Polimorfismo
**Entrega:** Diagrama de Classes UML + Implementação em Java + Testes

1. Em um aeroporto ocorrem voos (alguns decolam, outros aterrissam).
   - Cada voo utiliza um avião, composto por vários assentos;
   - Cada assento pode estar ocupado por um passageiro;
   - Cada voo possui tripulação obrigatória: 1 piloto, 1 copiloto e ≥ 1 aeromoça(o).

Modele e implemente um sistema que registre voos, aloque avião, atribua tripulação, realize embarque/desembarque, controle assentos e altere o status do voo (planejado, em solo, taxiando, decolado, em rota, em aproximação, pousado, cancelado).

**Requisitos de implementação:**

1. Encapsulamento
   - Atributos privados, acesso via getters/setters com validação (ex.: impedir `null`, limites, formatos).
   - Métodos de negócio em `Voo`/`Aviao` (ex.: `alocarTripulante(Tripulante t)`, `atribuirAssento(Passageiro p, String codigo)`).
2. Herança
   - `Pessoa` abstrata; `Tripulante` abstrata; subclasses concretas `Piloto`, `Copiloto`, `Comissario`.
3. Abstração
   - `OperacaoVoo` com implementações `Decolagem` e `Pouso`.
4. Polimorfismo
   - Invoque `anunciar()` sem conhecer a subclasse.
   - Permita que `Voo` troque de estratégia de operação em tempo de execução.
5. Para a demonstração
   - Um `main` simples que:
     1. cria avião e assentos,
     2. cria voo,
     3. aloca tripulação,
     4. embarca passageiros com assento,
     5. percorre estados do voo,
     6. imprime anúncios polimórficos.

## Organização

Sem pacotes; todos os `.java` ficam na raiz da pasta (o `.iml` marca a raiz como source folder).

- `Pessoa` → base (nome, cpf, `anunciar()`); `Passageiro` (passaporte) e `Tripulante` (titulo) herdam dela.
- `Tripulante` → `Piloto`, `Copiloto`, `Comissario`.
- `Aviao` → assentos (`Passageiro[50]`), `adicionarPessoa`, `adicionarTripulante`.
- `Voo` → atributos, construtor, getters e setters prontos (passos 1 e 2 do plano); falta comportamento (ver "Estado atual").
- `StatusVoo` → enum com os 8 status do enunciado.
- `Main` → `public static void main` tradicional, só um `println` por enquanto.
- `Diagrama Aeroporto.drawio` → diagrama UML do usuário (abre em app.diagrams.net).

## O que já foi feito

Decisões de design discutidas:
- Status do voo como **enum** (`StatusVoo`), não `String` — o compilador barra valores inválidos.
- **Tripulação pertence ao `Voo`**, não ao `Aviao` (o mesmo avião faz voos com equipes diferentes).
- **Assentos/passageiros ficam só no `Aviao`**; `Voo` não deve ter array próprio de passageiros (duplicaria informação).
- `Voo` não deve criar `new Aviao()` por dentro: o avião é **alocado** depois. `nomeVoo` é redundante com o destino.
- `Aeromoca` foi renomeada para `Comissario` (nome do enunciado).
- `Main` passou de sintaxe Java 25 (`void Main()`, `IO.println`) para `public static void main`, para rodar no JDK 21.

Bugs já corrigidos pelo usuário:
- `Pessoa.setNome`: condição agora `nome != null && !nome.isBlank()`.
- `Pessoa`: construtor agora chama `setNome(nome)` / `setCpf(cpf)` (antes ignorava o parâmetro); construtor vazio removido.
- `anunciar()` minúsculo em `Pessoa` e `Tripulante` (o `@Override` pegou a divergência); `setId` → `setTitulo`.
- `Aviao`: `Boolean` → `boolean`; construtor com tripulação removido; assentos tipados como `Passageiro[]` (arrays são covariantes — `Pessoa[]` aceitaria um `Piloto` e só quebraria em runtime com `ArrayStoreException`).
- `Aviao.adicionarPessoa`: sem loop desnecessário, checa limites (`>= 0` e `< length`) antes de acessar o array (curto-circuito do `&&`), impede o mesmo passageiro em dois assentos.
- `Aviao.adicionarTripulante`: `return` ao alocar e mensagem "sem espaço" só depois do loop.

`Voo`, passos 1 e 2 (feitos pelo usuário):
- Atributos `private`: `codVoo`, `aeroSaida`, `aeroEntrada`, `StatusVoo estadoAtual`, `Aviao aviao` (sem `new`, começa `null`), `Tripulante[] tripulante = new Tripulante[10]`.
- `Tripulante[10]` é decisão do usuário: capacidade para mais comissários. A regra do enunciado (1 piloto, 1 copiloto, ≥ 1 comissário) deve ser garantida pelos métodos, não pelo tamanho do array. Não criar classes fora do enunciado (ex.: "administração").
- Construtor recebe código/origem/destino, chama os setters (validação) e começa em `StatusVoo.PLANEJADO`.
- Getters de tudo; setters de código/origem/destino validam `!= null && !isBlank()`; `setEstadoAtual` sem regras.
- Conceitos explicados nesta etapa: uso de enum (tipo vs. nome da variável, `Tipo.VALOR`), modificador de acesso não vale em variável local, `void` não devolve valor (setter já atribui), atributo objeto sem atribuição = `null`.
- `Main`: cria um `Aviao` e um array `Passageiro[15]` (lista para a demonstração, não são os assentos).

## Estado atual e próximos passos

Pendências apontadas no último review (o usuário ainda não corrigiu):
1. `Aviao.adicionarPessoa(null, x)`: o loop de duplicidade compara `null == assentos[i]`, que é `true` num assento vazio → imprime "Passageiro ja alocado!" (mensagem errada) e o `passageiro != null` do `if` virou código morto. Lição: ordem das validações / cláusulas de guarda no topo, uma mensagem por motivo.
2. `Aviao.adicionarTripulante(null)` ainda "aloca" e imprime sucesso.
3. `Pessoa.setCpf` e `Passageiro.setPassaporte` sem validação; `setNome` ignora inválidos em silêncio (discutir: ignorar vs. lançar `IllegalArgumentException`).
4. `Pessoa` e `Tripulante` devem ser `abstract` (enunciado).
5. Tripulação ainda está no `Aviao` — sai de lá quando o `Voo` for feito.

Decisões em aberto no `Voo` (do usuário, sem bug):
- Valor inválido: ignorar em silêncio ou lançar `IllegalArgumentException`? Hoje `new Voo(null, "GRU", "GIG")` cria voo com `codVoo` `null` sem aviso (mesma questão da `Pessoa`).
- `setEstadoAtual` livre (aceita `null`, `CANCELADO` → `EM_ROTA`) vs. método com regras — resolver no passo 5.
- Quais atributos podem mudar depois de criado; métodos sem modificador (package-private) vs. `public`.

Plano do `Voo` (guiar passo a passo; o usuário implementa):
1. ~~Atributos e construtor~~ (feito).
2. ~~Getters/setters com validação~~ (feito, com as decisões em aberto acima).
3. ~~`alocarAviao`~~ (feito: 4 cláusulas de guarda com `return` — já tem avião, status ≠ `PLANEJADO`, `null`, `!isDisponivel()` — nessa ordem para não dar `NullPointerException`; marca `setDisponivel(false)`. `setAviao` removido por ser porta dos fundos. `Aviao` ganhou `isDisponivel()`).
   ~~`alocarTripulante`~~ (feito e testado: guardas `null`/status/duplicado com `return`; piloto e copiloto checados separadamente — no máx. 1 de cada; loops de alocação no formato "achou vaga → aloca, avisa e `return`, tudo dentro do `if`"; mensagem de "sem vagas" depois dos loops). Conceitos que custaram a assentar e foram explicados com a analogia do **estacionamento** (procura vaga → estaciona, avisa e vai embora; "cheio" só depois de olhar todas): `return` encerra o **método inteiro**; chave `}` fechada no lugar errado vira código morto / "unreachable statement"; compilar ≠ funcionar; ler avisos amarelos do IntelliJ. Se ele se perder com estrutura de blocos, voltar a essa analogia.
   Pontos levantados para ele pensar (opcionais, sem bug): comissários podem ocupar as 10 vagas antes do piloto e tornar a tripulação obrigatória impossível; o loop de alocação está duplicado (poderia ficar um só depois dos `if`s).
   ~~Verificação de tripulação completa~~ (feito e testado: `boolean requisitosAlcancado()` — contadores **locais**, conta a partir do array `tripulantes` com `instanceof`, compara só **depois** do loop e devolve a comparação direto com `return`). Conceitos explicados: variável local nasce zerada e morre a cada chamada ("quadro que se apaga"); não guardar contadores como atributos (divergiriam do array); método que responde pergunta devolve `boolean` em vez de imprimir; "missing return statement". Ainda não é usado — será usado na troca de status (passo 5).
   Atenção: quando o Claude edita um arquivo aberto no IntelliJ enquanto o usuário digita, o IntelliJ pode salvar por cima. Pedir para ele não digitar e recarregar (Load File System Changes).
4. ~~Embarque/desembarque~~ (feito e testado). `Voo.embarcar(Passageiro, int assento)`: guarda de avião `null` **antes** de usar `aviao` (senão NPE), guarda de status (`EM_SOLO`), delega a `aviao.adicionarPessoa`. `Voo.desembarcar(int assento)`: mesma forma, mas aceita vários status via `boolean allowDesembarque()` (POUSADO, EM_SOLO, PLANEJADO, CANCELADO) e delega a `aviao.removerPessoa(int)`. `Aviao` ganhou `removerPessoa(int assento)` (limite `assentos.length - 1`, recusa assento já vazio).
   Conceitos explicados nesta etapa: **o `Voo` não mexe nos assentos, delega** ao `Aviao`; um método da classe já enxerga os campos dela — não receber como parâmetro algo que já é seu (**shadowing**, exemplo da `Estante`/geladeira, e o porquê do `this.` nos setters); `length` é quantidade, último índice é `length - 1`; método `void` não informa o resultado, então quem chama não pode anunciar sucesso (ele escolheu a opção "só o `Aviao` imprime"; a alternativa de `adicionarPessoa` devolver `boolean` ficou em aberto); condição "nenhum dos permitidos" usa `&&`, não `||`; **nome de método booleano deve casar com o que ele devolve** (o `allowDesembarque` chegou a devolver `true` quando era proibido).
   Ele gosta de extrair verificações para métodos próprios — bom instinto, incentivar. Manteve um contador onde um `||` bastava; foi apontado duas vezes, não insistir mais.
5. ~~Troca de status com regras~~ (feito e testado em 05/10).
   Decisões dele: manteve o nome `setEstadoAtual` (em vez de criar `alterarStatus`) e o moveu para o fim da classe junto com as regras — não há mais porta dos fundos.
   `private boolean transicaoValida(StatusVoo novo)`: `switch` de seta sobre `this.estadoAtual`, devolvendo os destinos válidos de cada estado. Tabela escolhida: PLANEJADO→EM_SOLO/CANCELADO; EM_SOLO→TAXIANDO/CANCELADO; TAXIANDO→DECOLADO/CANCELADO; DECOLADO→EM_ROTA; EM_ROTA→EM_APROXIMACAO; EM_APROXIMACAO→POUSADO; POUSADO/CANCELADO→nada (finais). Ele optou por não permitir TAXIANDO→EM_SOLO (voltar ao portão).
   `setEstadoAtual(StatusVoo)`: 1) `!transicaoValida` (guarda mais geral vem primeiro); 2) só quando o destino é EM_SOLO, exige `aviao != null` e `requisitosAlcancado()` — aninhadas num `if` de destino, senão bloqueariam até o cancelamento de um voo sem avião; 3) atribui `this.estadoAtual`; 4) em CANCELADO/POUSADO chama `aviao.setDisponivel(true)`, com guarda de `null` antes.
   `null` é barrado de graça: o `switch` roda sobre o campo (nunca nulo) e todas as comparações com o parâmetro dão `false`.
   Conceitos desta etapa: regras dependem do **destino**, não valem para toda troca; ordem das guardas (mais geral primeiro); `switch` de seta não precisa de `break` nem de `default` quando o enum está coberto; estado final; liberar recurso ao fim do ciclo de vida.
   Sobras pequenas, não insistir: a mensagem `"Aviao " + aviao + "agora disponivel!"` imprime `Classe@hash` (toString padrão) e falta um espaço — explicado, decisão dele; `contarPassageiros()` ficou sem ninguém chamando depois que a regra de lotação foi removida por não estar no enunciado.
   `allowDesembarque` segue com contador onde um `||` bastava — apontado 3 vezes, NÃO insistir mais.
   Pendências antigas resolvidas em 06/10: `Pessoa`/`Tripulante` agora `abstract` (explicado com o porquê: categorias, não coisas concretas; construtor usado via `super`); `tripulantes`/`adicionarTripulante` removidos do `Aviao` (uma informação, um lugar).
6. ~~`OperacaoVoo` / `Decolagem` / `Pouso`~~ (feito e testado em 06/10). Interface `OperacaoVoo { void executar(Voo voo); }`; `Decolagem` faz TAXIANDO→DECOLADO→EM_ROTA e `Pouso` EM_APROXIMACAO→POUSADO, só via `voo.setEstadoAtual` (as regras valem de graça; voo cancelado não decola). `Voo` tem `private OperacaoVoo operacao`, `setOperacao` e `executarOperacao` com guarda de `null` + `return`. Explicado com a analogia da furadeira (encaixe = atributo, ponta = implementação, botão = `executarOperacao`). `setEstadoAtual` ganhou `println` de sucesso ("Voo X: status alterado para Y"). Decisão dele: manter "Operação executada!" no `setOperacao` (imprime ao encaixar, não ao executar).
7. ~~`main` de demonstração~~ (feito em 06/10): cobre os 6 itens do enunciado, inclusive recusas de propósito (EM_ROTA antes da tripulação completa, embarque antes de EM_SOLO, passageiro repetido) e o `Pessoa[]` com for-each chamando `anunciar()`.

**Projeto concluído em 06/10.** O Claude, a pedido dele, fez: validação em `Pessoa.setCpf` e `Passageiro.setPassaporte` (`!= null && !isBlank()`, construtor do `Passageiro` passa pelo setter) e redesenhou o `Diagrama Aeroporto.drawio` como UML completo a partir do código (atributos, métodos com visibilidade, estereótipos «abstract»/«interface»/«enumeration», herança, realização e associações com multiplicidade). Auditoria de 06/10 (feita pelo Claude, a pedido dele):
- Corrigido: `Tripulante.setTitulo` valida e o construtor usa o setter; `Aviao.adicionarPessoa` ganhou guarda de `null` no topo (pendência antiga do "Passageiro ja alocado!" falso); erros de digitação em mensagens.
- Corrigido: avião era liberado ao pousar/cancelar com passageiros ainda nos assentos. Agora `Voo.liberarAviaoSeVazio()` (privado) só libera se o voo está POUSADO/CANCELADO e `contarPassageiros() == 0`; é chamado no `setEstadoAtual` e em cada `desembarcar`. No `Main` atual o avião termina sem liberar porque ninguém desembarca (aviso "será liberado após o desembarque").
- Decisão dele, não insistir: o mesmo tripulante pode estar em vários voos ao mesmo tempo ("companhias aéreas deixam isso acontecer").
- Em aberto para a entrega: o enunciado pede "Testes" — hoje só há o `Main` de demonstração (confirmar com o professor); exemplo do enunciado usa assento `String` (`atribuirAssento(Passageiro p, String codigo)`), o dele usa `int`; visibilidade dos métodos não padronizada (Voo sem modificador, Pessoa `public`).

## Como rodar

Da pasta `project-aeroporto`:

```bash
javac -encoding UTF-8 -d out *.java
java -cp out Main
```

Funciona com JDK 21+. Localmente o usuário usa IntelliJ; a pasta `out/` é ignorada pelo git. Na nuvem dá para compilar e rodar se houver JDK instalado no ambiente; o teste "de verdade" o usuário faz na máquina local após `git pull`.
