package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import unq.travel.cupos.*;
import unq.travel.reportes.*;

public class ServicioIndividual extends ItemCatalogo {

	private String codigoReserva;
	private String proveedor;
	private Categoria categoria;
	private float precioBase;
	private float descuentoPromocional;
	private int stock;
	private List<AtributoDinamico> atributosDinamicos;
	private CalendarioDeCupos calendario;

	public ServicioIndividual(String nombre, String descripcion, String codigoReserva, String proveedor,
			Categoria categoria, float precioBase, float descuentoPromocional, int stock, CalendarioDeCupos calendario) {

		super(nombre, descripcion);
		validarDescuento(descuentoPromocional);
		if (precioBase < 0) {
			throw new IllegalArgumentException("El precio base no puede ser negativo");
		}
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
		this.codigoReserva = codigoReserva;
		this.proveedor = proveedor;
		this.categoria = categoria;
		this.precioBase = precioBase;
		this.descuentoPromocional = descuentoPromocional;
		this.stock = stock;
		this.atributosDinamicos = new ArrayList<AtributoDinamico>();
		this.calendario = calendario;
		
	}

	public String getProveedor() {

		return proveedor;

	}

	@Override
	protected float descuento() {

		return descuentoPromocional;

	}

	@Override
	protected float calcularPrecioBase() {

		return precioBase;

	}

	@Override
	protected boolean validarServicio() {

		if (codigoReserva == null) {
			return false;
		}
		for (AtributoDinamico atributo : atributosDinamicos) {
			if (!atributo.esValido()) {
				return false;
			}
		}
		return true;

	}

	@Override
	public boolean esDeCategoria(Categoria categoria) {

		return this.categoria == categoria;

	}

	@Override
	public boolean tieneDisponibilidad() {

		return stock > 0;

	}

	@Override
	public void decrementarCupo() {

		if (!this.tieneDisponibilidad()) {
			throw new SinStockException("No hay cupo disponible de " + this.getNombre());
		}
		stock = stock - 1;

	}

	@Override
	public void incrementarCupo() {

		stock = stock + 1;

	}

	public void definirAtributo(String nombre, boolean obligatorio, TipoAtributo tipo) {

		if (nombre == null) {
			throw new IllegalArgumentException("El atributo debe tener nombre");
		}
		if (this.estaDefinido(nombre)) {
			throw new IllegalArgumentException("El atributo " + nombre + " ya esta definido");
		}
		atributosDinamicos.add(new AtributoDinamico(nombre, obligatorio, tipo));

	}

	public void asignarValor(String nombre, Object valor) {

		this.buscarAtributo(nombre).asignarValor(valor);

	}

	public Object getValor(String nombre) {

		return this.buscarAtributo(nombre).getValor();

	}

	private boolean estaDefinido(String nombre) {

		for (AtributoDinamico atributo : atributosDinamicos) {
			if (atributo.tieneNombre(nombre)) {
				return true;
			}
		}
		return false;

	}

	private AtributoDinamico buscarAtributo(String nombre) {

		for (AtributoDinamico atributo : atributosDinamicos) {
			if (atributo.tieneNombre(nombre)) {
				return atributo;
			}
		}
		throw new AtributoInexistenteException("El atributo " + nombre + " no esta definido");

	}

	@Override
	public void aceptar(Visitor visitor) {

		visitor.visitar(this);

	}

	public Categoria getCategoria() {

		return categoria;

	}

	@Override
	public List<Cupo> cupos() {
		
		return calendario.cupos();
		
	}

	public Map<RangoDeFechas, Cupo> cuposPorPeriodo() {

		return calendario.cuposPorPeriodo();

	}

	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {

		return calendario.cantidadDeVentasEnPeriodo(periodo);
		
	}

}
