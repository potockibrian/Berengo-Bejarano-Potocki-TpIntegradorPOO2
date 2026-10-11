package unq.travel.traslado;

import java.awt.List;
import java.util.ArrayList;

public class Reserva {

	private EstadoReserva estado;
	private EstrategiaTraslado estrategia;
	private ArrayList<ItemCatalogo> items;

	public Reserva(Direccion direccion, EstrategiaTraslado estrategia) {

		this.estrategia = estrategia;
		this.items = new ArrayList<ItemCatalogo>();
		this.estado = new EstadoPendiente(this);
		
	}

	public void cambiarEstado(EstadoReserva estado) {

		this.estado = estado;
		
	}

	public void confirmarReserva() {

		estado.confirmar();
		
	}

	public void checkinReserva() {

		estado.checkin();
		
	}

	public void ejecutarReserva() {

		estado.ejecutar();
		
	}

	public void finalizarReserva() {

		estado.finalizar();
		
	}

	public void cancelarReserva() {

		estado.cancelar();
		
	}

	public void costoTraslado() {

		estrategia.costoTraslado();
		
	}

	public void agregarServicio(ItemCatalogo dummy) {

		estado.agregarServicio(dummy, items);
		
	}

	public void quitarServicio(ItemCatalogo dummy) {

		estado.quitarServicio(dummy, items);
		
	}

}
