package unq.travel.cupos;

import java.time.LocalDate;
import java.util.*;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.SinStockException;

public class CalendarioDeCupos {

	private Map<LocalDate, Cupo> cuposConFecha;
	private int topeMaximo;
	private List<RangoDeFechas> periodos;
	
	public CalendarioDeCupos(int topeMaximo) {
		
		this.topeMaximo = topeMaximo;
		this.cuposConFecha = new HashMap();
		
	}
	
	public List<Cupo> cupos() {

		return new ArrayList<Cupo>(cuposConFecha.values());
		
	}

	public Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo() {

		Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo = new HashMap();
		
		periodos.stream()
				.forEach(p -> cuposPorPeriodo.put(p, this.cuposEnPeriodo(p)));
		
		return cuposPorPeriodo;
		
	}
	
	public List<Cupo> cuposEnPeriodo(RangoDeFechas periodo) {
		
		return periodo.fechas().stream()
			    .filter(cuposConFecha::containsKey)
			    .map(cuposConFecha::get)
			    .toList();
		
	}
	
	public List<Map.Entry<LocalDate, Cupo>> cuposEnPeriodoConFecha(RangoDeFechas periodo) {
		
		return periodo.fechas().stream()
			    .filter(cuposConFecha::containsKey)
			    .map(fecha -> Map.entry(fecha, cuposConFecha.get(fecha)))
			    .toList();
		
	}
	
	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {

		List<Cupo> cupos = this.cuposEnPeriodo(periodo);
		
		return cupos.stream().mapToInt(c -> c.cantidadDeVentas()).sum();
		
	}

	public void reservar(RangoDeFechas rango) {
		
		List<Map.Entry<LocalDate, Cupo>> cupos = this.cuposEnPeriodoConFecha(rango);
		
		if(!cupos.stream().allMatch((kv -> kv.getValue().tieneDisponibilidad()))) throw new SinStockException();
		
		cupos.stream().forEach(kv -> kv.getValue().reservar());
		
		List<LocalDate> fechasNuevas = rango.fechas().stream()
												     .filter(fecha -> cupos.stream().allMatch(kv -> !kv.getKey().equals(fecha)))
												     .toList();
		
		fechasNuevas.stream().forEach(fecha -> { 
			Cupo cupoNuevo = new Cupo(topeMaximo);
			cupoNuevo.reservar();
			cuposConFecha.put(fecha, cupoNuevo);
		});
		
		periodos.add(rango);
		
	}
	
	public void liberar(RangoDeFechas rango) {
		
		List<Cupo> cupos = this.cuposEnPeriodo(rango);
		
		cupos.forEach(c -> c.liberar());
		
	}

	public boolean tieneDisponibilidad(RangoDeFechas rango) {

		List<Cupo> cupos = this.cuposEnPeriodo(rango);
		
		return cupos.stream().allMatch(c -> c.tieneDisponibilidad());

	}
	
	public boolean tieneDisponibilidad() {

		return cuposConFecha.values().stream()
							 .anyMatch(c -> c.tieneDisponibilidad());
		
	}
	
}
