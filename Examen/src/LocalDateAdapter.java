package Examen.src;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/MM/yyyy", Locale.ENGLISH);

    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.format(dateFormat);
    }

    @Override
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date, dateFormat);
    }
}
