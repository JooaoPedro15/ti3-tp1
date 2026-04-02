package arquivos;

import entidades.Usuario;
import java.util.HashMap;

public class ArquivoUsuarios {

    // simula armazenamento em memoria id -> usuario
    private HashMap<Integer, Usuario> dados;

    // indice por email
    private HashMap<String, Integer> indiceEmail;

    private int proximoId;

    public ArquivoUsuarios() {
        dados = new HashMap<>();
        indiceEmail = new HashMap<>();
        proximoId = 1;
    }

    // cria usuario
    public int create(Usuario u) {

        u.setId(proximoId);

        dados.put(proximoId, u);

        // atualiza indice por email
        indiceEmail.put(u.getEmail(), proximoId);

        return proximoId++;
    }

    // busca por id
    public Usuario read(int id) {
        return dados.get(id);
    }

    // busca por email (usado no login)
    public Usuario buscarPorEmail(String email) {

        Integer id = indiceEmail.get(email);

        if(id != null)
            return dados.get(id);

        return null;
    }

    // atualiza usuario
    public boolean update(Usuario u) {

        if(!dados.containsKey(u.getId()))
            return false;

        dados.put(u.getId(), u);

        // atualiza indice email
        indiceEmail.put(u.getEmail(), u.getId());

        return true;
    }

    // exclui usuario
    public boolean delete(int id) {

        Usuario u = dados.remove(id);

        if(u != null) {
            indiceEmail.remove(u.getEmail());
            return true;
        }

        return false;
    }
}