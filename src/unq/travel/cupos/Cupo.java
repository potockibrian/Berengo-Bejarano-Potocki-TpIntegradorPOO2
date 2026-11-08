package unq.travel.cupos;

import unq.travel.catalogo.SinStockException;

public class Cupo {

	private int topeMaximo;
	private Integer cantidadDeVentas = 0;

	public Cupo(int topeMaximo) {
		
		this.topeMaximo = topeMaximo;
		
	}

	public Integer cantidadDeVentas() {

		return cantidadDeVentas ;
		
	}

	public void liberar() {

		cantidadDeVentas--;
		
	}

	public void reservar() {

		if (cantidadDeVentas == topeMaximo) throw new SinStockException();
		
	}

	public boolean tieneDisponibilidad() {

		return cantidadDeVentas < topeMaximo;
		
	}

}
