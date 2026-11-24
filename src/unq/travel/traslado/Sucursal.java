package unq.travel.traslado;

import unq.travel.catalogo.SinStockException;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class Sucursal {

	private CalendarioDeCupos calendario;

	public Sucursal(CalendarioDeCupos calendario) {

		this.calendario = calendario;
		
	}

	public boolean hayDisponibilidad(RangoDeFechas periodo) {

		return calendario.tieneDisponibilidad(periodo);

	}
	
	public void agregarCupoParaPeriodo(RangoDeFechas periodo, int cantidad) {
		
		calendario.agregarCupoParaPeriodo(periodo, cantidad);
		
	}

	public void reservar(RangoDeFechas periodo) {

		calendario.reservar(periodo);
		
	}
	
	public void liberar(RangoDeFechas periodo) {
		
		calendario.liberar(periodo);
		
	}

}
