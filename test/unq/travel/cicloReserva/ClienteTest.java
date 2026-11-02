package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.traslado.Voucher;

class ClienteTest {

	private Cliente cliente;
	private List<Comprobante> comprobantes;
	private List<Voucher> vouchers;
	private Voucher voucher;
	private Comprobante comprobante;
	
	@BeforeEach
	void setUp() {
		
		comprobantes = new ArrayList<Comprobante>();
		vouchers = new ArrayList<Voucher>();
		cliente = new Cliente("mail@domain.example", comprobantes, vouchers);
		comprobante = mock(Comprobante.class);
		voucher = mock(Voucher.class);
	}

	@Test
	void testDireccionDeEmail() {

		assertEquals("mail@domain.example", cliente.getDireccionDeEmail());
		
	}
	
	@Test
	void testAñadirVoucher() {
		
		cliente.añadirVoucher(voucher);

		assertTrue(vouchers.contains(voucher));
		
	}
	
	@Test
	void testAñadirComprobante() {
		
		cliente.añadirComprobante(comprobante);

		assertTrue(comprobantes.contains(comprobante));
		
	}

}
