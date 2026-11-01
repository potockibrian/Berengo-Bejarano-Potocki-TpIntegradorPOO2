package unq.travel.catalogo;

public class AtributoDinamico {

	private String nombre;
	private boolean obligatorio;
	private TipoAtributo tipo;
	private Object valor;

	public AtributoDinamico(String nombre, boolean obligatorio, TipoAtributo tipo) {

		this.nombre = nombre;
		this.obligatorio = obligatorio;
		this.tipo = tipo;

	}

	public boolean tieneNombre(String nombre) {

		return this.nombre.equals(nombre);

	}

	public void asignarValor(Object valor) {

		this.valor = valor;

	}

	public Object getValor() {

		if (!this.tieneValor()) {
			throw new AtributoSinValorException("El atributo " + nombre + " no tiene valor asignado");
		}
		return valor;

	}

	public boolean tieneValor() {

		return valor != null;

	}

	public boolean esValido() {

		if (this.tieneValor()) {
			return tipo.esValorValido(valor);
		} else {
			return !obligatorio;
		}

	}

}
