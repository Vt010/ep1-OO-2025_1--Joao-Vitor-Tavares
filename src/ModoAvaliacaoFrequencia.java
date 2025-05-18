
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModoAvaliacaoFrequencia implements ModoSistema {
    private List<Avaliacao> avaliacoes;
    private List<Turma> turmas;
    private Scanner sc;

    public ModoAvaliacaoFrequencia(List<Turma> turmas){
        this.avaliacoes = new ArrayList<>();
        this.turmas = turmas;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void iniciarModo() {
        int opcao;
        do {
            System.out.println("\n--- MODO AVALIAÇÃO/FREQUÊNCIA ---");
            System.out.println("1. Lançar notas");
            System.out.println("2. Registrar faltas");
            System.out.println("3. Gerar relatório por turma");
            System.out.println("4. Gerar relatório por disciplina");
            System.out.println("5. Gerar relatório por professor");
            System.out.println("6. Gerar boletim do aluno");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine();
            
            switch (opcao) {
                case 1: lancarNotas();
                break;
                
                case 2: registrarFaltas();
                break;

                case 3: gerarRelatorioTurma();
                break;

                case 4: gerarRelatorioDisciplina();
                break;
                
                case 5: gerarRelatorioProfessor();
                break;
                
                case 6: gerarBoletimAluno();
                break;
                
                case 0: System.out.println("Retornando...");
                break;
                
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    private void lancarNotas(){
        System.out.println("\n--- LANÇAR NOTAS ---");
        System.out.print("Código da turma: ");
        String codigoTurma = sc.nextLine();

        System.out.print("Matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        System.out.print("Nota P1: ");
        double p1 = sc.nextDouble();
        System.out.print("Nota P2: ");
        double p2 = sc.nextDouble();
        System.out.print("Nota P3: ");
        double p3 = sc.nextDouble();
        System.out.print("Nota Listas: ");
        double listas = sc.nextDouble();
        System.out.print("Nota Seminário: ");
        double seminario = sc.nextDouble();
        sc.nextLine();
        
        Turma turma = buscarTurma(codigoTurma);
        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }
        Avaliacao avaliacao = buscarAvaliacao(matricula, codigoTurma);
        if (avaliacao == null) {
            avaliacao = new Avaliacao(matricula, codigoTurma, turma.getDisciplina().getCargaHoraria());
            avaliacoes.add(avaliacao);
        }
        
        avaliacao.setNotas(p1, p2, p3, listas, seminario);
        System.out.println("Notas lançadas com sucesso!");
    }
    private void registrarFaltas(){
        System.out.println("\n--- REGISTRAR FALTAS ---");
        System.out.print("Código da turma: ");
        String codigoTurma = sc.nextLine();

        System.out.print("Matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        System.out.print("Número de faltas: ");
        int faltas = sc.nextInt();
        sc.nextLine();
        
        Avaliacao avaliacao = buscarAvaliacao(matricula, codigoTurma);
        if (avaliacao == null) {
            System.out.println("Avaliação não encontrada!");
            return;
        }
        
        avaliacao.registarFaltas(faltas);
        System.out.println("Faltas registradas com sucesso!");
    }
    
    private void gerarRelatorioTurma() {
        System.out.print("\nCódigo da turma: ");
        String codigoTurma = sc.nextLine();
        
        Turma turma = buscarTurma(codigoTurma);
           if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
            }
            
       System.out.println("\n--- RELATÓRIO DA TURMA " + codigoTurma + " ---");
        System.out.println("Disciplina: " + turma.getDisciplina().getNome());
        System.out.println("Professor: " + turma.getProfessor());
        System.out.println("Forma de avaliação: " + turma.getFormaAvaliacao());
        System.out.println("------------------------------------------------");
            
        boolean formulaPonderada = turma.getFormaAvaliacao().contains("ponderada");
            
        for (String matricula : turma.getAlunosMatriculados()) {
            Avaliacao av = buscarAvaliacao(matricula, codigoTurma);
            if (av != null) {
                System.out.println("Aluno: " + matricula);
                System.out.printf("Média: %.1f | Frequência: %.1f%%\n", 
                                 av.calcularMedia(formulaPonderada), 
                                 av.calcularFrequencia());
                System.out.println("Situação: " + av.verificarSituacao(formulaPonderada));
                System.out.println("------------------------------------------------");
            }
        }
    }
    private void gerarRelatorioDisciplina() {
        System.out.print("\nCódigo da disciplina: ");
        String codigoDisciplina = sc.nextLine();
        
        System.out.println("\n--- RELATÓRIO POR DISCIPLINA ---");
        System.out.println("Disciplina: " + codigoDisciplina);
        System.out.println("------------------------------------------------");
        
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equals(codigoDisciplina)) {
                boolean formulaPonderada = turma.getFormaAvaliacao().contains("ponderada");
                int aprovados = 0, reprovadosNota = 0, reprovadosFalta = 0;
                
                for (String matricula : turma.getAlunosMatriculados()) {
                    Avaliacao av = buscarAvaliacao(matricula, turma.getCodigo());
                    if (av != null) {
                        String situacao = av.verificarSituacao(formulaPonderada);
                        if (situacao.equals("Aprovado")) aprovados++;
                        else if (situacao.equals("Reprovado por nota")) reprovadosNota++;
                        else reprovadosFalta++;
                    }
                }
                
                System.out.println("Turma: " + turma.getCodigo() + 
                                 " | Professor: " + turma.getProfessor());
                System.out.println("Aprovados: " + aprovados + 
                                 " | Reprovados por nota: " + reprovadosNota +
                                 " | Reprovados por falta: " + reprovadosFalta);
                System.out.println("------------------------------------------------");
            }
        }
    }
    private void gerarRelatorioProfessor() {
        System.out.print("\nNome do professor: ");
        String professor = sc.nextLine();
        
        System.out.println("\n--- RELATÓRIO POR PROFESSOR ---");
        System.out.println("Professor: " + professor);
        System.out.println("------------------------------------------------");
        
        for (Turma turma : turmas) {
            if (turma.getProfessor().equalsIgnoreCase(professor)) {
                boolean formulaPonderada = turma.getFormaAvaliacao().contains("ponderada");
                
                System.out.println("Disciplina: " + turma.getDisciplina().getNome() + 
                                 " | Turma: " + turma.getCodigo());
                
                for (String matricula : turma.getAlunosMatriculados()) {
                    Avaliacao av = buscarAvaliacao(matricula, turma.getCodigo());
                    if (av != null) {
                        System.out.printf("Aluno %s: Média %.1f | %s\n",
                            matricula,
                            av.calcularMedia(formulaPonderada),
                            av.verificarSituacao(formulaPonderada));
                    }
                }
                System.out.println("------------------------------------------------");
            }
        }
    }
    private void gerarBoletimAluno() {
        System.out.print("\nMatrícula do aluno: ");
        String matricula = sc.nextLine();
        
        System.out.print("\nSemestre (ex: 2023.1): ");
        String semestre = sc.nextLine();
        
        System.out.print("Mostrar dados da turma? (S/N): ");
        boolean mostrarTurma = sc.nextLine().equalsIgnoreCase("S");
        
        System.out.println("\n--- BOLETIM DO ALUNO ---");
        System.out.println("Matrícula: " + matricula);
        System.out.println("Semestre: " + semestre);
        System.out.println("------------------------------------------------");
        
        for (Turma turma : turmas) {
            if (turma.getSemestre().equals(semestre) && 
                turma.getAlunosMatriculados().contains(matricula)) {
                
                Avaliacao av = buscarAvaliacao(matricula, turma.getCodigo());
                if (av != null) {
                    boolean formulaPonderada = turma.getFormaAvaliacao().contains("ponderada");
                    
                    if (mostrarTurma) {
                        System.out.println("Disciplina: " + turma.getDisciplina().getNome());
                        System.out.println("Professor: " + turma.getProfessor());
                        System.out.println("Modalidade: " + (turma.isPresencial() ? "Presencial" : "Remota"));
                        System.out.println("Carga horária: " + turma.getDisciplina().getCargaHoraria() + "h");
                    }
                    
                    System.out.printf("P1: %.1f | P2: %.1f | P3: %.1f\n", 
                                    av.getP1(), av.getP2(), av.getP3());
                    System.out.printf("Listas: %.1f | Seminário: %.1f\n",
                                    av.getListas(), av.getSeminario());
                    System.out.printf("Média Final: %.1f\n", av.calcularMedia(formulaPonderada));
                    System.out.printf("Frequência: %.1f%%\n", av.calcularFrequencia());
                    System.out.println("Situação: " + av.verificarSituacao(formulaPonderada));
                    System.out.println("------------------------------------------------");
                }
            }
        }
    }
    
        //metodos auxiliares
        private Turma buscarTurma(String codigo) {
            for (Turma t : turmas) {
                if (t.getCodigo().equals(codigo)) {
                    return t;
                }
            }
            return null;
        }
    
        private Avaliacao buscarAvaliacao(String matricula, String codigoTurma) {
            for (Avaliacao a : avaliacoes) {
                if (a.getMatriculaAluno().equals(matricula) && 
                    a.getCodigoTurma().equals(codigoTurma)) {
                    return a;
                }
            }
            return null;
        }
        
    

}
