package unq.travel.traslado;

import java.util.ArrayList;
import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

public class LineaDeReserva {

	private ItemCatalogo item;
	private RangoDeFechas rango;
	private boolean reservada;

	public LineaDeReserva(ItemCatalogo item, RangoDeFechas rango) {

		this.item = item;
		this.rango = rango;

	}

	public float precioFinal() {

		return item.getPrecioFinal();

	}

	public boolean tieneDisponibilidad() {

		return item.tieneDisponibilidad(rango);

	}
	
	public void reservarCupo() {

		item.reservar(rango);

	}

	public void liberarCupo() {
		
		item.liberar(rango);

	}

}
