package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.cupos.RegistroVentaPeriodo;
import unq.travel.reportes.BolsaDeProveedores;
import unq.travel.reportes.Visitor;

public class PaqueteTuristico extends ItemCatalogo {

	private List<ItemCatalogo> items;
	private float descuento;

	public PaqueteTuristico(String nombre, String descripcion, float descuento) {
		
		super(nombre, descripcion);
		
		validarAtributos(nombre, descripcion, descuento);
		validarDescuento(descuento);
		
		this.descuento = descuento;
		this.items = new ArrayList<ItemCatalogo>();

	}

	private void validarAtributos(String nombre, String descripcion, float descuento) {
		
		try {
			
			List.of(nombre, descripcion, descuento);
			
		} catch (Exception e) {
			
			throw new AtributoSinValorException("Atributo sin valor");
			
		}
		
	}
	
	public void agregarItem(ItemCatalogo item) {

		items.add(item);

	}

	@Override
	protected float descuento() {

		return descuento;

	}

	@Override
	public float getPrecioBase() {

		return (float)items.stream().mapToDouble(i -> i.getPrecioFinal()).sum();

	}

	/*
	@Override
	protected boolean validarServicio() {

		return !items.isEmpty() && items.stream().allMatch(i -> i.esValido());

	}
	*/

	@Override
	public boolean esDeCategoria(Categoria categoria) {
		
		return items.stream().anyMatch(i -> i.esDeCategoria(categoria));

	}

	@Override
	public RegistroVentaPeriodo cantidadYTotalCobradoDeVentasEnPeriodo(RangoDeFechas periodo) {

		RegistroVentaPeriodo ultimoRegistro = items.stream()
												.map(i -> i.cantidadYTotalCobradoDeVentasEnPeriodo(periodo))
												.reduce(new RegistroVentaPeriodo(periodo), (nuevo, acumulador)
														-> nuevo.combinarConRegistroRecursivo(acumulador));
		
		ultimoRegistro.aplicarDescuento(this.descuento());
		
		return ultimoRegistro;
		
	}

	@Override
	public Cupo cuposEnPeriodoDeCategoria(RangoDeFechas periodo, Categoria categoria) {
		
		return items.stream()
				.map(i -> i.cuposEnPeriodoDeCategoria(periodo, categoria))
				.reduce(new Cupo(0), (nuevo, acumulador) -> nuevo.combinarConCupo(acumulador));
		
	}

	@Override
	public BolsaDeProveedores proveedores() {
		
		return items.stream()
					.map(i -> i.proveedores())
					.reduce(new BolsaDeProveedores(), (nueva, acumulador) -> acumulador.combinarConBolsaDeProveedores(nueva));
		
	}
	
	@Override
	public boolean tieneDisponibilidad() {

		return items.stream().allMatch(item -> item.tieneDisponibilidad());

	}

	@Override
	public boolean tieneDisponibilidad(RangoDeFechas rango) {

		return items.stream().allMatch(i -> i.tieneDisponibilidad(rango));
		
	}

	@Override
	public void reservar(RangoDeFechas rango) {

		if (!this.tieneDisponibilidad(rango)) throw new SinStockException();
		
		List<ItemCatalogo> reservados = new ArrayList<ItemCatalogo>();
		
		try {
			
			for (ItemCatalogo item : items) {
				item.reservar(rango);
				reservados.add(item);
			}
			
		} catch (SinStockException e) {
			
			for (ItemCatalogo item : reservados) {
				item.liberar(rango);
			}
			
			throw e;
		}
		
	}

	@Override
	public void liberar(RangoDeFechas rango) {

		items.stream().forEach(i -> i.liberar(rango));
		
	}

	@Override
	public Set<Categoria> categorias() {

		return items.stream()
				.map(i -> i.categorias())
				.reduce(new HashSet<>(), (nuevo, acumulador) -> {acumulador.addAll(nuevo); return acumulador;});
		
	}
	
}
