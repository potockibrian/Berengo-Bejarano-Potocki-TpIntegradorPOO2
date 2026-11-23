package unq.travel.cupos;

import java.time.LocalDate;

import unq.travel.catalogo.SinStockException;

public class Cupo {

	private int topeMaximo;
	private Integer cantidadDeVentas;

	public Cupo(int topeMaximo) {
		
		this.topeMaximo = topeMaximo;
		this.cantidadDeVentas = 0;
		
	}
	
	public Cupo(int topeMaximo, Integer cantidadDeVentas) {
		
		this.topeMaximo = topeMaximo;
		this.cantidadDeVentas = cantidadDeVentas;
		
	}

	public Integer cantidadDeCuposDisponibles() {
		
		return topeMaximo - cantidadDeVentas;
		
	}
	
	public Integer cantidadDeVentas() {

		return cantidadDeVentas ;
		
	}

	public void liberar() {

		cantidadDeVentas--;
		
	}

	public void reservar() {

		if (cantidadDeVentas == topeMaximo) throw new SinStockException();
		
		cantidadDeVentas++;
		
	}

	public boolean tieneDisponibilidad() {

		return cantidadDeVentas < topeMaximo;
		
	}

	public int getTopeMaximo() {

		return topeMaximo;

	}
	
	public void incrementarTope(int incremento) {
		
		topeMaximo += incremento;
		
	}
	
	public Cupo combinarConCupo(Cupo cupo) {
		
		return new Cupo(this.topeMaximo + cupo.topeMaximo, this.cantidadDeVentas + cupo.cantidadDeVentas);
		
	}
		
	/*
	public Cupo combinarConCupoMin(Cupo cupo) {
		
		return new Cupo(Math.min(this.topeMaximo, cupo.topeMaximo), Math.min(this.cantidadDeVentas, cupo.cantidadDeVentas));
		
	}
	*/

}
