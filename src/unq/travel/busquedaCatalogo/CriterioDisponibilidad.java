package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

public class CriterioDisponibilidad implements Criterio {

	private RangoDeFechas periodo;

	public CriterioDisponibilidad(RangoDeFechas periodo) {
		
		this.periodo = periodo;
		
	}
	
	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return item.tieneDisponibilidad(periodo);

	}

}
