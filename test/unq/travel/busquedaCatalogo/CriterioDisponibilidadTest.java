package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

class CriterioDisponibilidadTest {

	private ItemCatalogo item;
	private Criterio criterio;
	private RangoDeFechas periodo;

	@BeforeEach
	void setUp() {
		periodo = mock(RangoDeFechas.class);
		item = mock(ItemCatalogo.class);
		criterio = new CriterioDisponibilidad(periodo);
	}

	@Test
	void testCumpleCuandoElItemTieneDisponibilidad() {

		when(item.tieneDisponibilidad(periodo)).thenReturn(true);

		assertTrue(criterio.cumpleConCriterio(item));
		
		verify(item).tieneDisponibilidad(periodo);

	}

	@Test
	void testNoCumpleCuandoElItemNoTieneDisponibilidad() {

		when(item.tieneDisponibilidad(periodo)).thenReturn(false);

		assertFalse(criterio.cumpleConCriterio(item));
		
		verify(item).tieneDisponibilidad(periodo);

	}

}
