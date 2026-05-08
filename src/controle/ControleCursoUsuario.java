package controle;

import arquivos.ArquivoCursos;
import arquivos.ArquivoUsuarios;
import entidades.Usuario;
import utils.Entrada;
import entidades.Curso;
import java.util.Scanner;

public class ControleCursoUsuario {
    private ArquivoCursos arqCursos;
    private ArquivoUsuarios arqUsuarios;

    public ControleCursoUsuario(ArquivoCursos arqCursos, ArquivoUsuarios arqUsuarios){
        this.arqCursos = arqCursos;
        this.arqUsuarios = arqUsuarios;
    }

    public void menuInscricoes(Usuario logado){
        while(true){
            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("> Inicio > Minhas Inscrições");
            System.out.println("\nINSCRIÇÕES");

            //Implementar lista de cursos do usuario

            System.out.println("(A) Buscar curso por codigo");
            System.out.println("(B) Buscar curso por palavras-chave");
            System.out.println("(C) Listar todos os cursos");
            System.out.println("\n(R) Retornar ao menu anterior");
            System.out.println("\nOpção: ");

            String op = Entrada.SCANNER.nextLine().trim();

            if(op.equalsIgnoreCase("A")){
                buscarCursoPorCodigo(logado);
                continue;
            }
            
            if(op.equalsIgnoreCase("B")){
                System.out.println("\nBusca por palavra-chave sera implementada no TP3.");
                continue;
            }
            
            if(op.equalsIgnoreCase("C")){
                //implementar listagem de cursos
                continue;
            }

            if(op.equalsIgnoreCase("R")){
                return;
            }

            System.out.println("Opcao invalida");
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
}
