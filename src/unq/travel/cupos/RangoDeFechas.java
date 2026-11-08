package unq.travel.cupos;

import java.time.LocalDate;

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
	
	public boolean equals(RangoDeFechas rango) {
		
		return this.interseccion(rango);
		//(fechaInicio.equals(rango.getFechaInicio()))
		//&& (fechaFinal.equals(rango.getFechaFinal()));
		
	}
	
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
	
}
