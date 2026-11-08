package unq.travel.cupos;

import java.time.LocalDate;
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
		
		return Stream.iterate(fechaInicio, fecha -> !fecha.isAfter(fechaFinal), fecha -> fecha.plusDays(1))
		        .toList();
				
	}
	
	public boolean equals(RangoDeFechas rango) {
		
		return (fechaInicio.equals(rango.getFechaInicio()))
		&& 	   (fechaFinal.equals(rango.getFechaFinal()));
		
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(fechaInicio, fechaFinal);
		
	}
	
	/*
	public boolean interseccion(RangoDeFechas rango) {
		
		return (fechaInicio.compareTo(rango.getFechaFinal()) <= 0)
			&& (rango.getFechaInicio().compareTo(fechaFinal) <= 0);
				
	}
	
	public RangoDeFechas union(RangoDeFechas rango) {

		return new RangoDeFechas(min(this.fechaInicio, rango.getFechaInicio()), max(this.fechaFinal, rango.getFechaFinal()));
		
	}
	
	private LocalDate min(LocalDate fecha1, LocalDate fecha2) {
		
		return (fecha1.compareTo(fecha2) <= 0) ? fecha1 : fecha2;
		
	}
	
	private LocalDate max(LocalDate fecha1, LocalDate fecha2) {
		
		return (fecha1.compareTo(fecha2) >= 0)? fecha1 : fecha2;
		
	}
	*/
	
}
