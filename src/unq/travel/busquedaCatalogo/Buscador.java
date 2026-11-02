package unq.travel.busquedaCatalogo;

import java.util.ArrayList;
import java.util.List;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;

public class Buscador {

	private Catalogo catalogo;

	public Buscador(Catalogo catalogo) {

		this.catalogo = catalogo;

	}

	public List<ItemCatalogo> buscar(Criterio criterio) {

		List<ItemCatalogo> resultado = new ArrayList<ItemCatalogo>();
		for (ItemCatalogo item : catalogo.getItems()) {
			if (criterio.cumpleConCriterio(item)) {
				resultado.add(item);
			}
		}
		return resultado;

	}

}
