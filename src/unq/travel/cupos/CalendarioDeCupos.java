package unq.travel.cupos;

import java.util.*;

import unq.travel.catalogo.Categoria;

public class CalendarioDeCupos {

	private Map<RangoDeFechas, Cupo> cupos = new TreeMap<RangoDeFechas, Cupo>();
	
	public List<Cupo> cupos() {

		return new ArrayList<Cupo>(cupos.values());
		
	}

	public Map<RangoDeFechas, Cupo> cuposPorPeriodo() {

		return cupos;
		
	}

	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {

		Cupo cupo = cupos.get(periodo);
		
		return (cupo != null) ? cupo.cantidadDeVentas() : 0;
		
	}

}
