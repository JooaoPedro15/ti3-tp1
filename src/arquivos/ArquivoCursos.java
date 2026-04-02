package arquivos;

import entidades.Curso;

import java.util.*;

public class ArquivoCursos {

    // armazenamento id -> curso
    private HashMap<Integer, Curso> dados;

    // relacionamento usuario -> lista de cursos
    private HashMap<Integer, List<Integer>> usuarioCursos;

    private int proximoId;

    public ArquivoCursos() {
        dados = new HashMap<>();
        usuarioCursos = new HashMap<>();
        proximoId = 1;
    }

    // cria curso
    public int create(Curso c) {

        c.setId(proximoId);

        dados.put(proximoId, c);

        // adiciona no relacionamento usuario -> cursos
        usuarioCursos
            .computeIfAbsent(c.getIdUsuario(), k -> new ArrayList<>())
            .add(proximoId);

        return proximoId++;
    }

    // le curso
    public Curso read(int id) {
        return dados.get(id);
    }

    // atualiza curso
    public boolean update(Curso c) {

        if(!dados.containsKey(c.getId()))
            return false;

        dados.put(c.getId(), c);
        return true;
    }

    // exclui curso
    public boolean delete(int id) {

        Curso c = dados.remove(id);

        if(c != null) {

            List<Integer> lista = usuarioCursos.get(c.getIdUsuario());

            if(lista != null)
                lista.remove(Integer.valueOf(id));

            return true;
        }

        return false;
    }

    // lista cursos de um usuario
    public List<Curso> listarPorUsuario(int idUsuario) {

        List<Curso> listaCursos = new ArrayList<>();

        List<Integer> ids = usuarioCursos.get(idUsuario);

        if(ids != null) {
            for(int id : ids) {
                listaCursos.add(dados.get(id));
            }
        }

        return listaCursos;
    }
}