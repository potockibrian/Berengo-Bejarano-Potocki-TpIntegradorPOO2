package unq.travel.catalogo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import unq.travel.cupos.*;
import unq.travel.reportes.*;

public abstract class ItemCatalogo {

	protected String nombre;
	protected String descripcion;

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

	public abstract float getPrecioBase() ;

	public final float getPrecioFinal() {

		return this.getPrecioBase() * (1 - this.descuento());

	}

	/*
	public final boolean esValido() {

		return this.nombre != null && this.validarServicio();

	}

	protected abstract boolean validarServicio();
	*/
	
	protected static void validarDescuento(float descuento) {

		if (descuento < 0 || descuento > 1) {
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
		}

	}

	protected abstract float descuento();

	public abstract boolean esDeCategoria(Categoria categoria);

	public abstract boolean tieneDisponibilidad();
	
	public abstract boolean tieneDisponibilidad(RangoDeFechas rango);

	public abstract Cupo cuposEnPeriodoDeCategoria(RangoDeFechas periodo, Categoria categoria);

	public abstract RegistroVentaPeriodo cantidadYTotalCobradoDeVentasEnPeriodo(RangoDeFechas periodo);
	
	public abstract BolsaDeProveedores proveedores();

	public abstract void reservar(RangoDeFechas rango);

	public abstract void liberar(RangoDeFechas rango);

	public abstract Set<Categoria> categorias();

	public abstract void agregarCupoEnPeriodo(RangoDeFechas periodo, int cantidad);

}
