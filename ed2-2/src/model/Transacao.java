package model;

public class Transacao implements Comparable<Transacao> {
    private String id;
    private float valor;
    private String origem;
    private String destino;
    private String timestamp;

    public Transacao(String id, String origem, String destino, float valor, String timestamp) {
        this.id = id;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.timestamp = timestamp;
    }

    // Getters
    public String getId() {
        return id;
    }

    public float getValor() {
        return valor;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Implementação de Comparable
    @Override
    public int compareTo(Transacao outra) {
        return this.timestamp.compareTo(outra.timestamp);
    }

    @Override
    public String toString() {
        return "ID:" + id +"  | origem:" + origem + "  | valor:" + valor +"  | destino='" + destino+"  | timestamp:'" + timestamp;
    }
}
