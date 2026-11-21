package unq.travel.cupos;

import java.time.LocalDate;
import java.util.*;

import unq.travel.catalogo.SinStockException;

public class CuposDeFechas {

	private Map<LocalDate, Cupo> cuposConFecha = new HashMap<LocalDate, Cupo>();
	
	public void agregarCupoAFecha(LocalDate fecha, Cupo cupo) {
		
		cupo = cupo.combinarConCupo(cuposConFecha.getOrDefault(fecha, new Cupo(0)));
		
		cuposConFecha.put(fecha, cupo);
		
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
		catch (NullPointerException e) {
		
			throw new SinStockException();
		
		}
		
	}

	public int cantidadDeVentasEnFecha(LocalDate fecha) {

		Cupo cupoDeFecha = cuposConFecha.get(fecha);
		
		return (cupoDeFecha == null) ? 0 : cupoDeFecha.cantidadDeVentas();
		
	}

	public Cupo cuposEnFechas(List<LocalDate> fechas) {
		
		List<Cupo> cupos = fechas.stream()
					 			 .map(f -> cuposConFecha.get(f))
					 			 .filter(c -> c != null)
					 			 .toList();
		
		return (cupos.isEmpty())
				? new Cupo(0)
						: cupos.stream().reduce(cupos.getFirst(), (acumulador, nuevo) -> acumulador.combinarConCupoMin(nuevo));
		
	}

	public Cupo cupos() {

		List<Cupo> cupos = (List<Cupo>) cuposConFecha.values();
		
		return (cuposConFecha.isEmpty())
				? new Cupo(0)
						: cuposConFecha.values().stream()
												.reduce(cupos.getFirst(), (nuevo, acumulador) -> nuevo.combinarConCupo(acumulador));
		
	}
	
	public List<Map.Entry<LocalDate, Cupo>> cuposEnFechasConFecha(List<LocalDate> fechas) {
		
		return fechas.stream()
			    .filter(cuposConFecha::containsKey)
			    .map(fecha -> Map.entry(fecha, cuposConFecha.get(fecha)))
			    .toList();
		
	}
	
}
