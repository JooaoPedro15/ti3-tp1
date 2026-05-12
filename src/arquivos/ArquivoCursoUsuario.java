package arquivos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import aed3.ArvoreBMais;
import aed3.RegistroArvoreBMais;
import entidades.Curso;
import entidades.CursoUsuario;
import estruturas.ArquivoIndexado;

import java.util.ArrayList;
import java.util.List;

public class ArquivoCursoUsuario extends ArquivoIndexado<CursoUsuario>{
    private static final String ARQ_DADOS = "dados/cursoUsuario.db";
    private static final String ARQ_INDICE_DIRETO = "dados/cursoUsuarioId.hash";
    private static final String ARQ_INDICE_CURSOS = "dados/cursoUsuario_curso.idx";
    private static final String ARQ_INDICE_USUARIOS = "dados/cursoUsuario_usuario.idx";

    private final ArvoreBMais<ParIdId> indiceCursos;
    private final ArvoreBMais<ParIdId> indiceUsuarios;

    public ArquivoCursoUsuario() throws Exception{
        super(ARQ_DADOS, ARQ_INDICE_DIRETO, CursoUsuario::new);

        limparArquivoIndice(ARQ_INDICE_CURSOS);
        limparArquivoIndice(ARQ_INDICE_USUARIOS);

        indiceCursos = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, ARQ_INDICE_CURSOS);
        indiceUsuarios = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, ARQ_INDICE_USUARIOS);
        reconstruirIndices();
    }

    @Override
    public int create(CursoUsuario cursoUsuario){
        int idInscricao = super.create(cursoUsuario);

        try{
            ParIdId parCurso = new ParIdId(cursoUsuario.getIdCurso(), idInscricao);
            ParIdId parUsuario = new ParIdId(cursoUsuario.getIdUsuario(), idInscricao);

            indiceCursos.create(parCurso);
            indiceUsuarios.create(parUsuario);
        }catch (Exception e) {
            System.out.println("Erro ao salvar\n" + e.getMessage());
        }
            return idInscricao;
    }

    @Override
    public boolean delete(int idInscricao){
        CursoUsuario cursoUsuario = read(idInscricao);

        if(cursoUsuario == null){
            return false;
        }

        boolean ok = super.delete(idInscricao);

        if(!ok){
            return false;
        }

        try {
            ParIdId parCurso = new ParIdId(cursoUsuario.getIdCurso(), idInscricao);
            ParIdId parUsuario = new ParIdId(cursoUsuario.getIdUsuario(), idInscricao);

            indiceCursos.delete(parCurso);
            indiceUsuarios.delete(parUsuario);

        } catch (Exception e) {
            System.out.println("Erro ao deletar\n" + e.getMessage());
        }

        return true;
    }

    @Override
    public boolean update(CursoUsuario cursoUsuario){
        CursoUsuario antigo = read(cursoUsuario.getId());

        if(antigo == null){
            return false;
        }

        boolean ok = super.update(cursoUsuario);

        if(!ok){
            return false;
        }

        try{
            if(antigo.getIdCurso() != cursoUsuario.getIdCurso()){
                indiceCursos.delete(
                        new ParIdId(
                                antigo.getIdCurso(),
                                cursoUsuario.getId()
                        )
                );

                indiceCursos.create(
                        new ParIdId(
                                cursoUsuario.getIdCurso(),
                                cursoUsuario.getId()
                        )
                );
            }

            if(antigo.getIdUsuario() != cursoUsuario.getIdUsuario()){
                indiceUsuarios.delete(
                        new ParIdId(
                                antigo.getIdUsuario(),
                                cursoUsuario.getId()
                        )
                );

                indiceUsuarios.create(
                        new ParIdId(
                                cursoUsuario.getIdUsuario(),
                                cursoUsuario.getId()
                        )
                );
            }
        }catch(Exception e){
            System.out.println("Erro ao atualizar indices\n" + e.getMessage());
        }

        return true;
    }

    public ArrayList<CursoUsuario> readByCurso(int idCurso){

        ArrayList<CursoUsuario> lista = new ArrayList<>();

        try{

            ArrayList<ParIdId> pares =
                    indiceCursos.read(new ParIdId(idCurso, -1));

            for(ParIdId par : pares){

                CursoUsuario cursoUsuario =
                        super.read(par.idInscricao);

                if(cursoUsuario != null){
                    lista.add(cursoUsuario);
                }
            }

        }catch(Exception e){
            System.out.println(
                    "Erro ao buscar inscrições do curso\n"
                    + e.getMessage()
            );
        }

        return lista;
    }

    public ArrayList<CursoUsuario> readByUsuario(int idUsuario){

        ArrayList<CursoUsuario> lista = new ArrayList<>();

        try{

            ArrayList<ParIdId> pares =
                    indiceUsuarios.read(
                            new ParIdId(idUsuario, -1)
                    );

            for(ParIdId par : pares){

                CursoUsuario cursoUsuario =
                        super.read(par.idInscricao);

                if(cursoUsuario != null){
                    lista.add(cursoUsuario);
                }
            }

        }catch(Exception e){
            System.out.println(
                    "Erro ao buscar inscrições do usuário\n"
                    + e.getMessage()
            );
        }

        return lista;
    }

    private void reconstruirIndices(){
        try{
            List<CursoUsuario> inscricoes = super.readAll();

            for(CursoUsuario cursoUsuario : inscricoes){
                indiceCursos.create(
                        new ParIdId(
                                cursoUsuario.getIdCurso(),
                                cursoUsuario.getId()
                        )
                );

                indiceUsuarios.create(
                        new ParIdId(
                                cursoUsuario.getIdUsuario(),
                                cursoUsuario.getId()
                        )
                );
            }
        }catch(Exception e){
            System.out.println("Erro ao reconstruir indices\n" + e.getMessage());
        }
    }

    private void limparArquivoIndice(String caminho) throws IOException{
        Files.deleteIfExists(Paths.get(caminho));
    }

    public static class ParIdId implements RegistroArvoreBMais<ParIdId>{
        public int idEstrangeiro;
        public int idInscricao;
        private final short TAMANHO = 8;

        public ParIdId(){
            this(-1, -1);
        }

        public ParIdId(int idEstrangeiro, int idInscricao){
            this.idEstrangeiro = idEstrangeiro;
            this.idInscricao = idInscricao;
        }

        public int getIdEstrangeiro(){
            return this.idEstrangeiro;
        }

        public int getIdInscricao(){
            return this.idInscricao;
        }

        @Override
        public ParIdId clone() {
            return new ParIdId(this.idEstrangeiro, this.idInscricao);
        }

        @Override
        public short size(){
            return TAMANHO;
        }

        @Override
        public int compareTo(ParIdId par){
            if(this.idEstrangeiro != par.idEstrangeiro){
                return Integer.compare(this.idEstrangeiro, par.idEstrangeiro);
            }

            if(this.idInscricao == -1 || par.idInscricao == -1){
                return 0;
            }

            return Integer.compare(this.idInscricao, par.idInscricao);
        }

        @Override
        public byte[] toByteArray() throws IOException {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            DataOutputStream da = new DataOutputStream(ba);
            da.writeInt(this.idEstrangeiro);
            da.writeInt(this.idInscricao);
            return ba.toByteArray();
        }

        @Override
        public void fromByteArray(byte[] buffer) throws IOException {
            ByteArrayInputStream ba = new ByteArrayInputStream(buffer);
            DataInputStream di = new DataInputStream(ba);
            this.idEstrangeiro = di.readInt();
            this.idInscricao = di.readInt();
        }
    }
}
