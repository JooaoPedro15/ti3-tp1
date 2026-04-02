package utils;

import java.security.SecureRandom;

public class GeradorCodigo {
    private static final String alfabetoPadrao = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //definindo o alfabeto para geracao de codigo
    private static final int tamPadrao = 10; //tamanho padrao do codigo
    private static final SecureRandom gerador = new SecureRandom(); //gerador do codigo

    public static String gerarCodigo(){
        return gerarCodigo(tamPadrao);
    }

    private static String gerarCodigo(int tam){
        StringBuilder codigo = new StringBuilder(tam);

        //forma codigo ate ter 10 caracteres
        for(int i = 0; i < tam; i++){
            int indice = gerador.nextInt(alfabetoPadrao.length()); //pega o indice aleatorio da string alfabeto
            codigo.append(alfabetoPadrao.charAt(indice)); //concatena na string
        }

        return codigo.toString();
    }

}