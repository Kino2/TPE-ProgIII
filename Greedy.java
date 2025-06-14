
import java.util.ArrayList;
import java.util.List;

public class Greedy {

    private int objetivo;
    private List<Maquina> maquinas;
    private int estados;
    private List<Maquina> mejorSolucion;

    public Greedy(int objetivo, List<Maquina> maquinas) {
        this.objetivo = objetivo;
        this.maquinas = maquinas;
        this.estados = 0;
        this.mejorSolucion = new ArrayList<>();
    }

    public void greedy() {
        ordenarMaquinasPorPiezasDesc();
        
    }

    private void ordenarMaquinasPorPiezasDesc() {
        maquinas.sort((a, b) -> b.getPiezas() - a.getPiezas());
    }
}
