package unq.travel.traslado;

import java.util.List;
import java.util.ArrayList;

import unq.travel.cicloReserva.*;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.notificaciones.Suscriptor;

public class Reserva {

	private EstadoDeReserva estado;
	private EstrategiaTraslado traslado;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private List<Suscriptor> suscriptores;
	public int cantidadPasajeros;
	public Direccion direccion;

	public Reserva(Cliente cliente, Direccion direccion, EstrategiaTraslado estrategia, List<Suscriptor> suscriptores, int cantidadPasajeros) {

		this.traslado = estrategia;
		this.estado = new EstadoPendiente(this);
		this.cliente = cliente;
		this.items = new ArrayList<ItemCatalogo>();
		this.suscriptores = suscriptores;
		this.direccion = direccion;
		this.cantidadPasajeros = cantidadPasajeros;
		
	}

	public Cliente getCliente() {

		return cliente;
		
	}
	
	public void elegirTraslado(EstrategiaTraslado traslado) {
		
		this.traslado = traslado;
		
	}
	
	public void cambiarEstado(EstadoDeReserva estado) {
		
		suscriptores.stream()
			.forEach(s -> s.notify(this, this.estado, estado));
		
		this.estado = estado;
		
	}

	public void confirmarReserva() {

		if(!traslado.hayDisponibilidad()) throw new NoHayDisponibilidadException();
		
		estado.confirmar();
		
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

		estado.cancelar();
		
	}

	public float costoTotalReserva() {
		
		return costoServicios() + costoTasasTraslado();
		
	}
	
	public float costoServicios() {
		
		return (float) items.stream()
				.mapToDouble(i -> i.getPrecioFinal())
				.sum();
		
	}
	
	public float costoTasasTraslado() {

		return traslado.costoTasasTraslado(this);
		
	}

	public void agregarServicio(ItemCatalogo item) {

		estado.agregarItem(item);
		
	}

	public void quitarServicio(ItemCatalogo item) {

		estado.quitarItem(item);
		
	}

	public Direccion direccion() {

		return direccion;
		
	}

	public int cantidadPasajeros() {

		return cantidadPasajeros;
		
	}

	public void suscribir(Suscriptor suscriptor) {

		suscriptores.add(suscriptor);
		
	}

	public void desuscribir(Suscriptor suscriptor) {

		suscriptores.remove(suscriptor);
		
	}

	public void doConfirmar() {

		if (!items.stream().allMatch(i -> i.tieneDisponibilidad())) throw new NoHayDisponibilidadException();
		
		items.stream()
			 .forEach(i -> i.decrementarCupo());
		
		this.cambiarEstado(new EstadoConfirmado(this));
		
	}

	public void doCheckin() {

		this.cambiarEstado(new EstadoEnCheckin(this));
		
	}

	public void doEjecutar() {

		this.cambiarEstado(new EstadoEnCurso(this));
		
	}

	public void doFinalizar() {

		this.cambiarEstado(new EstadoFinalizado(this));
		
	}
	
	public void doCancelar() {
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}

	public void doCancelarFromConfirmada() {

		items.stream()
			 .forEach(i -> i.incrementarCupo());
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}

	public void doCancelarFromCheckin() {
		
		items.stream()
			 .forEach(i -> i.incrementarCupo());
		
		cliente.añadirVoucher(new Voucher(this.costoServicios() + this.costoTasasTraslado()));
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}

	public void doCancelarFromEnCurso() {

		cliente.añadirVoucher(new Voucher(this.costoServicios()));
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}
	
	public void doAgregarItem(ItemCatalogo item) {

		if(!item.tieneDisponibilidad()) throw new NoHayDisponibilidadException();
		
		items.add(item);
		
	}

	public void doQuitarItem(ItemCatalogo item) {

		items.remove(item);
		
	}

}
