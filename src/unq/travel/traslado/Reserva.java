package unq.travel.traslado;

import java.util.List;
import java.util.ArrayList;

import unq.travel.cicloReserva.*;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.metodosDePago.MetodoDePago;
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
	private ArrayList<LineaDeReserva> lineas;
	private MetodoDePago metodoDePago;

	public Reserva(Cliente cliente, Direccion direccion, EstrategiaTraslado estrategia, List<Suscriptor> suscriptores, int cantidadPasajeros, MetodoDePago metodoDePago) {

		this.traslado = estrategia;
		this.estado = new EstadoPendiente(this);
		this.cliente = cliente;
		this.items = new ArrayList<ItemCatalogo>();
		this.suscriptores = suscriptores;
		this.direccion = direccion;
		this.cantidadPasajeros = cantidadPasajeros;
		this.lineas = new ArrayList<LineaDeReserva>();
		this.metodoDePago = metodoDePago;
		
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
		
		return (float) lineas.stream()
				.mapToDouble(i -> i.precioFinal())
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

		if (!lineas.stream().allMatch(i -> i.tieneDisponibilidad())) throw new NoHayDisponibilidadException();
		if (!lineas.stream().allMatch(i -> i.tieneDisponibilidad(traslado))) throw new NoHayDisponibilidadException();
		
		metodoDePago.pagar(this.costoTotalReserva());	// metodoDePago se encarga de levantar la excepción
		
		lineas.stream()
			 .forEach(i -> i.reservarCupo(traslado));
		
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

		lineas.stream()
			 .forEach(i -> i.liberarCupo(traslado));
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}

	public void doCancelarFromCheckin() {
		
		lineas.stream()
			 .forEach(i -> i.liberarCupo(traslado));
		
		cliente.añadirVoucher(new Voucher(this.costoServicios() + this.costoTasasTraslado()));
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}

	public void doCancelarFromEnCurso() {

		cliente.añadirVoucher(new Voucher(this.costoServicios()));
		
		this.cambiarEstado(new EstadoCancelado(this));
		
	}
	
	public void doAgregarItem(ItemCatalogo item, RangoDeFechas periodo) {

		LineaDeReserva linea = new LineaDeReserva(item, periodo);
		
		if(!linea.tieneDisponibilidad()) throw new NoHayDisponibilidadException();
		
		lineas.add(linea);
		
	}

	public void doQuitarItem(ItemCatalogo item) {

		items.remove(item);
		
	}

	public void cambiarMetodoDePago(MetodoDePago metodoDePago) {

		this.metodoDePago = metodoDePago;
		
	}
	
	public void generarComprobanteAlCliente() {
		
		cliente.añadirComprobante(new Comprobante(this.costoTotalReserva()
				 , this.costoServicios()
				 , this.costoTasasTraslado()));
		
	}

	public String obtenerEmailDelCliente() {

		return cliente.getDireccionDeEmail();
		
	}

}
