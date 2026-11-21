package unq.travel.traslado;

import unq.travel.cupos.RangoDeFechas;

public class TrasladoRecepcion extends EstrategiaTraslado {

	public Sucursal sucursal;
	
	public TrasladoRecepcion(Sucursal sucursal) {

		this.sucursal = sucursal;
		
	}

	@Override
	public float costoTasasTraslado(Reserva reserva) {
		
		return 0;
		
	}
	
	@Override
	public boolean hayDisponibilidad(RangoDeFechas periodo) {
		
		return sucursal.hayDisponibilidad(periodo);
		
	}

	@Override
	public void reservar(RangoDeFechas periodo) {
		
		sucursal.reservar(periodo);
		
	}

	@Override
	public void liberar(RangoDeFechas periodo) {
		
		sucursal.liberar(periodo);
		
	}
	
}
