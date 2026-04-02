import entidades.Usuario;
import controle.ControleUsuario;
import controle.ControleCurso;

import java.util.Scanner;

public class Main {

    // controle de usuarios
    static ControleUsuario cu = new ControleUsuario();

    // controle de cursos
    static ControleCurso cc = new ControleCurso();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // usuario logado
        Usuario logado = null;

        while(true) {

            System.out.println("\n1 login");
            System.out.println("2 novo usuario");
            System.out.println("0 sair");

            String op = sc.nextLine();

            // login
            if(op.equals("1"))
                logado = cu.login();

            // cadastro
            else if(op.equals("2"))
                cu.cadastrar();

            // sair
            else if(op.equals("0"))
                break;

            // se login deu certo entra no menu de cursos
            if(logado != null) {
                cc.menu(logado);
                logado = null;
            }
        }
    }
}