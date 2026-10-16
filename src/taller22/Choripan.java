package taller22;

public class Choripan {
    private Participante consumidor;
    private String fecha;
    private int largo;
    private int ancho;

    public Choripan(Participante consumidor, String fecha, int largo, int ancho) {
        this.consumidor = consumidor;
        this.fecha = fecha;
        this.largo = largo;
        this.ancho = ancho;
    }

    public Participante getConsumidor() {
        return consumidor;
    }

    public String getFecha() {
        return fecha;
    }

    public int getLargo() {
        return largo;
    }

    public int getAncho() {
        return ancho;
    }
}