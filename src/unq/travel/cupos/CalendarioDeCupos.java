package unq.travel.cupos;

import java.time.LocalDate;
import java.util.*;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.SinStockException;

public class CalendarioDeCupos {

	private CuposDeFechas cuposDeFechas = new CuposDeFechas();
	
	/*
	public RegistroVentaPeriodo cantidadYTotalCobradoDeVentasEnPeriodo(RangoDeFechas periodo) {
		
		return registrosDeVenta.stream()
				.filter(r -> r.periodoContiene(periodo))
				.reduce(new RegistroVentaPeriodo(periodo), (nuevo, acumulador) -> nuevo.combinarConRegistro(acumulador));
		
	}
	*/
	
	/*
	public Integer cantidadDeVentasEnPeriodo(RangoDeFechas periodo) {
		
		return periodo.fechas().stream()
					  .mapToInt(f -> cuposDeFechas.cantidadDeVentasEnFecha(f))
					  .max()
					  .orElse(0);
		
	}
	*/
	
	public Cupo cuposEnPeriodo(RangoDeFechas periodo) {
		
		return cuposDeFechas.cuposEnFechas(periodo.fechas());
		
	}
	
	public void reservar(RangoDeFechas rango) {
		
		if(!rango.fechas().stream().allMatch(f -> cuposDeFechas.tieneDisponibilidad(f))) throw new SinStockException();
		
		rango.fechas().stream().forEach(f -> cuposDeFechas.reservar(f));
		
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
			   .forEach(f -> cuposDeFechas.agregarCantidadDeCuposAFecha(f, cantidad));
		
	}
	
}
