package controle;

import arquivos.ArquivoCursos;
import arquivos.ArquivoUsuarios;
import arquivos.ArquivoCursoUsuario;
import entidades.CursoUsuario;
import entidades.Usuario;
import utils.Entrada;
import entidades.Curso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ControleCursoUsuario {
    private ArquivoCursos arqCursos;
    private ArquivoUsuarios arqUsuarios;
    private ArquivoCursoUsuario arqCursoUsuario;

    public ControleCursoUsuario(
        ArquivoCursos arqCursos,
        ArquivoUsuarios arqUsuarios
    ){

        this.arqCursos = arqCursos;
        this.arqUsuarios = arqUsuarios;

        try{
            this.arqCursoUsuario =
                    new ArquivoCursoUsuario();

        }catch(Exception e){

            System.out.println(
                    "Erro ao abrir arquivo de inscrições\n"
                    + e.getMessage()
            );
        }
    }

    private boolean inscreverUsuario(
        int idCurso,
        int idUsuario
    ){

        try{

            Curso curso = arqCursos.read(idCurso);

            if(curso == null){

                System.out.println(
                        "\nCurso inexistente."
                );

                return false;
            }

            Usuario usuario = arqUsuarios.read(idUsuario);

            if(usuario == null){

                System.out.println(
                        "\nUsuário inexistente."
                );

                return false;
            }

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByUsuario(
                            idUsuario
                    );

            for(CursoUsuario cu : inscricoes){

                if(cu.getIdCurso() == idCurso){

                    System.out.println(
                            "\nVocê já está inscrito neste curso."
                    );

                    return false;
                }
            }

            CursoUsuario novaInscricao =
                    new CursoUsuario(
                            idCurso,
                            idUsuario
                    );

            arqCursoUsuario.create(novaInscricao);

            return true;

        }catch(Exception e){

            System.out.println(
                    "\nErro ao realizar inscrição!\n"
                    + e.getMessage()
            );

            return false;
        }
    }

    private boolean cancelarInscricao(
        int idCurso,
        int idUsuario
    ){

        try{

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByUsuario(
                            idUsuario
                    );

            for(CursoUsuario cu : inscricoes){

                if(cu.getIdCurso() == idCurso){

                    return arqCursoUsuario.delete(
                            cu.getId()
                    );
                }
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao cancelar inscrição!\n"
                    + e.getMessage()
            );
        }

        return false;
    }

    private void listarTodosCursos(
        Usuario logado
    ){

        try{

            ArrayList<Curso> cursos =
                    arqCursos.readAll();

            if(cursos.isEmpty()){

                System.out.println(
                        "\nNenhum curso cadastrado."
                );

                return;
            }

            // ordenar por data

            Collections.sort(
                    cursos,
                    Comparator.comparing(
                            Curso::getDataInicio
                    )
            );

            int pagina = 0;
            final int TAMANHO_PAGINA = 10;

            while(true){

                int inicio =
                        pagina * TAMANHO_PAGINA;

                int fim =
                        Math.min(
                                inicio + TAMANHO_PAGINA,
                                cursos.size()
                        );

                int totalPaginas =
                        (int)Math.ceil(
                                (double)cursos.size()
                                / TAMANHO_PAGINA
                        );

                System.out.println(
                        "\nEntrePares 1.0"
                );

                System.out.println(
                        "--------------"
                );

                System.out.println(
                        "> Inicio > Minhas inscrições > Lista de cursos"
                );

                System.out.println(
                        "\nPágina "
                        + (pagina + 1)
                        + " de "
                        + totalPaginas
                );

                System.out.println();

                int numeroTela = 1;

                for(int i = inicio; i < fim; i++){

                    Curso curso = cursos.get(i);

                    System.out.println(
                            "(" + numeroTela + ") "
                            + curso.getNome()
                            + " - "
                            + curso.getDataInicio()
                            + " ("
                            + curso.getEstadoTexto()
                            + ")"
                    );

                    numeroTela++;
                }

                System.out.println(
                        "\n(A) Página anterior"
                );

                System.out.println(
                        "(B) Próxima página"
                );

                System.out.println(
                        "\n(R) Retornar ao menu anterior"
                );

                System.out.println(
                        "\nOpção: "
                );

                String op =
                        Entrada.SCANNER
                                .nextLine()
                                .trim();

                // selecionar curso

                try{

                    int escolha =
                            Integer.parseInt(op);

                    int indiceReal =
                            inicio + (escolha - 1);

                    if(indiceReal >= inicio
                            && indiceReal < fim){

                        visualizarCursoLista(
                                logado,
                                cursos.get(indiceReal)
                        );
                    }

                }catch(NumberFormatException e){

                    // navegação

                    if(op.equalsIgnoreCase("A")){

                        if(pagina > 0){
                            pagina--;
                        }

                        continue;
                    }

                    if(op.equalsIgnoreCase("B")){

                        if(fim < cursos.size()){
                            pagina++;
                        }

                        continue;
                    }

                    if(op.equalsIgnoreCase("R")){
                        return;
                    }

                    System.out.println(
                            "\nOpção inválida."
                    );
                }
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao listar cursos!\n"
                    + e.getMessage()
            );
        }
    }

    public void menuInscricoes(Usuario logado){
        while(true){
            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("(1) Minhas inscrições");
            System.out.println("(A) Buscar curso por codigo");
            System.out.println("(B) Buscar curso por palavras-chave");
            System.out.println("(C) Listar todos os cursos");
            System.out.println("\n(R) Retornar ao menu anterior");
            System.out.println("\nOpção: ");

            String op = Entrada.SCANNER.nextLine().trim();

            if(op.equals("1")){
                listarMinhasInscricoes(logado);
                continue;
            }

            if(op.equalsIgnoreCase("A")){
                buscarCursoPorCodigo(logado);
                continue;
            }
            
            if(op.equalsIgnoreCase("B")){
                System.out.println("\nBusca por palavra-chave sera implementada no TP3.");
                continue;
            }
            
            if(op.equalsIgnoreCase("C")){
                listarTodosCursos(logado);
                continue;
            }

            if(op.equalsIgnoreCase("R")){
                return;
            }

            System.out.println("Opcao invalida");
        }
    }

    private void listarMinhasInscricoes(
        Usuario logado
    ){

        try{

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByUsuario(
                            logado.getId()
                    );

            if(inscricoes.isEmpty()){

                System.out.println(
                        "\nVocê não possui inscrições."
                );

                return;
            }

            System.out.println("\nMINHAS INSCRIÇÕES\n");

            int contador = 1;

            for(CursoUsuario cu : inscricoes){

                Curso curso =
                        arqCursos.read(
                                cu.getIdCurso()
                        );

                if(curso != null){

                    System.out.println(
                            "(" + contador + ") "
                            + curso.getNome()
                    );

                    contador++;
                }
            }

            System.out.println(
                    "\nDigite o número do curso:"
            );

            String entrada =
                    Entrada.SCANNER.nextLine();

            int opcao;

            try{

                opcao = Integer.parseInt(entrada);

            }catch(NumberFormatException e){

                System.out.println(
                        "\nOpção inválida."
                );

                return;
            }

            if(opcao < 1 || opcao > inscricoes.size()){

                System.out.println(
                        "\nOpção inválida."
                );

                return;
            }

            CursoUsuario selecionada =
                    inscricoes.get(opcao - 1);

            Curso curso =
                    arqCursos.read(
                            selecionada.getIdCurso()
                    );

            if(curso != null){

                visualizarMinhaInscricao(
                        logado,
                        curso
                );
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao listar inscrições!\n"
                    + e.getMessage()
            );
        }
    }

    private void buscarCursoPorCodigo(Usuario logado){
        System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("> Inicio > Minhas Inscrições > Buscar curso por codigo");
            System.out.println("\nDigite o codigo do curso: ");
            String codigo = Entrada.SCANNER.nextLine().trim();

            try{
                Curso cursoEncontrado = arqCursos.buscarPorCodigo(codigo);

                if(cursoEncontrado == null){
                    System.out.println("Curso nao encontrado!");
                    return;
                }

                Usuario autorCurso = arqUsuarios.read(cursoEncontrado.getIdUsuario());
                String autor = (autorCurso != null? autorCurso.getNome() : "Usuario desconhecido");

                System.out.println("CÓDIGO........: " + cursoEncontrado.getCodigo());
                System.out.println("CURSO.........: " + cursoEncontrado.getNome());
                System.out.println("AUTOR.........: " + autor);
                System.out.println("DESCRIÇÃO.....: " + cursoEncontrado.getDescricao());
                System.out.println("DATA DE INÍCIO: " + cursoEncontrado.getDataInicio());
                
                System.out.println("\n(A) Fazer minha inscrição no curso");
                System.out.println("\n(R) Retornar ao menu anterior");
                System.out.println("\nOpção: ");
                String opCurso = Entrada.SCANNER.nextLine().trim();

                if(opCurso.equalsIgnoreCase("A")){
                    //salvar inscricao do usuario no curso
                    System.out.println("Inscricao feita com sucesso!");
                }else if(!opCurso.equalsIgnoreCase("R")){
                    System.out.println("\nOpcao invalida");
                }

            }catch(Exception e){
                System.out.println("\nErro ao buscar curso!\n" + e.getMessage());
            }
    }

    private void visualizarMinhaInscricao(
        Usuario logado,
        Curso curso
    ){

        try{

            Usuario autorCurso =
                    arqUsuarios.read(
                            curso.getIdUsuario()
                    );

            String autor =
                    (autorCurso != null)
                    ? autorCurso.getNome()
                    : "Usuário desconhecido";

            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println(
                    "> Inicio > Minhas inscrições > "
                    + curso.getNome()
            );

            System.out.println(
                    "\nCÓDIGO........: "
                    + curso.getCodigo()
            );

            System.out.println(
                    "CURSO.........: "
                    + curso.getNome()
            );

            System.out.println(
                    "AUTOR.........: "
                    + autor
            );

            System.out.println(
                    "DESCRIÇÃO.....: "
                    + curso.getDescricao()
            );

            System.out.println(
                    "DATA DE INÍCIO: "
                    + curso.getDataInicio()
            );

            System.out.println(
                    "\n(A) Cancelar minha inscrição"
            );

            System.out.println(
                    "\n(R) Retornar ao menu anterior"
            );

            System.out.println("\nOpção: ");

            String op =
                    Entrada.SCANNER.nextLine().trim();

            if(op.equalsIgnoreCase("A")){

                boolean ok =
                        cancelarInscricao(
                                curso.getId(),
                                logado.getId()
                        );

                if(ok){

                    System.out.println(
                            "\nInscrição cancelada com sucesso!"
                    );

                }else{

                    System.out.println(
                            "\nNão foi possível cancelar inscrição."
                    );
                }
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao visualizar inscrição!\n"
                    + e.getMessage()
            );
        }
    }

    private void visualizarCursoLista(
        Usuario logado,
        Curso curso
    ){

        try{

            Usuario autorCurso =
                    arqUsuarios.read(
                            curso.getIdUsuario()
                    );

            String autor =
                    (autorCurso != null)
                    ? autorCurso.getNome()
                    : "Usuário desconhecido";

            System.out.println(
                    "\nEntrePares 1.0"
            );

            System.out.println(
                    "--------------"
            );

            System.out.println(
                    "> Inicio > Minhas inscrições > "
                    + curso.getNome()
            );

            System.out.println(
                    "\nCÓDIGO........: "
                    + curso.getCodigo()
            );

            System.out.println(
                    "CURSO.........: "
                    + curso.getNome()
            );

            System.out.println(
                    "AUTOR.........: "
                    + autor
            );

            System.out.println(
                    "DESCRIÇÃO.....: "
                    + curso.getDescricao()
            );

            System.out.println(
                    "DATA DE INÍCIO: "
                    + curso.getDataInicio()
            );

            System.out.println(
                    "\n(A) Fazer minha inscrição"
            );

            System.out.println(
                    "\n(R) Retornar ao menu anterior"
            );

            System.out.println(
                    "\nOpção: "
            );

            String op =
                    Entrada.SCANNER
                            .nextLine()
                            .trim();

            if(op.equalsIgnoreCase("A")){

                boolean ok =
                        inscreverUsuario(
                                curso.getId(),
                                logado.getId()
                        );

                if(ok){

                    System.out.println(
                            "\nInscrição realizada!"
                    );

                }else{

                    System.out.println(
                            "\nNão foi possível realizar inscrição."
                    );
                }
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao visualizar curso!\n"
                    + e.getMessage()
            );
        }
    }

    private void exportarCSV(Curso curso){

        try{

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByCurso(
                            curso.getId()
                    );

            if(inscricoes.isEmpty()){

                System.out.println(
                        "\nNão há inscritos neste curso."
                );

                return;
            }

            String nomeArquivo =
                    "inscritos_" +
                    curso.getCodigo() +
                    ".csv";

            FileWriter fw =
                    new FileWriter(nomeArquivo);

            PrintWriter pw =
                    new PrintWriter(fw);

            // cabeçalho CSV

            pw.println("nome,email");

            for(CursoUsuario cu : inscricoes){

                Usuario usuario =
                        arqUsuarios.read(
                                cu.getIdUsuario()
                        );

                if(usuario != null){

                    pw.println(
                            usuario.getNome()
                            + ","
                            + usuario.getEmail()
                    );
                }
            }

            pw.close();
            fw.close();

            System.out.println(
                    "\nArquivo CSV exportado com sucesso!"
            );

            System.out.println(
                    "Arquivo: " + nomeArquivo
            );

        }catch(Exception e){

            System.out.println(
                    "\nErro ao exportar CSV!\n"
                    + e.getMessage()
            );
        }
    }

    public void abrirGerenciamentoInscritos(
        Curso curso
    ){

        try{

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByCurso(
                            curso.getId()
                    );

            System.out.println(
                    "\nEntrePares 1.0"
            );

            System.out.println(
                    "--------------"
            );

            System.out.println(
                    "> Inicio > Meus cursos > "
                    + curso.getNome()
                    + " > Inscrições"
            );

            System.out.println();

            int contador = 1;

            for(CursoUsuario cu : inscricoes){

                Usuario usuario =
                        arqUsuarios.read(
                                cu.getIdUsuario()
                        );

                if(usuario != null){

                    System.out.println(
                            "(" + contador + ") "
                            + usuario.getNome()
                            + " ("
                            + cu.getDataInscricao()
                            + ")"
                    );

                    contador++;
                }
            }

            System.out.println(
                    "\n(A) Exportar lista"
            );

            System.out.println(
                    "\n(R) Retornar ao menu anterior"
            );

            System.out.println(
                    "\nOpção: "
            );

            String op =
                    Entrada.SCANNER
                            .nextLine()
                            .trim();

            if(op.equalsIgnoreCase("A")){

                exportarCSV(curso);
            }

        }catch(Exception e){

            System.out.println(
                    "\nErro ao gerenciar inscritos!\n"
                    + e.getMessage()
            );
        }
    }

    public boolean cursoTemInscritos(
        int idCurso
    ){

        try{

            ArrayList<CursoUsuario> inscricoes =
                    arqCursoUsuario.readByCurso(
                            idCurso
                    );

            return !inscricoes.isEmpty();

        }catch(Exception e){

            return false;
        }
    }
}
