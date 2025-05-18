public class Avaliacao {
    private String matriculaAluno;
    private String codigoTurma;
    private double p1;
    private double p2;
    private double p3;
    private double listas;
    private double seminario;
    private int faltas;
    private int cargaHorariaTotal;

    public Avaliacao(String matriculaAluno, String codigoTurma, int cargaHorariaTotal) {
        this.matriculaAluno = matriculaAluno;
        this.codigoTurma = codigoTurma;
        this.cargaHorariaTotal = cargaHorariaTotal;
    }

    // get e set
    public double getP1() {
        return p1;
    }
    
    public double getP2() {
        return p2;
    }

    public double getP3() {
       return p3;
    }
    public double getListas() {
        return listas;
    }

    public double getSeminario() {
        return seminario;
    }

    public String getMatriculaAluno(){
        return matriculaAluno;
    }

    public String getCodigoTurma(){
        return codigoTurma;
    }

    public void setNotas(double p1, double p2, double p3, double listas, double seminario) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.listas = listas;
        this.seminario = seminario;
    }

    public void registarFaltas(int faltas) {
        this.faltas = faltas;
    }

    public double calcularMedia(boolean formulaPonderada) {
        if (formulaPonderada) {
            return (p1 + p2 * 2 + p3 * 3 + listas + seminario) / 8;
        } else {
            return (p1 + p2 + p3 + listas + seminario) / 5;
        }
    }

    public double calcularFrequencia() {
        int totalAulas = cargaHorariaTotal /2; //Considerando aulas de 2h
        int presencas = totalAulas - faltas;
        return (double) presencas / totalAulas * 100;
    }

    public String verificarSituacao(boolean formulaPonderada) {
        double media = calcularMedia(formulaPonderada);
        double frequencia = calcularFrequencia();

        if (frequencia < 75) {
            return "Reprovado por falta";
        } else if (media < 5) {
            return "Reprovado por nota";
        } else {
            return "Aprovado";
        }
    }
}

