import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaAcademicoFCTE {
    private Scanner sc;
    private ModoAluno modoAluno;
    private ModoDisciplinaTurma modoDisciplinaTurma;
    private ModoAvaliacaoFrequencia modoAvaliacaoFrequencia;
    
    public SistemaAcademicoFCTE() {
        this.sc = new Scanner(System.in);
        List<Disciplina> disciplinasCompartilhadas = new ArrayList<>();
        List<Turma> turmasCompartilhadas = new ArrayList<>();

        this.modoAvaliacaoFrequencia = new ModoAvaliacaoFrequencia(turmasCompartilhadas);
        this.modoDisciplinaTurma = new ModoDisciplinaTurma(disciplinasCompartilhadas, turmasCompartilhadas);
        this.modoAluno = new ModoAluno(disciplinasCompartilhadas, turmasCompartilhadas);
    }
    
    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA ACADÊMICO ===");
            System.out.println("1. Modo Aluno");
            System.out.println("2. Modo Disciplina/Turma");
            System.out.println("3. Modo Avaliação/Frequência");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine(); //pular a linha
            
            switch (opcao) {
                case 1:
                    System.out.println("\nModo Aluno selecionado!\n");
                    modoAluno.iniciarModo();
                    break;
                case 2:
                    System.out.println("\nModo Disciplina selecionado!");
                    modoDisciplinaTurma.iniciarModo();
                    break;
                case 3:
                    System.out.println("\nModo Avaliação selecionado!");
                    modoAvaliacaoFrequencia.iniciarModo();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        sc.close();
    }
}
