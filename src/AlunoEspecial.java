import java.util.List;

public class AlunoEspecial extends Aluno {
    public AlunoEspecial(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public boolean matricularDisciplina(String disciplina, int vagasDisponiveis, List<String> preRequisitos) {
        // Aluno especial pode ignorar pré-requisitos, mas só pode matricular em até 2 disciplinas
        if (getDisciplinasMatriculadas().size() >= 2) {
            return false;
        }
        
        if (isTrancado() || getDisciplinasMatriculadas().contains(disciplina) || vagasDisponiveis <= 0) {
            return false;
        }
        
        getDisciplinasMatriculadas().add(disciplina);
        return true;
    }
}