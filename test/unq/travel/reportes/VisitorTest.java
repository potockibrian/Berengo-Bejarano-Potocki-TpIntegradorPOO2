package unq.travel.reportes;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.PaqueteTuristico;

import unq.travel.catalogo.ServicioIndividual;
import unq.travel.catalogo.PaqueteTuristico;

public class VisitorTest {
	@Test
	void testUnServicioIndividualAceptaUnVisitor() {
		ServicioIndividual servicio = new ServicioIndividual(
				"Hotel",
				"Hotel en la playa",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				15000,
				0,
				10,
				new CalendarioDeCupos(0)
		);
		
		Visitor visitor = mock(Visitor.class);
		
		servicio.aceptar(visitor);
		
		verify(visitor).visitar(servicio);
	}
	
	@Test
	void testUnPaqueteTuristicoAceptaUnVisitor() {
		PaqueteTuristico paquete = new PaqueteTuristico(
				"Paquete de vacaciones",
				"Paquete de prueba",
				0.10f, null
		);
		
		Visitor visitor = mock(Visitor.class);
		
		paquete.aceptar(visitor);
		
		verify(visitor).visitar(paquete);
	}
}
