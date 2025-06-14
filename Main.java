
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get("E:\\Facultad\\Programacion III\\2025\\TPE\\datos.txt"));

            int objetivo = Integer.parseInt(lineas.get(0).trim());
            List<Maquina> maquinas = new ArrayList<>();

            for (int i = 1; i < lineas.size(); i++) {
                String[] partes = lineas.get(i).split(",");
                String nombre = partes[0].trim();
                int piezas = Integer.parseInt(partes[1].trim());
                maquinas.add(new Maquina(nombre, piezas));
            }

            Backtracking app = new Backtracking(objetivo, maquinas);
            app.backtrack();

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
