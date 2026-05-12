# TP2 - EntrePares 1.0

## Integrantes

- Jamille Ferreira
- Joao Pedro Costa
- Maria Clara G. Soares

## 1. Descricao do sistema

O sistema EntrePares 1.0 permite:

- cadastrar usuarios;
- autenticar usuarios por e-mail e senha;
- recuperar senha com pergunta secreta;
- cadastrar cursos vinculados ao usuario logado;
- alterar, concluir, encerrar inscricoes ou cancelar cursos;
- buscar cursos de outros usuarios por codigo NanoID;
- listar todos os cursos com paginacao de 10 itens por pagina;
- visualizar os dados completos de um curso antes da inscricao;
- realizar inscricoes em cursos de outros usuarios;
- listar e cancelar as proprias inscricoes;
- gerenciar os inscritos nos cursos do usuario proponente;
- visualizar nome, e-mail e data de inscricao de um usuario inscrito;
- cancelar a inscricao de um usuario em um curso proprio;
- exportar a lista de inscritos em CSV.

Neste TP2, foi implementado o modulo de inscricoes, incluindo o relacionamento N:N entre usuarios e cursos por meio da entidade `CursoUsuario`.

## 2. Organizacao do projeto

O projeto segue o padrao MVC, separando entidades, persistencia, controle e visao.

### Classes principais

### Usuarios

- Cadastro de usuario
- Login com e-mail e senha
- Armazenamento de senha utilizando hash
- Recuperacao de senha por pergunta secreta
- Busca de usuario por e-mail
- Exclusao de usuario com verificacao de cursos ativos e limpeza de inscricoes relacionadas

### Cursos

- Cadastro de cursos
- Associacao automatica ao usuario logado
- Geracao de codigo compartilhavel NanoID
- Busca de curso por codigo
- Listagem dos cursos do usuario ativo
- Listagem geral de cursos com paginacao
- Visualizacao completa dos dados do curso
- Alteracao, encerramento de inscricoes, conclusao e cancelamento de curso
- Gerenciamento dos usuarios inscritos no curso

### Inscricoes

- CRUD da entidade de associacao `CursoUsuario`
- Inscricao de usuario em curso
- Consulta dos cursos em que o usuario esta inscrito
- Consulta dos usuarios inscritos em um curso
- Cancelamento da propria inscricao
- Cancelamento da inscricao de um usuario pelo proponente do curso
- Exportacao da lista de inscritos em CSV

### Usuario

- `id`
- `nome`
- `email`
- `hashSenha`
- `perguntaSecreta`
- `hashRespostaSecreta`

### Curso

- `id`
- `idUsuario`
- `nome`
- `descricao`
- `dataInicio`
- `codigo`
- `estado`

### CursoUsuario

- `idCursoUsuario`
- `idCurso`
- `idUsuario`
- `dataInscricao`

### Pacotes

- `entidades` -> classes `Usuario`, `Curso`, `CursoUsuario` e interface `Registro`
- `arquivos` -> classes de CRUD e persistencia
- `controle` -> logica do sistema e menus
- `visao` -> interacao com o usuario
- `utils` -> funcoes auxiliares
- `estruturas` -> classes de hash extensivel, arvore B+ e arquivo indexado

## 3. Modelagem de Dados

Relacionamentos implementados:

```text
Usuario (1) -------- (N) Curso
Usuario (N) -------- (N) Curso
```

O relacionamento 1:N entre `Usuario` e `Curso` indica que um usuario pode criar varios cursos.

O relacionamento N:N entre `Usuario` e `Curso` indica que um usuario pode se inscrever em varios cursos e que um curso pode possuir varios usuarios inscritos.

Para implementar o relacionamento N:N, foi criada a entidade de associacao `CursoUsuario`.

## 4. Persistencia e indices

### CRUD generico

A classe `ArquivoIndexado` foi implementada no modelo apresentado em sala:

- cabecalho com ultimo ID e cabeca da lista de espacos livres;
- registro com lapide, tamanho e vetor de bytes;
- indice direto por hash extensivel apontando de `id` para o endereco do registro no arquivo;
- reaproveitamento de espacos excluidos.

### Indices de usuarios

A classe `ArquivoUsuarios` estende `ArquivoIndexado<Usuario>` e mantem:

- indice direto por ID;
- indice indireto por e-mail com hash extensivel.

### Indices de cursos

A classe `ArquivoCursos` estende `ArquivoIndexado<Curso>` e mantem:

- indice direto por ID;
- indice por codigo NanoID;
- indice por nome;
- indice relacional `idUsuario -> idCurso`.

### Indices de inscricoes

A classe `ArquivoCursoUsuario` estende `ArquivoIndexado<CursoUsuario>` e mantem:

- indice direto por ID da inscricao;
- arvore B+ com os pares `(idCurso, idCursoUsuario)`;
- arvore B+ com os pares `(idUsuario, idCursoUsuario)`.

Essas duas arvores B+ permitem recuperar eficientemente:

- todos os usuarios inscritos em um curso;
- todos os cursos em que um usuario esta inscrito.

## 5. Menus implementados

### Tela inicial

- login;
- novo usuario;
- recuperacao de senha;
- sair.

### Menu principal do usuario logado

- meus dados;
- meus cursos;
- minhas inscricoes;
- sair.

### Menu de cursos

- novo curso;
- visualizar curso selecionado;
- gerenciar inscritos no curso;
- corrigir dados do curso;
- encerrar inscricoes;
- concluir curso;
- cancelar curso;
- retornar ao menu anterior.

