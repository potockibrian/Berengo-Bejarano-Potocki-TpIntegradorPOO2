package unq.travel.traslado;

public abstract class EstrategiaTraslado {
	
	public abstract float costoTasasTraslado(Reserva reserva);

	public boolean hayDisponibilidad() {
		
		return true;
		
	}
	
}
