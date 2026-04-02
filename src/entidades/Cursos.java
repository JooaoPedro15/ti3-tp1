package entidades;

import java.io.*;

public class Curso {

    // id unico do curso
    private int id;

    // id do usuario dono do curso
    private int idUsuario;

    // nome do curso
    private String nome;

    // descricao detalhada do curso
    private String descricao;

    // data de inicio do curso
    private String dataInicio;

    // codigo compartilhavel para divulgar o curso
    private String codigo;

    // estado do curso
    // 0 ativo
    // 1 sem inscricoes
    // 2 concluido
    // 3 cancelado
    private int estado;

    // construtor vazio
    public Curso() {
        this.id = -1;
    }

    // construtor completo
    public Curso(int idUsuario, String nome, String descricao, String dataInicio, String codigo, int estado) {

        // define os valores recebidos
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.codigo = codigo;
        this.estado = estado;
    }

    // getters e setters

    // retorna o id
    public int getId() { return id; }

    // define o id
    public void setId(int id) { this.id = id; }

    // retorna o id do usuario dono
    public int getIdUsuario() { return idUsuario; }

    // define o id do usuario dono
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    // retorna o nome do curso
    public String getNome() { return nome; }

    // define o nome do curso
    public void setNome(String nome) { this.nome = nome; }

    // retorna a descricao
    public String getDescricao() { return descricao; }

    // define a descricao
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // retorna a data de inicio
    public String getDataInicio() { return dataInicio; }

    // define a data de inicio
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    // retorna o codigo compartilhavel
    public String getCodigo() { return codigo; }

    // define o codigo compartilhavel
    public void setCodigo(String codigo) { this.codigo = codigo; }

    // retorna o estado do curso
    public int getEstado() { return estado; }

    // define o estado do curso
    public void setEstado(int estado) { this.estado = estado; }

    // transforma o objeto em bytes para salvar no arquivo
    public byte[] toByteArray() throws IOException {

        // cria fluxo de escrita em memoria
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream da = new DataOutputStream(ba);

        // escreve os dados na ordem definida
        da.writeInt(id);
        da.writeInt(idUsuario);
        da.writeUTF(nome);
        da.writeUTF(descricao);
        da.writeUTF(dataInicio);
        da.writeUTF(codigo);
        da.writeInt(estado);

        // retorna os bytes gerados
        return ba.toByteArray();
    }

    // reconstroi o objeto a partir dos bytes lidos do arquivo
    public void fromByteArray(byte[] ba) throws IOException {

        // cria fluxo de leitura
        ByteArrayInputStream bi = new ByteArrayInputStream(ba);
        DataInputStream di = new DataInputStream(bi);

        // le os dados na mesma ordem em que foram gravados
        id = di.readInt();
        idUsuario = di.readInt();
        nome = di.readUTF();
        descricao = di.readUTF();
        dataInicio = di.readUTF();
        codigo = di.readUTF();
        estado = di.readInt();
    }
}