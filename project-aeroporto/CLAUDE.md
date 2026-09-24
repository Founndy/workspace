# project-aeroporto

Exercício de POO em Java: sistema de operação de voos em um aeroporto. É trabalho da faculdade e **sessão de aprendizado**: siga as regras de trabalho do `CLAUDE.md` da raiz (atuar como professor, não implementar código).

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
3. **Passo em que paramos:** `alocarAviao` (perguntas lançadas: o que recebe, o que faz com `aviao`, o que fazer com `null`), depois `alocarTripulante`, `alocarTripulante` (no máx. 1 piloto e 1 copiloto), verificação de tripulação completa (1 piloto, 1 copiloto, ≥ 1 comissário).
4. Embarque/desembarque delegando aos assentos do `Aviao`.
5. Troca de status com regras (ex.: não sair do solo sem avião e tripulação completa; cancelado não muda).
6. `OperacaoVoo` / `Decolagem` / `Pouso` e troca de estratégia no `Voo`.
7. `main` de demonstração conforme o item 5 do enunciado.

## Como rodar

Da pasta `project-aeroporto`:

```bash
javac -encoding UTF-8 -d out *.java
java -cp out Main
```

Funciona com JDK 21+. Localmente o usuário usa IntelliJ; a pasta `out/` é ignorada pelo git. Na nuvem dá para compilar e rodar se houver JDK instalado no ambiente; o teste "de verdade" o usuário faz na máquina local após `git pull`.
