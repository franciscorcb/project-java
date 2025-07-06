package service;

import java.util.ArrayList;
import java.util.List;

public class HashQuadratico<Key, Value> {
    private int N; // Número de pares chave-valor
    private int M = 512; // Tamanho da tabela hash
    private Key[] keys;
    private Value[] vals;

    @SuppressWarnings("unchecked")
    public HashQuadratico() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    @SuppressWarnings("unchecked")
    public HashQuadratico(int cap) {
        keys = (Key[]) new Object[cap];
        vals = (Value[]) new Object[cap];
        M = cap;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {
        HashQuadratico<Key, Value> t = new HashQuadratico<>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                t.put(keys[i], vals[i]);
            }
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    public void put(Key key, Value val) {
        putWithCount(key, val);
    }

    // Novo método que retorna quantidade de colisões até inserir
    public int putWithCount(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Chave nula");
        if (val == null) {
            delete(key);
            return 0;
        }

        if (N >= M / 2) {
            resize(2 * M);
        }

        int hashInicial = hash(key);
        int pos = hashInicial;
        int colisoes = 0;

        if (keys[pos] == null) {
            keys[pos] = key;
            vals[pos] = val;
            N++;
            return colisoes;
        } else if (keys[pos].equals(key)) {
            vals[pos] = val;
            return colisoes;
        }

        for (int k = 1; k < M; k++) {
            pos = (hashInicial + k * k) % M;
            colisoes++;
            if (keys[pos] == null) {
                keys[pos] = key;
                vals[pos] = val;
                N++;
                return colisoes;
            } else if (keys[pos].equals(key)) {
                vals[pos] = val;
                return colisoes;
            }
        }

        throw new RuntimeException("Falha na insercao com sondagem quadratica.");
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Chave nula");

        int i = hash(key);
        int j = 0;

        while (true) {
            int pos = (i + j * j) % M;
            if (keys[pos] == null) return null;
            if (keys[pos].equals(key)) return vals[pos];
            j++;
        }
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Chave nula");

        int hashInicial = hash(key);
        int pos = hashInicial;

        if (keys[pos] == null || !keys[pos].equals(key)) {
            boolean found = false;
            int k = 1;
            while (k < M) {
                pos = (hashInicial + (k * (k + 1)) / 2) % M;
                if (keys[pos] == null) break;
                if (keys[pos].equals(key)) {
                    found = true;
                    break;
                }
                k++;
            }
            if (!found) return;
        }

        keys[pos] = null;
        vals[pos] = null;

        int k = 1;
        int next = (pos + (k * (k + 1)) / 2) % M;
        while (keys[next] != null) {
            Key keyToRedo = keys[next];
            Value valToRedo = vals[next];
            keys[next] = null;
            vals[next] = null;
            N--;
            put(keyToRedo, valToRedo);

            k++;
            next = (pos + (k * (k + 1)) / 2) % M;
        }

        N--;
        if (N > 0 && N == M / 8) {
            resize(M / 2);
        }
    }

    // Getters seguros
    public int getCapacidade() {
        return M;
    }

    public Key getKeyAt(int index) {
        return keys[index];
    }

    public Value getValAt(int index) {
        return vals[index];
    }

    // Retorna todas as transações com determinada chave (origem)
    public List<Value> listarPorChave(Key chave) {
        List<Value> lista = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null && keys[i].equals(chave)) {
                lista.add(vals[i]);
            }
        }
        return lista;
    }
}
