package unq.travel.cupos;

import java.time.LocalDate;
import java.util.*;

import unq.travel.catalogo.SinStockException;

public class CuposDeFechas {
	
	private Map<LocalDate, Cupo> cuposConFecha = new HashMap<LocalDate, Cupo>();

	public Object agregarCantidadDeCuposAFecha(LocalDate fecha, int cantidad) {
		
		Cupo cupoAIncrementar = cuposConFecha.getOrDefault(fecha, new Cupo(0));
		
		cupoAIncrementar.incrementarTope(cantidad);
		
		return cuposConFecha.put(fecha, cupoAIncrementar);
		
	}
	
	public boolean tieneDisponibilidad() {

		return cuposConFecha.values().stream()
							 .anyMatch(c -> c.tieneDisponibilidad());
	
	}
	
	public boolean tieneDisponibilidad(LocalDate fecha) {

		Cupo cupoDeFecha = cuposConFecha.get(fecha);
	
		return (cupoDeFecha == null) ? false : cupoDeFecha.tieneDisponibilidad();
		
	}
	
	public void liberar(LocalDate fecha) {
		
		Cupo cupoDeFecha = cuposConFecha.get(fecha);
		
		if (cupoDeFecha != null) cupoDeFecha.liberar();
		
	}

	public void reservar(LocalDate fecha) {

		try {
			
			cuposConFecha.get(fecha).reservar();
			
		}
		catch (Exception e) {
		
			throw new SinStockException();
		
		}
		
	}

	/*
	public int cantidadDeVentasEnFecha(LocalDate fecha) {

		Cupo cupoDeFecha = cuposConFecha.get(fecha);
		
		return (cupoDeFecha == null) ? 0 : cupoDeFecha.cantidadDeVentas();
		
	}
	*/

	public Cupo cuposEnFechas(List<LocalDate> fechas) {
		
		// Obtiene los cupos de las fechas dadas, filtrando los cupos de las fechas sin cupo
		List<Cupo> cupos = fechas.stream()
					 			 .map(f -> cuposConFecha.getOrDefault(f, new Cupo(0)))
					 			 .toList();
		
		// Combina todos los cupos, y si no hay ningún cupo devuelve un cupo vacío
		return cupos.stream().reduce(new Cupo(0), (acumulador, nuevo) -> acumulador.combinarConCupo(nuevo));
		
	}

	/*
	public Cupo cupos() {

		List<Cupo> cupos = (List<Cupo>) cuposConFecha.values();
		
		return (cuposConFecha.isEmpty())
				? new Cupo(0)
						: cuposConFecha.values().stream()
												.reduce(cupos.getFirst(), (nuevo, acumulador) -> nuevo.combinarConCupo(acumulador));
		
	}
	*/
	
}
