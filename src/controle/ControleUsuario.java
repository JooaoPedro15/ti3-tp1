package controle;

import entidades.Usuario;
import arquivos.ArquivoUsuarios;
import visao.VisaoUsuario;
import utils.HashSenha;

import java.util.*;

public class ControleUsuario {

    // acesso aos dados de usuario
    private ArquivoUsuarios arq = new ArquivoUsuarios();

    // interface de entrada e saida
    private VisaoUsuario visao = new VisaoUsuario();

    // cadastra novo usuario
    public void cadastrar() {

        // le dados digitados
        Usuario u = visao.leUsuario();

        // gera o hash da senha
        u.setHashSenha(HashSenha.hash(u.getHashSenha()));

        // salva usuario
        arq.create(u);

        System.out.println("usuario cadastrado");
    }

    // realiza login
    public Usuario login() {

        // le email e senha
        String[] dados = visao.leLogin();

        // busca usuario pelo email
        Usuario u = arq.buscarEmail(dados[0]);

        // compara hash da senha
        if(u != null && u.getHashSenha().equals(HashSenha.hash(dados[1]))) {
            System.out.println("login ok");
            return u;
        }

        System.out.println("login invalido");
        return null;
    }
}