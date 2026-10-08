# restaurante

Exercício de POO em Java (faculdade): modelagem de um restaurante com clientes, garçons, telefonista, pedidos, pratos, cardápio e regiões.

## Sobre a disciplina

Exercício de estudo da disciplina de **Programação Orientada a Objetos (POO)**. É uma tarefa **mais antiga que o `project-aeroporto`**: quando foi passada, vários conceitos usados no aeroporto ainda não tinham sido apresentados.

## Enunciado da atividade (transcrição fiel do PDF, que não foi publicado)

**Exercício: Restaurante**

Um restaurante serve pratos em um cardápio. O cardápio contém o preço de cada prato e o dia da semana em que este está disponível. Garçons atendem os clientes conforme a região do restaurante. Cada região define um conjunto de garçons que as servem. Pedidos podem ser coletados por um dos garçons ou pela telefonista. O pedido pode ser atendido imediatamente, cenário mais comum, ou requisitar que seja servido no dia e horário especificados. Neste último caso, este deverá ser servido em uma mesa ou entregue em determinado domicílio.

A partir do enunciado, modele o problema por meio de um diagrama de classes UML e implemente o código correspondente.

## Regras de trabalho (exceção à raiz)

Nesta tarefa o código pode ser completado diretamente, no mesmo estilo do aeroporto, usando **só conceitos que ele já mostrou saber** lá, sem extrapolar o enunciado. Não usar: `ArrayList`/coleções, `LocalDate`/`LocalTime`, exceções, `toString`/`equals` sobrescritos, `static` além do `main`, genéricos, records, `String.format`.

Conceitos liberados (vistos no aeroporto): atributos `private`, setters com validação (`!= null && !isBlank()`, limites numéricos) chamados pelo construtor, métodos de negócio com cláusulas de guarda + `println` + `return`, arrays com `for` e for-each, `instanceof`, enum, `switch` de seta, classe `abstract`, interface/`implements`, delegação, métodos `boolean` de verificação.

## Organização (`src/`)

- `Pessoa` (**abstract**; código, nome, cpf, setters com validação) → `Cliente`, `Garcom`, `Telefonista`.
- `DiaSemana` → enum SEGUNDA..DOMINGO.
- `Prato` → nome, preço, `DiaSemana dia` (o dia em que está disponível).
- `Cardapio` → `Prato[20]`; `adicionarPrato` (guardas: `null`, repetido, cheio), `contem(Prato)`, `exibirPratosDoDia(DiaSemana)`.
- `Regiao` → nome, `Garcom[5]`; `adicionarGarcom` (guardas: `null`, repetido, sem vaga), `atende(Garcom)`.
- `TipoAtendimento` → enum IMEDIATO, MESA, DOMICILIO.
- `Pedido` → cliente, dia, prato, garçom **ou** telefonista, tipo (começa IMEDIATO), horário, mesa, endereço. Métodos de negócio no estilo do `Voo`:
  - `escolherPrato(Cardapio, Prato)`: já escolhido, `null`, fora do cardápio, prato de outro dia.
  - `coletarPorGarcom(Garcom, Regiao)`: já coletado, `null`, garçom que não atende a região ("conforme a região").
  - `coletarPorTelefonista(Telefonista)`: já coletado, `null`.
  - `agendarMesa(String horario, int mesa)` / `agendarDomicilio(String horario, String endereco)`: já agendado, horário em branco, mesa ≤ 0 / endereço em branco.
  - `exibirResumo()`.
- `Main` → demonstração: cardápio (com prato repetido recusado), regiões, 3 pedidos (imediato no salão com recusas de prato de outro dia e de garçom de outra região; agendado na mesa; domicílio pela telefonista com recusas de "já coletado" e "já agendado").

## Decisões de modelagem (08/10)

- O esboço antigo tinha 4 construtores no `Pedido` (com garçom/telefonista, com/sem endereço) e os de telefonista gravavam `garcom` (a telefonista se perdia). Trocado por **um construtor (cliente, dia) + métodos de negócio**, como no `Voo` (`alocarAviao`/`alocarTripulante`).
- Garçom e telefonista ficam em dois atributos, um deles `null` — a ideia original dele; sem classe intermediária, para não extrapolar.
- `Boolean agendamento` virou o enum `TipoAtendimento` (mesmo motivo do `StatusVoo`: o compilador barra valores inválidos).
- Horário como `String` (ex.: "20:00") porque `LocalTime` não foi visto.
- Um prato por pedido, como no esboço dele.
- `Telefonista` tinha um campo `Pedido pedido` solto, sem uso: removido (uma informação, um lugar).
- `Regiao[] regiaos` do `Main` antigo removido; as regiões são criadas uma a uma na demonstração.

## Estado

Implementação completa, compila e o `Main` roda (08/10). Falta o **diagrama de classes UML** pedido no enunciado (não existe `.drawio` nesta pasta).

## Como rodar

Da pasta `restaurante`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
