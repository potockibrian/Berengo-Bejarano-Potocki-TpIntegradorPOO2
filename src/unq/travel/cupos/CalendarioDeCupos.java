package unq.travel.cupos;

import java.time.LocalDate;
import java.util.*;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.SinStockException;

public class CalendarioDeCupos {

	private CuposDeFechas cuposDeFechas = new CuposDeFechas();
	private List<RangoDeFechas> periodos;
	
	public Cupo cupos() {

		return cuposDeFechas.cupos();
		
	}

	public Map<RangoDeFechas, Cupo> cuposPorPeriodo() {

		Map<RangoDeFechas, Cupo> cuposPorPeriodo = new HashMap();
		
		periodos.stream()
				.forEach(p -> cuposPorPeriodo.put(p, cuposDeFechas.cuposEnFechas(p.fechas())));
		
		return cuposPorPeriodo;
		
	}
	
	/*
	public List<Cupo> cuposEnPeriodo(RangoDeFechas periodo) {
		
		return periodo.fechas().stream()
			    .map(f -> cuposDeFechas.cupoDeFecha);
		
	}
	*/
	
	/*
	public List<Map.Entry<LocalDate, Cupo>> cuposEnPeriodoConFecha(RangoDeFechas periodo) {
		
		return cuposDeFechas.cuposEnFechasConFecha(periodo.fechas());
		
	}
	*/
	
	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {
		
		return periodo.fechas().stream()
					  .mapToInt(f -> cuposDeFechas.cantidadDeVentasEnFecha(f))
					  .max()
					  .orElse(0);
		
	}

	public void reservar(RangoDeFechas rango) {
		
		if(!rango.fechas().stream().allMatch(f -> cuposDeFechas.tieneDisponibilidad(f))) throw new SinStockException();
		
		rango.fechas().stream().forEach(f -> cuposDeFechas.reservar(f));
		
		periodos.add(rango);
		
	}
	
	public void liberar(RangoDeFechas rango) {
		
		rango.fechas().stream()
			 .forEach(f -> cuposDeFechas.liberar(f));
		
	}

	public boolean tieneDisponibilidad(RangoDeFechas rango) {
		
		return rango.fechas().stream()
					.allMatch(f -> cuposDeFechas.tieneDisponibilidad(f));

	}
	
	public boolean tieneDisponibilidad() {
		
		return cuposDeFechas.tieneDisponibilidad();
		
	}
	
	public void agregarCupoParaPeriodo(RangoDeFechas periodo, int cantidad) {
		
		periodo.fechas().stream()
			   .forEach(f -> cuposDeFechas.agregarCupoAFecha(f, new Cupo(cantidad)));
		
	}
	
}
