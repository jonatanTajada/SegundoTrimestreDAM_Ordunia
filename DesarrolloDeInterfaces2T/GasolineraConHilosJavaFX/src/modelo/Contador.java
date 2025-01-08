package modelo;

public class Contador {
    private int valor = 0;

    public void incrementar() {
        valor++;
    }

    public void setValor(int valor) {
		this.valor = valor;
	}

	public int getValor() {
        return valor;
    }
}

