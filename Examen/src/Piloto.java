package Examen.src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Piloto {
    private String nombre;
    private int numero;
    private String equipo;
    private LocalDate fechaNacimiento;

    // Getters
    public String getNombre() { return nombre; }
    public int getNumero() { return numero;}
    public String getEquipo() { return equipo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setEquipo(String equipo) { this.equipo = equipo; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Override
    public String toString() {
        return "Piloto{" +
                "nombre='" + nombre + '\'' +
                ", numero=" + numero +
                ", equipo='" + equipo + '\'' +
                ", fechaNacimiento=" + fechaNacimiento.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) +
                '}';
    }
}
