package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.List;

public class PaqueteTuristico extends ItemCatalogo {

	private List<ItemCatalogo> items;
	private float descuento;

	public PaqueteTuristico(String nombre, String descripcion, float descuento) {

		super(nombre, descripcion);
		this.validarDescuento(descuento);
		this.descuento = descuento;
		this.items = new ArrayList<ItemCatalogo>();

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

}
