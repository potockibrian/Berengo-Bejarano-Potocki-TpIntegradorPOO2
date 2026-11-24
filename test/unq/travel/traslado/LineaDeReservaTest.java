package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

class LineaDeReservaTest {

	private ItemCatalogo item;
	private RangoDeFechas periodo;
	private LineaDeReserva linea;
	private EstrategiaTraslado estrategia;
	
	@BeforeEach
	void setUp() throws Exception {
		
		estrategia = mock(EstrategiaTraslado.class);
		item = mock(ItemCatalogo.class);
		periodo = mock(RangoDeFechas.class);
		
		linea = new LineaDeReserva(item, periodo);
		
	}

	@Test
	void testPrecioFinal() {

		linea.precioFinal();
		
		verify(item).getPrecioFinal();
		
	}
	
	@Test
	void testReservarCupo() {
		
		linea.reservarCupo(estrategia);
		
		verify(item).reservar(periodo);
		
	}
	
	@Test
	void testLiberarCupo() {
		
		linea.liberarCupo(estrategia);
		
		verify(item).liberar(periodo);
		
	}

}
