package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.reportes.Visitor;

public class PaqueteTuristico extends ItemCatalogo {

	private List<ItemCatalogo> items;
	private float descuento;
	private CalendarioDeCupos calendario;

	public PaqueteTuristico(String nombre, String descripcion, float descuento, CalendarioDeCupos calendario) {

		super(nombre, descripcion, calendario);
		validarDescuento(descuento);
		this.descuento = descuento;
		this.items = new ArrayList<ItemCatalogo>();
		this.calendario = calendario;

	}

	public void agregarItem(ItemCatalogo item) {

		items.add(item);

	}

	@Override
	protected float descuento() {

		return descuento;

	}

	@Override
	protected float calcularPrecioBase() {

		float suma = 0;
		for (ItemCatalogo item : items) {
			suma = suma + item.getPrecioFinal();
		}
		return suma;

	}

	@Override
	protected boolean validarServicio() {

		if (items.isEmpty()) {
			return false;
		}
		for (ItemCatalogo item : items) {
			if (!item.esValido()) {
				return false;
			}
		}
		return true;

	}

	@Override
	public boolean esDeCategoria(Categoria categoria) {

		for (ItemCatalogo item : items) {
			if (item.esDeCategoria(categoria)) {
				return true;
			}
		}
		return false;

	}

	/*
	@Override
	public boolean tieneDisponibilidad() {

		for (ItemCatalogo item : items) {
			if (!item.tieneDisponibilidad()) {
				return false;
			}
		}
		return true;

	}

	@Override
	public void decrementarCupo() {

		List<ItemCatalogo> decrementados = new ArrayList<ItemCatalogo>();
		try {
			for (ItemCatalogo item : items) {
				item.decrementarCupo();
				decrementados.add(item);
			}
		} catch (SinStockException e) {
			for (ItemCatalogo item : decrementados) {
				item.incrementarCupo();
			}
			throw e;
		}

	}

	@Override
	public void incrementarCupo() {

		for (ItemCatalogo item : items) {
			item.incrementarCupo();
		}

	}
	
	*/

	@Override
	public void aceptar(Visitor visitor) {

		visitor.visitar(this);
		
		items.stream()
		     .forEach(i -> i.aceptar(visitor));

	}

	public List<ItemCatalogo> getItems() {

		return new ArrayList<ItemCatalogo>(items);

	}

	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {

		return items.stream()
					.mapToInt(i -> i.cantidadDeVentasEnPeriodo(periodo))
					.sum();
		
	}

	@Override
	public List<Cupo> cupos() {

		List<Cupo> cupos = new ArrayList<Cupo>();
		
		items.stream()
				.map(i -> i.cupos())
				.forEach(cs -> cupos.addAll(cs));
		
		return cupos;
		
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

		if (!this.tieneDisponibilidad(rango)) {
			throw new SinStockException();
		}
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
}
