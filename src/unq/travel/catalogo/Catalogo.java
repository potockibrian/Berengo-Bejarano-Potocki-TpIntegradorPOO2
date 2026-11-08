package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {

	private List<ItemCatalogo> items;

	public Catalogo() {

		this.items = new ArrayList<ItemCatalogo>();

	}

	public void agregarItem(ItemCatalogo item) {

		items.add(item);

	}

	public void eliminarItem(ItemCatalogo item) {

		if (!items.contains(item)) {
			throw new ItemInexistenteException("El item " + item.getNombre() + " no esta en el catalogo");
		}
		items.remove(item);

	}

	public List<ItemCatalogo> getItems() {

		return items;

	}

}
