# TP1 – EntrePares 1.0

## Integrantes
- Jamille Ferreira
- João Pedro   
- Maria Clara  

---

## 1. Descrição do Sistema

O sistema EntrePares 1.0 permite o cadastro de usuários e a criação de cursos livres ofertados por esses usuários.

Cada usuário pode criar vários cursos, e cada curso pertence a apenas um usuário, caracterizando um relacionamento do tipo 1:N.

Neste trabalho prático (TP1), foram implementadas as funcionalidades básicas de cadastro, autenticação e gerenciamento de cursos.

---

## 2. Funcionalidades Implementadas

### Usuários
- Cadastro de usuário  
- Login com email e senha  
- Armazenamento de senha utilizando hash  
- Busca de usuário por email  

### Cursos
- Cadastro de cursos  
- Associação automática ao usuário logado  
- Geração de código compartilhável  
- Listagem de cursos do usuário  

---

## 3. Estrutura do Projeto

O sistema foi organizado seguindo o padrão MVC:

- entidades → classes Usuario e Curso  
- arquivos → simulação de persistência e CRUD  
- controle → lógica do sistema  
- visao → interação com o usuário  
- utils → funções auxiliares  
- estruturas → classes fornecidas 

---

## 4. Modelagem de Dados
Usuario (1) -------- (N) Curso

Usuario

- id
- nome
- email
- hashSenha
- perguntaSecreta
- respostaSecreta

Curso

- id
- idUsuario
- nome
- descricao
- dataInicio
- codigo
- estado


Cada curso possui um atributo `idUsuario`, que representa o dono do curso.

---

## 5. Execução

1. Executar a classe `Main.java`  
2. Selecionar login ou cadastro  
3. Após login, acessar o menu de cursos  

---

## 6. Estruturas de Dados

### Índice por email
Foi utilizado um índice em memória (HashMap) para permitir a busca rápida de usuários por email durante o login.

### Relacionamento usuário → cursos
O relacionamento 1:N foi implementado utilizando uma estrutura de associação em memória entre idUsuario e lista de cursos.

---

## 7. Checklist (respondido com base na implementação)

Há um CRUD de usuários que estende ArquivoIndexado com índices?  
Resposta: Não. Foi implementado um CRUD simplificado em memória utilizando HashMap.

Há um CRUD de cursos que estende ArquivoIndexado com índices?  
Resposta: Não. Foi implementado um CRUD simplificado em memória.

Os cursos estão vinculados aos usuários usando idUsuario?  
Resposta: Sim.

Há uma árvore B+ para relacionamento 1:N?  
Resposta: Não. O relacionamento foi implementado utilizando estrutura em memória.

O trabalho compila corretamente?  
Resposta: Sim.

O trabalho está funcionando sem erros?  
Resposta: Sim.

O trabalho é original?  
Resposta: Sim.

---

## 8. Observações

O sistema foi desenvolvido seguindo o padrão MVC, separando responsabilidades entre dados, lógica e interface.

As senhas são armazenadas utilizando hash SHA-256.

O código compartilhável dos cursos é gerado automaticamente.

Nesta versão, foi utilizada uma abordagem simplificada em memória para facilitar o desenvolvimento inicial. A substituição por estruturas como ArquivoIndexado, Hash extensível e Árvore B+ pode ser realizada em versões futuras.

---

## 9. Evidências de Execução

[Inserir prints de:

- Cadastro de usuário  
- Login  
- Criação de curso  
- Listagem de cursos]  

---

## 10. Vídeo

[link do video]
