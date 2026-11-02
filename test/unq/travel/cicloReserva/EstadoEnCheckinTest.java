package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoEnCheckinTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSenderI mailSender;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		
		mailSender = mock(MailSenderI.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		cliente = mock(Cliente.class);
		
		estado = new EstadoEnCheckin(reserva);
		
	}
	
	@Test
	void testAgregarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.agregarItem(item, items));
		
		//estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testQuitarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.quitarItem(item, items));
		
		//estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
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
		
		estado.ejecutar();
		
		verify(reserva).cambiarEstado(any(EstadoEnCurso.class));
		
	}
	
	@Test
	void testCompletar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
		//estado.completar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		// TODO: testear e implementar el reembolso y la liberacion de cupos
		
		when(reserva.getCliente()).thenReturn(cliente);
		List<ItemCatalogo> itemsLocal = new ArrayList<ItemCatalogo>();
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		ItemCatalogo item2 = mock(ItemCatalogo.class);
		itemsLocal.add(item1);
		itemsLocal.add(item2);
		
		estado.cancelar(itemsLocal);
		
		verify(item1).incrementarCupo();
		verify(item2).incrementarCupo();
		verify(cliente).añadirVoucher(any(Voucher.class));
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender, never()).enviarMail(any(),any(),any(),any());
		
	}
	
	@Test
	void testGenerarFactura() {
		
		estado.generarFactura(reserva);
		
		verify(reserva, never()).getCliente();
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never()).getCliente();
		
	}
	
}