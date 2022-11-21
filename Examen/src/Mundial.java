package Examen.src;

import java.util.List;

public class Mundial {

    public static List<Piloto> leerPilotos() {

    }
    
    public static List<Circuito> leerCircuitos() {

    }

    public static List<Resultado> leerResultados(List<Circuito> circuitos, List<Piloto> pilotos) {

    }

    public static void imprimirClasificacionFinal(List<Resultado> resultados) {

    }

    public static void imprimirMayoresde30(List<Piloto> pilotos) {

    }

    public static void main(String[] args) {
        List<Piloto> pilotos = leerPilotos();
		// System.out.println(pilotos);

        List<Circuito> circuitos = leerCircuitos();
		// System.out.println(circuitos);

        List<Resultado> resultados = leerResultados(circuitos, pilotos);

        imprimirClasificacionFinal(resultados);

        imprimirMayoresde30(pilotos);
    }
}
