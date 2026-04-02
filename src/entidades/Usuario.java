package entidades;

import java.io.*;

public class Usuario {

    // atributo que guarda o id unico do usuario
    private int id;

    // nome do usuario
    private String nome;

    // email usado para login
    private String email;

    // hash da senha nao armazenamos a senha real
    private String hashSenha;

    // pergunta usada para recuperar senha
    private String perguntaSecreta;

    // resposta da pergunta secreta
    private String respostaSecreta;

    // construtor vazio usado ao ler dados do arquivo
    public Usuario() {
        // inicializa id com valor invalido
        this.id = -1;
    }

    // construtor usado ao criar um novo usuario
    public Usuario(String nome, String email, String hashSenha,
                   String perguntaSecreta, String respostaSecreta) {

        // atribui os valores recebidos aos atributos
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    // retorna o id do usuario
    public int getId() { return id; }

    // define o id do usuario
    public void setId(int id) { this.id = id; }

    // retorna o nome
    public String getNome() { return nome; }

    // define o nome
    public void setNome(String nome) { this.nome = nome; }

    // retorna o email
    public String getEmail() { return email; }

    // define o email
    public void setEmail(String email) { this.email = email; }

    // retorna o hash da senha
    public String getHashSenha() { return hashSenha; }

    // define o hash da senha
    public void setHashSenha(String hashSenha) { this.hashSenha = hashSenha; }

    // retorna a pergunta secreta
    public String getPerguntaSecreta() { return perguntaSecreta; }

    // define a pergunta secreta
    public void setPerguntaSecreta(String perguntaSecreta) { this.perguntaSecreta = perguntaSecreta; }

    // retorna a resposta secreta
    public String getRespostaSecreta() { return respostaSecreta; }

    // define a resposta secreta
    public void setRespostaSecreta(String respostaSecreta) { this.respostaSecreta = respostaSecreta; }

    // metodo que transforma o objeto em vetor de bytes para salvar no arquivo
    public byte[] toByteArray() throws IOException {

        // cria um fluxo de bytes em memoria
        ByteArrayOutputStream ba = new ByteArrayOutputStream();

        // permite escrever dados primitivos no fluxo
        DataOutputStream da = new DataOutputStream(ba);

        // escreve cada atributo na mesma ordem
        da.writeInt(id);
        da.writeUTF(nome);
        da.writeUTF(email);
        da.writeUTF(hashSenha);
        da.writeUTF(perguntaSecreta);
        da.writeUTF(respostaSecreta);

        // retorna o vetor de bytes gerado
        return ba.toByteArray();
    }

    // metodo que reconstrói o objeto a partir de um vetor de bytes
    public void fromByteArray(byte[] ba) throws IOException {

        // cria um fluxo de leitura a partir do vetor de bytes
        ByteArrayInputStream bi = new ByteArrayInputStream(ba);

        // permite ler dados primitivos do fluxo
        DataInputStream di = new DataInputStream(bi);

        // le os dados na mesma ordem em que foram gravados
        id = di.readInt();
        nome = di.readUTF();
        email = di.readUTF();
        hashSenha = di.readUTF();
        perguntaSecreta = di.readUTF();
        respostaSecreta = di.readUTF();
    }
}