package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import unq.travel.cupos.*;
import unq.travel.reportes.*;

public class ServicioIndividual extends ItemCatalogo {

	private CalendarioDeCupos calendario;
	private String codigoReserva;
	private String proveedor;
	private Categoria categoria;
	private float precioBase;
	private float descuentoPromocional;
	private List<AtributoDinamico> atributosDinamicos;
	private List<RegistroVentaPeriodo> registrosDeVenta = new ArrayList<>();

	public ServicioIndividual(String nombre, String descripcion, String codigoReserva, String proveedor,
			Categoria categoria, float precioBase, float descuentoPromocional, CalendarioDeCupos calendario) {

		super(nombre, descripcion);
		
		validarAtributos(nombre, descripcion, codigoReserva, proveedor, categoria, precioBase, descuentoPromocional, calendario);
		validarDescuento(descuentoPromocional);
		
		if (precioBase < 0) {
			
			throw new IllegalArgumentException("El precio base no puede ser negativo");
		
		}
		
		this.codigoReserva = codigoReserva;
		this.proveedor = proveedor;
		this.categoria = categoria;
		this.precioBase = precioBase;
		this.descuentoPromocional = descuentoPromocional;
		this.atributosDinamicos = new ArrayList<AtributoDinamico>();
		this.calendario = calendario;

	}

	private void validarAtributos(String nombre, String descripcion, String codigoReserva, String proveedor,
			Categoria categoria, float precioBase, float descuentoPromocional, CalendarioDeCupos calendario) {
		
		try {
			
			List.of(nombre, descripcion, codigoReserva, proveedor,
					categoria, precioBase, descuentoPromocional, calendario);
			
		} catch (Exception e) {
			
			throw new AtributoSinValorException("Atributo sin valor");
			
		}
		
	}

	public String getProveedor() {

		return proveedor;

	}

	@Override
	protected float descuento() {

		return descuentoPromocional;

	}

	@Override
	public float getPrecioBase() {

		return precioBase;

	}

	/*
	@Override
	protected boolean validarServicio() {

		return codigoReserva != null && atributosDinamicos.stream().allMatch(a -> a.esValido());
		
	}
	*/

	@Override
	public boolean esDeCategoria(Categoria categoria) {

		return this.categoria == categoria;

	}

	public void definirAtributo(String nombre, boolean obligatorio, TipoAtributo tipo, Object valor) {

		if (nombre == null) throw new IllegalArgumentException("El atributo debe tener nombre");
		if (this.estaDefinido(nombre)) throw new IllegalArgumentException("El atributo " + nombre + " ya esta definido");
		
		atributosDinamicos.add(new AtributoDinamico(nombre, obligatorio, tipo, valor));

	}

	public void asignarValor(String nombre, Object valor) {

		this.buscarAtributo(nombre).asignarValor(valor);

	}

	public Object getValor(String nombre) {

		return this.buscarAtributo(nombre).getValor();

	}

	private boolean estaDefinido(String nombre) {
		
		return atributosDinamicos.stream().anyMatch(a -> a.tieneNombre(nombre));

	}

	private AtributoDinamico buscarAtributo(String nombre) {
		
		return atributosDinamicos.stream()
				.filter(a -> a.tieneNombre(nombre))
				.findFirst()
				.orElseThrow(() -> new AtributoInexistenteException("El atributo " + nombre + " no esta definido"));

	}

	public Categoria getCategoria() {

		return categoria;

	}

	@Override
	public Set<Categoria> categorias() {
		
		return new HashSet<>(Set.of(this.getCategoria()));
		
	}
	
	@Override
	public Cupo cuposEnPeriodoDeCategoria(RangoDeFechas periodo, Categoria categoria) {
		
		return (this.categoria == categoria) ? calendario.cuposEnPeriodo(periodo) : new Cupo(0);
		
	}

	@Override
	public RegistroVentaPeriodo cantidadYTotalCobradoDeVentasEnPeriodo(RangoDeFechas periodo) {
	
		return registrosDeVenta.stream()
				.filter(r -> r.periodoContiene(periodo))
				.reduce(new RegistroVentaPeriodo(periodo), (nuevo, acumulador) -> nuevo.combinarConRegistro(acumulador));
		
	}

	@Override
	public boolean tieneDisponibilidad(RangoDeFechas rango) {
		return calendario.tieneDisponibilidad(rango);
		
	}
	
	@Override
	public BolsaDeProveedores proveedores() {
		
		BolsaDeProveedores bolsaConProveedorActual = new BolsaDeProveedores();

		bolsaConProveedorActual.agregarProveedor(this.proveedor);
		
		return bolsaConProveedorActual;
		
	}

	@Override
	public void reservar(RangoDeFechas rango) {

		calendario.reservar(rango);
		
		registrosDeVenta.add(new RegistroVentaPeriodo(rango, this.getPrecioFinal(), 1));
		
	}

	@Override
	public void liberar(RangoDeFechas rango) {

		calendario.liberar(rango);
		
	}

	@Override
	public boolean tieneDisponibilidad() {

		return calendario.tieneDisponibilidad();
		
	}

}
