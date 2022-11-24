package Examen.src;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CustomResultadoDeserializer extends StdDeserializer<Resultado> {
    private Map<String, Piloto> pilotos;
    private Map<String, Circuito> circuitos;


    public CustomResultadoDeserializer(Class<?> vc, Map<String, Circuito> circuitoList, Map<String, Piloto> pilotoList) {
        super(vc);
        pilotos = pilotoList;
        circuitos = circuitoList;
    }

    @Override
    public Resultado deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Resultado resultado = new Resultado();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode posicionNode, puntosNode;

        resultado.setCircuito(circuitos.get(node.get("Track").asText()));

        resultado.setPiloto(pilotos.get(node.get("Driver").asText()));

        posicionNode = node.get("Position");
        resultado.setPosicion((Objects.equals(posicionNode.asText(), "NC")) ? -1 : posicionNode.asInt());

        puntosNode = node.get("Points");
        resultado.setPuntos(puntosNode.asDouble());

        return resultado;


    }
}
