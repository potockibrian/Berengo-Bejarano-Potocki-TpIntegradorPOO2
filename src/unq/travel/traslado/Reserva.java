package unq.travel.traslado;

import java.util.List;
import java.util.ArrayList;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.EstadoPendiente;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.notificaciones.Suscriptor;

public class Reserva {

	private EstadoDeReserva estado;
	private EstrategiaTraslado estrategia;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private List<Suscriptor> suscriptores;

	public Reserva(Cliente cliente, Direccion direccion, EstrategiaTraslado estrategia, List<Suscriptor> suscriptores) {

		this.estrategia = estrategia;
		this.estado = new EstadoPendiente(this);
		this.cliente = cliente;
		this.items = new ArrayList<ItemCatalogo>();
		this.suscriptores = suscriptores;
		
	}

	public void cambiarEstado(EstadoDeReserva estado) {

		this.estado = estado;
		
		suscriptores.stream()
			.forEach(s -> s.notify(this, estado));
		
	}

	public void confirmarReserva() {

		estado.confirmar(items);
		
	}

	public void checkinReserva() {

		estado.checkin();
		
	}

	public void ejecutarReserva() {

		estado.ejecutar();
		
	}

	public void finalizarReserva() {

		estado.completar();
		
	}

	public void cancelarReserva() {

		estado.cancelar(items);
		
	}

	public void costoTraslado() {

		estrategia.costoTraslado();
		
	}

	public void agregarServicio(ItemCatalogo dummy) {

		estado.agregarItem(dummy, items);
		
	}

	public void quitarServicio(ItemCatalogo dummy) {

		estado.quitarItem(dummy, items);
		
	}

	public Cliente getCliente() {

		return cliente;
		
	}

	public float getPrecioTotalServicios() {
		
		return (float) items.stream()
				.mapToDouble(i -> i.getPrecioFinal())
				.sum();
		
	}

}
