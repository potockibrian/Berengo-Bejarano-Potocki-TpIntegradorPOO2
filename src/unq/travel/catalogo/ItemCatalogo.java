package unq.travel.catalogo;

public abstract class ItemCatalogo {

	private String nombre;
	private String descripcion;

	protected ItemCatalogo(String nombre, String descripcion) {

		this.nombre = nombre;
		this.descripcion = descripcion;

	}

	public String getNombre() {

		return nombre;

	}

	public String getDescripcion() {

		return descripcion;

	}

	public final float getPrecioBase() {

		return this.calcularPrecioBase();

	}

	public final float getPrecioFinal() {

		return this.calcularPrecioBase() * (1 - this.descuento());

	}

	public final boolean esValido() {

		return this.nombre != null && this.validarServicio();

	}

	protected void validarDescuento(float descuento) {

		if (descuento < 0 || descuento > 1) {
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
		}

	}

	protected abstract float descuento();

	protected abstract float calcularPrecioBase();

	protected abstract boolean validarServicio();

	public abstract boolean esDeCategoria(Categoria categoria);

	public abstract boolean tieneDisponibilidad();

	public abstract void decrementarCupo();

	public abstract void incrementarCupo();

}
