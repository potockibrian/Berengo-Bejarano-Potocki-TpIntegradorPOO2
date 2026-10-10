package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioNotTest {

	private ItemCatalogo item;
	private Criterio criterioEnvuelto;
	private Criterio criterioNot;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		criterioEnvuelto = mock(Criterio.class);
		criterioNot = new CriterioNot(criterioEnvuelto);
	}

	@Test
	void testNoCumpleCuandoElCriterioEnvueltoCumple() {

		when(criterioEnvuelto.cumpleConCriterio(item)).thenReturn(true);

		assertFalse(criterioNot.cumpleConCriterio(item));

	}

	@Test
	void testCumpleCuandoElCriterioEnvueltoNoCumple() {

		when(criterioEnvuelto.cumpleConCriterio(item)).thenReturn(false);

		assertTrue(criterioNot.cumpleConCriterio(item));

	}

}
