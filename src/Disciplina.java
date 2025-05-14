import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private List<String> preRequisitos;

    public Disciplina(String codigo, String nome, int cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }

    //Get e Set
    public String getCodigo() {
        return codigo; 
    }

    public String getNome(){
        return nome; 
    }
    public int getCargaHoraria(){
        return cargaHoraria; 
    }
    public List<String> getPreRequisitos(){ 
        return preRequisitos; 
    }

    public void adicionarPreRequisito(String codigoDisciplina) {
        preRequisitos.add(codigoDisciplina);
    }
}

