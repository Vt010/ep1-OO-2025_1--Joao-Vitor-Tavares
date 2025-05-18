import java.util.List;
import java.util.Scanner;

public class ModoDisciplinaTurma implements ModoSistema {
    
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;
    private Scanner sc;

    public ModoDisciplinaTurma(List<Disciplina> disciplinas, List<Turma> turmas) {
        this.disciplinas = disciplinas;
        this.turmas = turmas;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void iniciarModo() {
        int opcao;
        do {
            System.out.println("\n--- MODO DISCIPLINA/TURMA ---");
            System.out.println("1. Cadastrar disciplina");
            System.out.println("2. Adicionar pré-requisito");
            System.out.println("3. Criar turma");
            System.out.println("4. Listar disciplinas");
            System.out.println("5. Listar turmas");
            System.out.println("6. Listar alunos de turma");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1: 
                cadastrarDisciplina(); 
                break;
                case 2: 
                adicionarPreRequisito(); 
                break;
                case 3: 
                criarTurma();
                break;
                case 4:
                listarDisciplinas();
                break;
                case 5: 
                listarTurmas();
                break;
                case 6: 
                listarAlunosTurma(); 
                break;
                case 0: 
                System.out.println("Retornando..."); 
                break;
                default: 
                System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarDisciplina() {
        System.out.println("\n--- CADASTRAR DISCIPLINA ---");
        System.out.print("Código: ");
        String codigo = sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Carga horária: ");
        int cargaHoraria = sc.nextInt();
        sc.nextLine();
        
        Disciplina novaDisciplina = new Disciplina(codigo, nome, cargaHoraria);
        disciplinas.add(novaDisciplina);
        System.out.println("Disciplina cadastrada com sucesso!");
    }

    private void adicionarPreRequisito() {
        System.out.println("\n--- ADICIONAR PRÉ-REQUISITO ---");
        System.out.print("Código da disciplina: ");
        String codigo = sc.nextLine();
        
        Disciplina disciplina = buscarDisciplina(codigo);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }
        
        System.out.print("Código da disciplina pré-requisito: ");
        String codigoPreReq = sc.nextLine();
        disciplina.adicionarPreRequisito(codigoPreReq);
        System.out.println("Pré-requisito adicionado!");
    }

    private void criarTurma() {
        System.out.println("\n--- CRIAR TURMA ---");
        System.out.print("Código da disciplina: ");
        String codigoDisciplina = sc.nextLine();
        
        Disciplina disciplina = buscarDisciplina(codigoDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }
        
        System.out.print("Código da turma: ");
        String codigoTurma = sc.nextLine();
        System.out.print("Professor responsável: ");
        String professor = sc.nextLine();
        System.out.print("Semestre (ex: 2023.1): ");
        String semestre = sc.nextLine();
        System.out.print("Forma de avaliação: ");
        String formaAvaliacao = sc.nextLine();
        System.out.print("É presencial? (S/N): ");
        boolean presencial = sc.nextLine().equalsIgnoreCase("S");
        
        String sala = null;
        if (presencial) {
            System.out.print("Sala: ");
            sala = sc.nextLine();
        }
        
        System.out.print("Horário: ");
        String horario = sc.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = sc.nextInt();
        sc.nextLine();
        
        Turma novaTurma = new Turma(codigoTurma, disciplina, professor, semestre, formaAvaliacao, presencial, sala, horario, capacidade);
        turmas.add(novaTurma);
        System.out.println("Turma criada com sucesso!");
    }

    private void listarDisciplinas() {
        System.out.println("\n--- DISCIPLINAS CADASTRADAS ---");
        for (Disciplina d : disciplinas) {
            System.out.println(d.getCodigo() + " - " + d.getNome() + 
                             " (" + d.getCargaHoraria() + "h)");
            System.out.println("Pré-requisitos: " + d.getPreRequisitos());
        }
    }

    private void listarTurmas() {
        System.out.println("\n--- TURMAS DISPONÍVEIS ---");
        for (Turma t : turmas) {
            System.out.println(t.getCodigo() + " - " + t.getDisciplina().getNome());
            System.out.println("Professor: " + t.getProfessor() + 
                             " | Horário: " + t.getHorario());
            System.out.println("Vagas: " + t.getAlunosMatriculados().size() + 
                             "/" + t.getCapacidadeMaxima());
        }
    }

    private void listarAlunosTurma() {
        System.out.print("\nCódigo da turma: ");
        String codigoTurma = sc.nextLine();
        
        Turma turma = buscarTurma(codigoTurma);
        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }
        
        turma.listarAlunos();
    }

    // Métodos auxiliares
    private Disciplina buscarDisciplina(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equals(codigo)) {
                return d;
            }
        }
        return null;
    }

    private Turma buscarTurma(String codigo) {
        for (Turma t : turmas) {
            if (t.getCodigo().equals(codigo)) {
                return t;
            }
        }
        return null;
    }






}
