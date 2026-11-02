package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

public class EstadoEnCheckin extends EstadoDeReserva {

	public EstadoEnCheckin(Reserva reserva) {
		super(reserva);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void confirmar(List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void checkin() {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void ejecutar() {
		
		reserva.cambiarEstado(new EstadoEnCurso(reserva));
		
	}

	@Override
	public void completar() {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void cancelar(List<ItemCatalogo> items) {

	items.stream().forEach(i -> i.incrementarCupo());
	
	reserva.getCliente().añadirVoucher(new Voucher());
	
	reserva.cambiarEstado(new EstadoCancelado(reserva));
		
	}

	@Override
	public void notificarPorEmail(Reserva reserva, MailSenderI mailSender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generarFactura(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fidelizar(Reserva reserva, MailSenderI mailSender) {
		// TODO Auto-generated method stub

	}

}
