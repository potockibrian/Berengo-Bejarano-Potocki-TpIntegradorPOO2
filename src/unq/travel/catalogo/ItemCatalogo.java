package unq.travel.catalogo;

import java.util.List;
import java.util.Map;

import unq.travel.cupos.*;
import unq.travel.reportes.*;

public abstract class ItemCatalogo {

	private String nombre;
	private String descripcion;
	protected CalendarioDeCupos calendario;

	protected ItemCatalogo(String nombre, String descripcion, CalendarioDeCupos calendario) {

		this.nombre = nombre;
		this.descripcion = descripcion;
		this.calendario = calendario;

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

	protected static void validarDescuento(float descuento) {

		if (descuento < 0 || descuento > 1) {
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
		}

	}

	protected abstract float descuento();

	protected abstract float calcularPrecioBase();

	protected abstract boolean validarServicio();

	public abstract boolean esDeCategoria(Categoria categoria);

	public abstract boolean tieneDisponibilidad(RangoDeFechas rango);
	
	public abstract void aceptar(Visitor visitor);

	public abstract List<Cupo> cupos() ;

	protected abstract Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo);

	public abstract void reservar(RangoDeFechas rango);

	public abstract void liberar(RangoDeFechas rango);

	public boolean tieneDisponibilidad() {

		return calendario.tieneDisponibilidad();
		
	}

}
