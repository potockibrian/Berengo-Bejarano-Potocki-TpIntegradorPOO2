package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.traslado.Reserva;

public abstract class EstadoDeReserva {

	protected Reserva reserva;

	public EstadoDeReserva(Reserva reserva) {
		
		this.reserva = reserva;
		
	}
	
	public abstract void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) ;

	public abstract void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) ;

	public abstract void confirmar() ;

	public abstract void checkin() ;

	public abstract void ejecutar() ;

	public abstract void completar() ;

	public abstract void cancelar() ;

	public abstract void notificarPorEmail(Reserva reserva, MailSenderI mailSender) ;

	public abstract void generarFactura(Reserva reserva) ;

	public abstract void fidelizar(Reserva reserva, MailSenderI mailSender) ;

}
