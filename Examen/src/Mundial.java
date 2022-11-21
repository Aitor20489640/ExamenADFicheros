package Examen.src;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mundial {

    public static List<Piloto> leerPilotos() {

        Path ruta = Path.of("Examen/data/pilotos.xml");
        List<Piloto> pilotoList;
        try {
            Pilotos pilotos = unmarshall(ruta);
            pilotoList = pilotos.getPilotoList();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return pilotoList;
    }
    
    public static List<Circuito> leerCircuitos() {
        List<Circuito> circuitoList;
        Path ruta = Path.of("Examen/data/circuitos.csv");

        try (Stream<String> lines = Files.lines(ruta).skip(1)) {
            circuitoList = lines.map(Mundial::crearCircuito).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return circuitoList;
    }

    public static List<Resultado> leerResultados(List<Circuito> circuitos, List<Piloto> pilotos) {
        Path ruta = Path.of("Examen/data/resultados.json");
        List<Resultado> resultadoList;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        SimpleModule module = new SimpleModule("CustomResultadoDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Resultado.class, new CustomResultadoDeserializer(Resultado.class, circuitos, pilotos));
        objectMapper.registerModule(module);
        try {
            resultadoList = objectMapper.readValue(ruta.toFile(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultadoList;
    }

    public static void imprimirClasificacionFinal(List<Resultado> resultados) {
        System.out.println("Clasificación Pilotos F1 2019:");
        resultados.stream().collect(Collectors.groupingBy(
                resultado -> resultado.getPiloto().getNombre(),
                Collectors.summingDouble(Resultado::getPuntos))).
                entrySet()
                .stream()
                .sorted((d1, d2) -> Double.compare(d2.getValue(), d1.getValue())).toList()
                .forEach(show -> System.out.println(show.getKey() + ": " + show.getValue() + " puntos"));
    }

    public static void imprimirMayoresde30(List<Piloto> pilotos) {
        System.out.println("Pilotos mayores de 30 años:");
        pilotos.stream()
                .filter(d -> calculateAge(d.getFechaNacimiento()) >= 30)
                .sorted((d1, d2) -> Integer.compare(calculateAge(d2.getFechaNacimiento()), calculateAge(d1.getFechaNacimiento())))
                .forEach(d -> System.out.println(d.getNombre() + " tiene " + calculateAge(d.getFechaNacimiento()) + "años"));

    }

    public static Circuito crearCircuito(String line) {
        String[] linea = line.split(",");
        Circuito circuito = new Circuito();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

        circuito.setRonda(Integer.parseInt(linea[0]));
        circuito.setPais(linea[1]);
        circuito.setFechaCarrera(LocalDate.parse(linea[2], dateFormat));

        return circuito;
    }

    public static Pilotos unmarshall(Path ruta) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Pilotos.class);
        return (Pilotos) context.createUnmarshaller()
                .unmarshal(ruta.toFile());
    }

    public static int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        if (dob != null) {
            return Period.between(dob, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        List<Piloto> pilotos = leerPilotos();
		// System.out.println(pilotos);

        List<Circuito> circuitos = leerCircuitos();
		// System.out.println(circuitos);

        List<Resultado> resultados = leerResultados(circuitos, pilotos);
        // System.out.println(resultados);

        imprimirClasificacionFinal(resultados);

        imprimirMayoresde30(pilotos);
    }
}
