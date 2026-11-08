package unq.travel.cupos;

import java.util.*;

import unq.travel.catalogo.Categoria;

public class CalendarioDeCupos {

	private Map<RangoDeFechas, Cupo> cupos = new TreeMap<RangoDeFechas, Cupo>();
	private int topeMaximo;
	
	public CalendarioDeCupos(int topeMaximo) {
		
		this.topeMaximo = topeMaximo;
		
	}
	
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

	public void reservar(RangoDeFechas rango) {
		
		Cupo cupo = cupos.get(rango);
		
		if (cupo == null) { cupo = new Cupo(topeMaximo); cupo.reservar(); return; }
		
		cupo.reservar();
		
		RangoDeFechas periodo = cupos.keySet().stream()
											  .filter(r -> r.interseccion(rango))
											  .toList()
											  .getFirst();
		
		cupos.remove(periodo);
		
		cupos.put(periodo.union(rango), cupo);
		
	}
	
	public void liberar(RangoDeFechas rango) {
		
		Cupo cupo = cupos.get(rango);
		
		if (cupo != null) cupo.liberar();
		
	}

	public boolean tieneDisponibilidad(RangoDeFechas rango) {

		Cupo cupo = cupos.get(rango);
		
		return (cupo == null) || (cupo.tieneDisponibilidad());

	}
	
	public boolean tieneDisponibilidad() {

		return cupos.values().stream()
							 .anyMatch(c -> c.tieneDisponibilidad());
		
	}
	
}
