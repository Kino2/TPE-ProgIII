
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get("datos.txt"));

            int objetivo = Integer.parseInt(lineas.get(0).trim());
            List<Maquina> maquinas = new ArrayList<>();

            for (int i = 1; i < lineas.size(); i++) {
                String[] partes = lineas.get(i).split(",");
                String nombre = partes[0].trim();
                int piezas = Integer.parseInt(partes[1].trim());
                maquinas.add(new Maquina(nombre, piezas));
            }

            Backtracking backtrack = new Backtracking(objetivo, maquinas);
            backtrack.backtrack();
            System.out.println("------------------------------------------------");

            Greedy greedy = new Greedy(objetivo, maquinas);
            greedy.resolver();

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
