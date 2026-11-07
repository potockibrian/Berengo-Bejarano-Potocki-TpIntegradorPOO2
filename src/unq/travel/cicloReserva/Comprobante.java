package unq.travel.cicloReserva;

public class Comprobante {

	private float costoTotalReserva;
	private float costoServicios;
	private float costoTasasTraslado;

	public Comprobante(float costoTotalReserva, float costoServicios, float costoTasasTraslado) {

		this.costoTotalReserva = costoTotalReserva;
		this.costoServicios = costoServicios;
		this.costoTasasTraslado = costoTasasTraslado;
		
	}

	public float getCostoTotalReserva() {
		
		return costoTotalReserva;
		
	}

	public float getCostoServicios() {
		
		return costoServicios;
		
	}
	
	public float getCostoTasasTraslado() {
		
		return costoTasasTraslado;
		
	}

}
