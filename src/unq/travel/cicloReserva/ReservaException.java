package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.traslado.Reserva;

public class ReservaException extends EstadoReserva{

	public ReservaException(Reserva reserva) {
		
		super(reserva);
		
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejecutar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void completar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
		
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
