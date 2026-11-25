package unq.travel.cupos;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RangoDeFechas {

	private LocalDate fechaInicio;
	private LocalDate fechaFinal;
	
	public RangoDeFechas(LocalDate fechaInicio, LocalDate fechaFinal) {
		
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		
	}
	
	public LocalDate getFechaInicio() {
		
		return fechaInicio;
		
	}
	
	public LocalDate getFechaFinal() {
		
		return fechaFinal;
	
	}
	
	public List<LocalDate> fechas() {
		
		return fechaInicio.datesUntil(fechaFinal.plusDays(1)).toList();
				
	}
	
	public boolean equals(RangoDeFechas rango) {
		
		return (fechaInicio.equals(rango.getFechaInicio()))
		&& 	   (fechaFinal.equals(rango.getFechaFinal()));
		
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(fechaInicio, fechaFinal);
		
	}
	
	public boolean contieneA(RangoDeFechas rango) {

		return this.fechas().containsAll(rango.fechas());
		
	}
	
	public RangoDeFechas union(RangoDeFechas rango) {

		return new RangoDeFechas(
				Collections.min(List.of(this.fechaInicio, rango.getFechaInicio())),
				Collections.max(List.of(fechaFinal, rango.getFechaFinal())));
		
	}
	
	@Override
	public String toString() {
		
		return fechaInicio.toString() + "/" + fechaFinal.toString();
		
	}
	
	/*
	public boolean interseccion(RangoDeFechas rango) {
		
		return (fechaInicio.compareTo(rango.getFechaFinal()) <= 0)
			&& (rango.getFechaInicio().compareTo(fechaFinal) <= 0);
				
	}
	*/
	
}
