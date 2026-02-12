---
title: TP1

---

#  Avaliação 1 — API da Guilda de Aventureiros

![lgloria_a_vast_medieval_guild_registry_hall_stone_architectur_a6e19ca9-ec89-470a-a9e5-e954c1b700a9_3](https://hackmd.io/_uploads/rklkc0DP-x.png)




##  Prólogo — O Registro da Guilda

Durante séculos, a Guilda de Aventureiros manteve seus registros em pergaminhos espalhados por salões, cofres e mesas de taverna.  
Nomes riscados às pressas, níveis anotados a carvão, companheiros esquecidos nas margens do papel.

Com o aumento das expedições e o surgimento de novas ameaças, o Conselho da Guilda decidiu que não bastava mais confiar na memória dos escribas.

Era necessário um **Registro Oficial**.

Esse registro deveria:

- saber quem pertence à guilda  
- distinguir ativos dos expulsos  
- registrar classes e níveis com precisão  
- manter, quando existente, o vínculo entre um aventureiro e seu companheiro leal  

O Conselho impôs regras claras:

- ninguém entra sem ser devidamente registrado  
- registros inválidos não são aceitos  
- aventureiros expulsos não desaparecem da história  
- companheiros não vagam sozinhos pelos arquivos  

Você foi convocado para construir esse novo registro.

O Conselho **não fornecerá instruções técnicas**.  
Apenas os **requisitos do mundo real**.

A Guilda espera clareza, consistência e respeito às regras.  
**Erros de registro custam vidas em campo.**

---

##  Conceitos do Domínio

###  Aventureiro

Um aventureiro possui obrigatoriamente:

- `id` — identificador único, gerado pelo sistema  
- `nome`  
- `classe` — valor obrigatório, pertencente a um conjunto fixo  
- `nivel`  
- `ativo`  
- `companheiro` — opcional  

#### Classes permitidas

O campo `classe` deve aceitar **exclusivamente** um dos valores abaixo:

- `GUERREIRO`
- `MAGO`
- `ARQUEIRO`
- `CLERIGO`
- `LADINO`

Qualquer outro valor deve ser tratado como **inválido**.

---

###  Companheiro (Composição)

O companheiro existe **apenas como parte do aventureiro**.  
Não pode existir isoladamente nem ser compartilhado entre aventureiros.

Um companheiro possui:

- `nome`  
- `especie` — valor obrigatório, pertencente a um conjunto fixo  
- `lealdade` — valor inteiro entre 0 e 100  

#### Espécies permitidas

O campo `especie` deve aceitar **exclusivamente** um dos valores abaixo:

- `LOBO`
- `CORUJA`
- `GOLEM`
- `DRAGAO_MINIATURA`

---

##  Regras de Negócio

- O `id` é sempre gerado pelo sistema  
- O `nome` do aventureiro é obrigatório e não pode ser vazio  
- A `classe` deve pertencer ao conjunto permitido  
- O `nivel` deve ser maior ou igual a 1  
- Um aventureiro recém-criado inicia obrigatoriamente como `ativo = true`  
- Um aventureiro inativo continua existindo no sistema  
- Caso exista companheiro:
  - `nome` é obrigatório  
  - `especie` deve pertencer ao conjunto permitido  
  - `lealdade` deve estar entre 0 e 100  
- Operações sobre recursos inexistentes devem indicar **recurso não encontrado**  
- Solicitações com dados inválidos devem ser rejeitadas como **inválidas**, informando os motivos  

---

##  Operações Disponíveis

### 1️⃣ Registrar aventureiro

Registra um novo aventureiro na guilda.

O cliente fornece:
- `nome`
- `classe`
- `nivel`

O sistema:
- gera o `id`
- define o aventureiro como ativo
- **não permite definir companheiro nessa operação**

O sistema deve indicar claramente que um **novo recurso foi criado**.

---

### 2️⃣ Listar aventureiros (com filtros e paginação)

Retorna aventureiros cadastrados.

#### Filtros suportados:
- por `classe`
- por `ativo`
- por `nivel` mínimo

#### Parâmetros de paginação:
- `page` — número da página (inicia em 0)
- `size` — quantidade de itens por página

Valores padrão:
- `page = 0`
- `size = 10`

Restrições:
- `page` não pode ser negativo
- `size` deve estar entre 1 e 50

#### A resposta deve conter:
- lista de aventureiros **em formato de resumo**, sem informações de companheiro
- metadados de paginação retornados via **headers**

Headers obrigatórios:
- `X-Total-Count`
- `X-Page`
- `X-Size`
- `X-Total-Pages`

Caso a página solicitada não exista:
- retornar lista vazia
- manter headers corretos

---

### 3️⃣ Consultar aventureiro por id

Retorna todas as informações do aventureiro, incluindo o companheiro (se existir).

Caso o id não exista, deve ser indicado **recurso não encontrado**.

---

### 4️⃣ Atualizar dados do aventureiro

Permite atualizar exclusivamente:
- `nome`
- `classe`
- `nivel`

Não é permitido:
- alterar `id`
- alterar estado `ativo`
- alterar `companheiro`

Dados inválidos devem ser rejeitados.  
Caso o recurso não exista, deve ser indicado **não encontrado**.

---

### 5️⃣ Encerrar vínculo com a guilda

Altera o estado do aventureiro para `ativo = false`.

O aventureiro permanece registrado no sistema.

---

### 6️⃣ Recrutar novamente

Altera o estado do aventureiro para `ativo = true`.

---

## Gerenciamento do Companheiro

### 7️⃣ Definir ou substituir companheiro

Cria ou substitui o companheiro associado a um aventureiro.

O cliente fornece:
- `nome`
- `especie`
- `lealdade`

Após a operação:
- o aventureiro passa a possuir **exatamente um companheiro**

Caso o aventureiro não exista:
- indicar **recurso não encontrado**

Caso os dados do companheiro sejam inválidos:
- rejeitar a solicitação

---

### 8️⃣ Remover companheiro

Remove o companheiro associado ao aventureiro.

Após a operação:
- o aventureiro continua existindo
- o companheiro deixa de existir

---

##  Paginação e Ordenação

- Todas as listagens devem ser retornadas em ordem crescente de `id`
- A paginação deve ser aplicada **após** os filtros
- O total de registros considera apenas os itens que atendem aos filtros

---

##  Padrão de Erro

Em caso de erro, a resposta deve seguir um formato consistente.

Exemplo:

```json
{
  "mensagem": "Solicitação inválida",
  "detalhes": [
    "classe inválida",
    "nivel deve ser maior ou igual a 1"
  ]
}

```

## Considerações finais 
 - As operações devem respeitar corretamente a semântica HTTP.
 - Os códigos de status devem refletir o resultado real da operação.
 - A API deve ser previsível, consistente e estável.
 - Não deve ser utilizado banco de dados ou persistência externa.
 - Deve-se criar uma classe que simula o banco de dados com uma ArrayList que deverá ser inicializada com pelo menos 100 registros.
 - Deve se usar linguagem Java a partir do Java 8.


> **“O Conselho da Guilda não avalia apenas registros, mas decisões.  
> Um bom sistema preserva a memória; um sistema falho reescreve tragédias.”**

*— O Conselho da Guilda de Aventureiros*

![lgloria_ancient_guild_archive_hall_filled_with_towering_shelv_eb34dda4-82b2-4f75-bd2d-767d3fd6fc13_0](https://hackmd.io/_uploads/B1Pg50wwWl.png)
