package Examen.src;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomResultadoDeserializer extends StdDeserializer<Resultado> {
    private List<Piloto> pilotos;
    private List<Circuito> circuitos;


    public CustomResultadoDeserializer(Class<?> vc, List<Circuito> circuitoList, List<Piloto> pilotoList) {
        super(vc);
        pilotos = pilotoList;
        circuitos = circuitoList;
    }

    @Override
    public Resultado deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Resultado resultado = new Resultado();
        String circuito, piloto;
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode circuitoNode, pilotoNode, posicionNode, puntosNode;

        circuitoNode = node.get("Track");
        circuito = circuitoNode.asText();

        resultado.setCircuito(circuitos.stream()
                .filter(track -> circuito.equals(track.getPais()))
                .findAny()
                .orElse(null));

        pilotoNode = node.get("Driver");
        piloto = pilotoNode.asText();

        resultado.setPiloto(pilotos.stream()
                .filter(driver -> piloto.equals(driver.getNombre()))
                .findAny()
                .orElse(null));

        posicionNode = node.get("Position");
        resultado.setPosicion((Objects.equals(posicionNode.asText(), "NC")) ? -1 : posicionNode.asInt());

        puntosNode = node.get("Points");
        resultado.setPuntos(puntosNode.asDouble());

        return resultado;


    }
}
