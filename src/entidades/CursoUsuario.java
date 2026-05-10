package entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CursoUsuario implements Registro{
    private int idCursoUsuario;
    private int idCurso;
    private int idUsuario;
    private long dataInscricao;

    public CursoUsuario(){
        this(-1, -1, 0L);
    }

    public CursoUsuario(
        int idCurso,
        int idUsuario
    ){

        this.idCurso = idCurso;
        this.idUsuario = idUsuario;

        this.dataInscricao =
                System.currentTimeMillis();

        this.idCursoUsuario = -1;
    }

    public CursoUsuario(int idCurso, int idUsuario, long dataInscricao){
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
        this.dataInscricao = dataInscricao;
        this.idCursoUsuario = -1;
    }

    @Override
    public int getId(){
        return this.idCursoUsuario;
    }

    @Override
    public void setId(int id){
        this.idCursoUsuario = id;
    }

    @Override
    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream da = new DataOutputStream(ba);

        da.writeInt(this.idCursoUsuario);
        da.writeInt(this.idCurso);
        da.writeInt(this.idUsuario);
        da.writeLong(this.dataInscricao);

        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException{
        ByteArrayInputStream bi = new ByteArrayInputStream(ba);
        DataInputStream di = new DataInputStream(bi);

        this.idCursoUsuario = di.readInt();
        this.idCurso = di.readInt();
        this.idUsuario = di.readInt();
        this.dataInscricao = di.readLong();
    }

    public long getDataInscricao() {
        return dataInscricao;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdCursoUsuario() {
        return idCursoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setDataInscricao(long dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setIdCursoUsuario(int idCursoUsuario) {
        this.idCursoUsuario = idCursoUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
