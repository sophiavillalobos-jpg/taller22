package taller22;

public class Terremoto {
    private Participante consumidor;
    private String fecha;
    private double litros;

    public Terremoto(Participante consumidor, String fecha, double litros) {
        this.consumidor = consumidor;
        this.fecha = fecha;
        this.litros = litros;
    }

    public Participante getConsumidor() {
        return consumidor;
    }

    public String getFecha() {
        return fecha;
    }

    public double getLitros() {
        return litros;
    }
}
