package Examen.src;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "drivers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pilotos {

    @XmlElement(name = "driver")
    private ArrayList<Piloto> pilotoList = new ArrayList<>();

    public Pilotos () {
    }

    public ArrayList<Piloto> getPilotoList() {
        return pilotoList;
    }

    public void setPilotoList(ArrayList<Piloto> pilotoList) {
        this.pilotoList = pilotoList;
    }
}
