import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModoAluno implements ModoSistema{
    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;
    private Scanner sc;

    public ModoAluno(List<Disciplina> disciplinas, List<Turma> turmas){
        this.alunos = new ArrayList<>();
        this.disciplinas = disciplinas;
        this.turmas = turmas;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void iniciarModo() {
        int opcao;
        do {
            System.out.println("\n--- MODO ALUNO ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Editar aluno");
            System.out.println("3. Listar alunos");
            System.out.println("4. Matricular aluno em disciplina");
            System.out.println("5. Trancar disciplina");
            System.out.println("6. Listar disciplinas matriculadas");
            System.out.println("7. Trancar semestre");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine(); 
            
            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    editarAluno();
                    break;
                case 3:
                    listarAlunos();
                    break;
                case 4:
                    matricularAlunoDisciplina();
                    break;
                case 5:
                    trancarDisciplina();
                    break;
                case 6:
                    listarDisciplinasMatriculadas(); 
                break;
                case 7:
                    trancarSemestre();
                    break;
                case 0:
                    System.out.println("\n Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarAluno() {
        System.out.println("\n--- CADASTRAR ALUNO ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        
        // Verificar se matrícula já existe
        if (alunoExiste(matricula)) {
            System.out.println("Erro: Matrícula já cadastrada!");
            return;
        }
        
        System.out.print("Curso: ");
        String curso = sc.nextLine();
        
        System.out.print("É aluno especial? (S/N): ");
        String especial = sc.nextLine();
        
        Aluno aluno;
        if (especial.equalsIgnoreCase("S")) {
            aluno = new AlunoEspecial(nome, matricula, curso);
        } else {
            aluno = new Aluno(nome, matricula, curso);
        }
        
        alunos.add(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private boolean alunoExiste(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }

    private void editarAluno() {
        System.out.println("\n--- EDITAR ALUNO ---");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
        
        System.out.println("Dados atuais:");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Curso: " + aluno.getCurso());
        
        System.out.print("Novo nome (deixe em branco para manter): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) {
            aluno.setNome(novoNome);
        }
        
        System.out.print("Novo curso (deixe em branco para manter): ");
        String novoCurso = sc.nextLine();
        if (!novoCurso.isEmpty()) {
            aluno.setCurso(novoCurso);
        }
        
        System.out.println("Dados atualizados com sucesso!");
    }

    private Aluno buscarAluno(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }

    private void listarAlunos() {
        System.out.println("\n--- LISTA DE ALUNOS ---");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        
        for (Aluno aluno : alunos) {
            System.out.println("Matrícula: " + aluno.getMatricula());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Curso: " + aluno.getCurso());
            System.out.println("Tipo: " + (aluno instanceof AlunoEspecial ? "Especial" : "Normal"));
            System.out.println("Status: " + (aluno.isTrancado() ? "Semestre trancado" : "Ativo"));
            System.out.println("Disciplinas matriculadas: " + aluno.getDisciplinasMatriculadas());
            System.out.println("--------------------");
        }
    }

    //Métodos simplificados para demonstração
    private void matricularAlunoDisciplina() {
        System.out.println("\n--- MATRICULAR ALUNO EM DISCIPLINA ---");
        
        // 1. Selecionar aluno
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
    
        // 2. Listar disciplinas disponíveis
        System.out.println("\nDisciplinas disponíveis:");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.printf("%d - %s (%s)\n", i+1, disciplinas.get(i).getNome(), 
                              disciplinas.get(i).getCodigo());
        }
        
        System.out.print("Escolha o número da disciplina: ");
        int escolhaDisciplina = sc.nextInt() - 1;
        sc.nextLine();
        
        if (escolhaDisciplina < 0 || escolhaDisciplina >= disciplinas.size()) {
            System.out.println("Disciplina inválida!");
            return;
        }
        Disciplina disciplinaEscolhida = disciplinas.get(escolhaDisciplina);
    
        // 3. Listar turmas disponíveis para esta disciplina
        System.out.println("\nTurmas disponíveis para " + disciplinaEscolhida.getNome() + ":");
        List<Turma> turmasDisciplina = new ArrayList<>();
        for (Turma t : turmas) {
            if (t.getDisciplina().getCodigo().equals(disciplinaEscolhida.getCodigo())) {
                turmasDisciplina.add(t);
            }
        }
    
        if (turmasDisciplina.isEmpty()) {
            System.out.println("Nenhuma turma disponível para esta disciplina!");
            return;
        }
    
        for (int i = 0; i < turmasDisciplina.size(); i++) {
            Turma t = turmasDisciplina.get(i);
            System.out.printf("%d - %s | Professor: %s | Vagas: %d/%d\n",
                             i+1, t.getHorario(), t.getProfessor(),
                             t.getAlunosMatriculados().size(), t.getCapacidadeMaxima());
        }
    
        System.out.print("Escolha o número da turma: ");
        int escolhaTurma = sc.nextInt() - 1;
        sc.nextLine();
    
        if (escolhaTurma < 0 || escolhaTurma >= turmasDisciplina.size()) {
            System.out.println("Turma inválida!");
            return;
        }
    
        // 4. Realizar matrícula
        Turma turmaEscolhida = turmasDisciplina.get(escolhaTurma);
        if (turmaEscolhida.getAlunosMatriculados().size() >= turmaEscolhida.getCapacidadeMaxima()) {
            System.out.println("Turma lotada!");
            return;
        }
    
        // Verificar pré-requisitos (se não for aluno especial)
        if (!(aluno instanceof AlunoEspecial)) {
            for (String preReq : disciplinaEscolhida.getPreRequisitos()) {
                boolean temPreReq = false;
                for (String discMatriculada : aluno.getDisciplinasMatriculadas()) {
                    if (discMatriculada.equals(preReq)) {
                        temPreReq = true;
                        break;
                    }
                }
                if (!temPreReq) {
                    System.out.println("Aluno não tem o pré-requisito: " + preReq);
                    return;
                }
            }
        }
    
        // Verificar limite de disciplinas para aluno especial
        if (aluno instanceof AlunoEspecial && aluno.getDisciplinasMatriculadas().size() >= 2) {
            System.out.println("Alunos especiais só podem se matricular em 2 disciplinas!");
            return;
        }
    
        // Tudo ok, fazer matrícula
        turmaEscolhida.matricularAluno(aluno.getMatricula());
        aluno.getDisciplinasMatriculadas().add(disciplinaEscolhida.getCodigo());
        System.out.println("Matrícula realizada com sucesso na turma " + 
                          turmaEscolhida.getHorario() + "!");
    }

    private void trancarDisciplina() {
        System.out.println("\n--- TRANCAR DISCIPLINA ---");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
        
        System.out.print("Digite o nome da disciplina a trancar: ");
        String disciplina = sc.nextLine();
        
        if (aluno.trancarDisciplina(disciplina)) {
            System.out.println("Disciplina trancada com sucesso!");
        } else {
            System.out.println("Não foi possível trancar a disciplina. Verifique se o aluno está matriculado nela.");
        }
    }

    private void listarDisciplinasMatriculadas() {
        System.out.println("\n--- DISCIPLINAS MATRICULADAS ---");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
        
        System.out.println("\nDisciplinas matriculadas por " + aluno.getNome() + ":");
        List<String> disciplinas = aluno.getDisciplinasMatriculadas();
        
        if (disciplinas.isEmpty()) {
            System.out.println("O aluno não está matriculado em nenhuma disciplina.");
        } else {
            for (String disciplina : disciplinas) {
                System.out.println("- " + disciplina);
            }
        }
    }

    private void trancarSemestre() {
        System.out.println("\n--- TRANCAR SEMESTRE ---");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
        
        aluno.trancarSemestre();
        System.out.println("Semestre trancado com sucesso! Todas as disciplinas foram removidas.");
    }

}
    
    
    
    
