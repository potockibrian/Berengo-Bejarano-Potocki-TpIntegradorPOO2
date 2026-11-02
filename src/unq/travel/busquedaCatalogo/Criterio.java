package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public interface Criterio {

	boolean cumpleConCriterio(ItemCatalogo item);

}
