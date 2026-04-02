package controle;

import entidades.Usuario;
import entidades.Curso;
import arquivos.ArquivoCursos;
import visao.VisaoCurso;
import utils.GeradorCodigo;

import java.util.List;
import java.util.*;

public class ControleCurso {

    // acesso aos dados de cursos
    private ArquivoCursos arq = new ArquivoCursos();

    // interface
    private VisaoCurso visao = new VisaoCurso();

    // menu principal de cursos
    public void menu(Usuario u) {

        java.util.Scanner sc = new java.util.Scanner(System.in);

        while(true) {

            // lista cursos do usuario
            List<Curso> lista = arq.listarPorUsuario(u.getId());

            visao.listarCursos(lista);

            System.out.println("(a) novo curso");
            System.out.println("(r) voltar");

            String op = sc.nextLine();

            // cria curso
            if(op.equalsIgnoreCase("a"))
                criar(u);

            // volta para menu anterior
            else if(op.equalsIgnoreCase("r"))
                break;
        }
    }

    // cria novo curso
    public void criar(Usuario u) {

        // le dados do curso
        Curso c = visao.leCurso();

        // define usuario dono
        c.setIdUsuario(u.getId());

        // gera codigo unico
        c.setCodigo(GeradorCodigo.gerar());

        // define estado inicial
        c.setEstado(0);

        // salva curso
        arq.create(c);

        System.out.println("curso criado");
    }
}