package unq.travel.traslado;

import unq.travel.cupos.RangoDeFechas;

public abstract class EstrategiaTraslado {
	
	public abstract float costoTasasTraslado(Reserva reserva);

	public boolean hayDisponibilidad(RangoDeFechas perioso) { return true; }
	
	public void reservar(RangoDeFechas periodo) {}
	
	public void liberar(RangoDeFechas periodo) {}
	
}
