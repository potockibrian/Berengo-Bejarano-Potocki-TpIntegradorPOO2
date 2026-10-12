package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoFinalizadoTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private MailSenderI mailSender;
	
	@BeforeEach
	void setUp() {

		mailSender = mock(MailSenderI.class);
		cliente = mock(Cliente.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoFinalizado(reserva);
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar(items);
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar(items);
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionDeEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva finalizada")
				, eq("Se ha finalizado vuestra reserva")
				, eq(null));
		
	}
	
	@Test
	void testGenerarFactura() {
		
		when(reserva.getCliente()).thenReturn(cliente);
		estado.generarFactura(reserva);
		
		verify(cliente).añadirComprobante(any(Comprobante.class));
		verify(cliente).añadirVoucher(any(Voucher.class));
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never()).getCliente();
		
	}
	
}