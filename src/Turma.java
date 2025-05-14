import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigo;
    private Disciplina disciplina;
    private String professor;
    private String semestre;
    private String formaAvaliacao;
    private boolean presencial;
    private String sala;                     //pode ser null se for remota
    private String horario;
    private int capacidadeMaxima;
    private List<String> alunosMatriculados; // Lista de matrículas

    public Turma(String codigo, Disciplina disciplina, String professor, String semestre, String formaAvaliacao, boolean presencial, String sala, String horario, int capacidadeMaxima) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = presencial ? sala : null;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = new ArrayList<>();
    }

    // Get
    public String getCodigo(){
        return codigo;
    }

    public Disciplina getDisciplina(){ 
        return disciplina;
    }

    public String getProfessor(){
        return professor;
    }

    public String getSemestre(){
        return semestre;
    }

    public String getFormaAvaliacao(){
        return formaAvaliacao;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getSala() {
        return sala;  
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public List<String> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public int getVagasDisponiveis() {
        return capacidadeMaxima - alunosMatriculados.size();
    }

    public void matricularAluno(String matricula) {
        if (alunosMatriculados.size() < capacidadeMaxima) {
            alunosMatriculados.add(matricula);
        }
    }

    public void listarAlunos() {
        System.out.println("Alunos matriculados (" + alunosMatriculados.size() + "/" + capacidadeMaxima + "):");
        for (String matricula : alunosMatriculados) {
            System.out.println("- " + matricula);
        }
    }
}
