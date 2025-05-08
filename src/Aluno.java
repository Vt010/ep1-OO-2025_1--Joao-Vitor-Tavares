import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private List<String> disciplinasMatriculadas;
    private boolean trancado;

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.disciplinasMatriculadas = new ArrayList<>();
        this.trancado = false;
    }

    //Get e set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<String> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    public boolean isTrancado() {
        return trancado;
    }

    public boolean matricularDisciplina(String disciplina, int vagasDisponiveis, List<String> preRequisitos) {
        if (trancado || disciplinasMatriculadas.contains(disciplina) || vagasDisponiveis <= 0) {
            return false;
        }
        
        //Verifica pre requisitos
        for (String preReq : preRequisitos) {
            if (!disciplinasMatriculadas.contains(preReq)) {
                return false;
            }
        }
        
        disciplinasMatriculadas.add(disciplina);
        return true;
    }

    public boolean trancarDisciplina(String disciplina) {
        return disciplinasMatriculadas.remove(disciplina);
    }

    public void trancarSemestre() {
        trancado = true;
        disciplinasMatriculadas.clear();
    }

    public void destrancarSemestre() {
        trancado = false;
    }
}