### Menu de inscricoes

- listagem das inscricoes do usuario logado;
- busca de curso por codigo;
- busca por palavras-chave, reservada para o TP3;
- listagem de todos os cursos;
- visualizacao completa do curso;
- realizacao de inscricao;
- cancelamento da propria inscricao;
- retorno ao menu anterior.

### Menu de inscritos no curso

- listagem dos usuarios inscritos;
- visualizacao dos dados de um inscrito;
- cancelamento da inscricao de um usuario;
- exportacao da lista em CSV;
- retorno ao menu anterior.

## 6. Regras de negocio implementadas

- o login e feito por e-mail e senha com comparacao por hash;
- a resposta secreta tambem e armazenada em hash;
- um e-mail nao pode ser reutilizado por outro usuario;
- um codigo compartilhavel nao pode ser reutilizado por outro curso;
- todo curso novo recebe automaticamente o `idUsuario` do usuario logado;
- usuarios com cursos ativos nao podem ser excluidos;
- ao excluir um usuario, suas inscricoes sao removidas;
- ao excluir cursos inativos de um usuario, as inscricoes relacionadas tambem sao removidas;
- um usuario nao pode se inscrever no proprio curso;
- um usuario nao pode se inscrever duas vezes no mesmo curso;
- somente cursos abertos aceitam novas inscricoes;
- cursos com inscricoes nao sao excluidos diretamente, mas marcados como cancelados;
- cursos sem inscritos podem ser excluidos;
- o usuario pode cancelar a propria inscricao;
- o proponente do curso pode cancelar a inscricao de um usuario;
- a lista de inscritos pode ser exportada em CSV.

## 7. Compilacao e execucao

Exemplo de compilacao:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
```

Exemplo de execucao:

```powershell
java -cp out Main
```

## 8. Evidencias que devem aparecer no video

- cadastro de usuario;
- login;
- criacao de curso;
- visualizacao do codigo NanoID do curso;
- busca de curso por codigo;
- listagem de todos os cursos com paginacao;
- visualizacao completa dos dados do curso;
- inscricao de um usuario em um curso;
- listagem das proprias inscricoes;
- cancelamento da propria inscricao;
- gerenciamento de inscritos pelo proponente do curso;
- visualizacao dos dados de um usuario inscrito;
- cancelamento da inscricao de um usuario pelo proponente;
- exportacao da lista de inscritos em CSV.

## 9. Checklist

Ha um CRUD da entidade de associacao CursoUsuario (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensiveis e Arvores B+ como indices diretos e indiretos conforme necessidade) que funciona corretamente?  
Resposta: Sim. `ArquivoCursoUsuario` estende `ArquivoIndexado<CursoUsuario>`, usa o indice direto herdado da classe base e mantem duas arvores B+: uma para `(idCurso, idCursoUsuario)` e outra para `(idUsuario, idCursoUsuario)`.

A visao de inscricoes esta corretamente implementada e permite consultas aos cursos em que um usuario esta inscrito?  
Resposta: Sim. O menu "Minhas inscricoes" mostra as inscricoes do usuario logado, permite abrir os dados completos do curso e cancelar a inscricao.

A visao de cursos funciona corretamente e permite a gestao dos usuarios inscritos em um curso?  
Resposta: Sim. No menu "Meus cursos", a opcao "Gerenciar inscritos no curso" lista os inscritos, permite visualizar dados do usuario, cancelar inscricoes e exportar CSV.

Ha uma visualizacao dos cursos de outras pessoas por meio de um codigo NanoID?  
Resposta: Sim. A busca por codigo localiza o curso pelo NanoID e abre diretamente a tela de dados completos do curso.

A integridade do relacionamento entre cursos e usuarios esta mantida em todas as operacoes?  
Resposta: Sim. O sistema impede inscricoes duplicadas, impede inscricao no proprio curso, remove associacoes no cancelamento de inscricoes e limpa inscricoes relacionadas quando usuarios ou cursos sao removidos.

O trabalho compila corretamente?  
Resposta: Sim.

O trabalho esta completo e funcionando sem erros de execucao?  
Resposta: Sim para o escopo do TP2. A busca por palavras-chave permanece para o TP3, conforme o enunciado.

O trabalho e original e nao a copia de um trabalho de outro grupo?  
Resposta: Sim.

## 10. Evidencias de Execucao

As imagens abaixo registram operacoes ja demonstradas no sistema

#### Cadastro de Usuario

<img src="/public/tela_cadastro.jpg">

#### Login de Usuario

<img src="/public/tela_login.jpg">

#### Exibir dados do Usuario e Alterar dados

<img src="/public/tela_dados_do_usuario_e_alteracao.jpg">

#### Esqueci minha Senha

<img src="/public/tela_esquecer_senha.jpg">

#### Excluir Usuario

<img src="/public/tela_excluir_usuario.jpg">

#### Cadastro de Curso

<img src="/public/tela_cadastro_de_curso.jpg">

#### Exibir dados do Curso

<img src="/public/tela_dados_curso.jpg">

#### Atualizar Curso

<img src="/public/tela_atualizar_curso.jpg">

#### Encerrar inscricoes e Excluir Curso

<img src="/public/tela_encerrar_inscricoes_e_deletar_curso.jpg">

#### Telas de inscricao

#### Busca por codigo

#### Listagem paginada

#### Gerenciamento de inscritos

#### Exportacao CSV

## 11. Video

[Assistir video](./video/video.mp4)
