package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoCanceladoTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSenderI mailSender;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		
		cliente = mock(Cliente.class);
		mailSender = mock(MailSenderI.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoCancelado(reserva);
		
	}
	
	@Test
	void testAgregarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.agregarItem(item, items));
		
		//estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		//assertEquals(items.size(), 0);
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testQuitarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.quitarItem(item, items));
		
		//estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		//assertEquals(items.size(), 0);
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testConfirmar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.confirmar(items));
		
		//estado.confirmar(items);
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCheckin() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.checkin());
		
		//estado.checkin();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testEjecutar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.ejecutar());
		
		//estado.ejecutar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCompletar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
		//estado.completar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCancelar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.cancelar(items));
		
		//estado.cancelar(items);
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender, never()).enviarMail(any(),any(),any(),any());
		
	}
	
	@Test
	void testGenerarFactura() {
		
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.generarFactura(reserva);
		
		verify(reserva, never()).getCliente();
		verify(cliente, never()).añadirComprobante(any(Comprobante.class));
		verify(cliente, never()).añadirVoucher(any(Voucher.class));
		
	}
	
	@Test
	void testFidelizar() {
		
		when(reserva.getCliente()).thenReturn(cliente);
		when(cliente.getDireccionDeEmail()).thenReturn("testmail@test.domain");
		
		estado.fidelizar(reserva, mailSender);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva cancelada")
				, eq("Se ha cancelado vuestra reserva")
				, any(Cupon.class));
		
	}
	
}