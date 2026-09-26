# restaurante

Exercício de POO em Java (faculdade): modelagem de um restaurante com clientes, garçons, telefonista, pedidos, pratos, cardápio e regiões.

## Sobre a disciplina

Exercício de estudo da disciplina de **Programação Orientada a Objetos (POO)**, feito em Java para praticar modelagem de classes e os pilares da matéria: encapsulamento, herança (`Pessoa` → `Cliente`, `Garcom`, `Telefonista`), sobrecarga de construtores e associação entre objetos (`Pedido` ligando cliente, atendente e prato). O enunciado original não está no repositório.

## Regras de trabalho

Siga as regras do `CLAUDE.md` da raiz: atue como **professor**, não implemente código.

## Organização (`src/`)

- `Pessoa` (código, nome, cpf) → `Cliente`, `Garcom`, `Telefonista`.
- `Pedido` → cliente, garçom, prato, agendamento, endereço; 4 construtores (com garçom ou telefonista, com ou sem endereço).
- `Regiao` → array de garçons.
- `Prato`, `Cardapio` → vazios.
- `Main` → só cria um array de `Regiao`.

## Estado

Em andamento / esboço inicial. Ainda não foi revisado nas sessões. O enunciado da atividade não está no repositório — pergunte ao usuário antes de propor escopo.

## Como rodar

Da pasta `restaurante`:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
