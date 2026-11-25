package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.ArgumentMatchers.any;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class VisitanteServiciosMasReservadosTest {

	private RangoDeFechas periodo;
	private ServicioIndividual servicio1;
	private ServicioIndividual servicio2;
	private VisitanteServiciosMasReservados visitante;
	private Catalogo catalogo;
	private PaqueteTuristico paquete;
	private RegistroDeVentas registroDeVentas;
	
	@BeforeEach
	void setUp() throws Exception {
		
		periodo = mock(RangoDeFechas.class);
		
		servicio1 = mock(ServicioIndividual.class);
		servicio2 = mock(ServicioIndividual.class);
		paquete = mock(PaqueteTuristico.class);
		
		catalogo = new Catalogo();
		registroDeVentas = mock(RegistroDeVentas.class);
		
		visitante = new VisitanteServiciosMasReservados(
				periodo, registroDeVentas);
		
	}

	@Test
	void testProcesarCatalogoVacio() {
		
		ReporteServiciosMasReservados reporte = 
				visitante.procesar(catalogo, periodo);
		
		assertTrue(reporte.getItemsOrdenados().isEmpty());
		
	}
	
	@Test
	void testProcesarConCatalogoConElementos() {
	    catalogo.agregarItem(servicio1);
	    catalogo.agregarItem(paquete);

	    visitante.procesar(catalogo, periodo);

	    verify(servicio1).exportar(visitante);
	    verify(paquete).exportar(visitante);
	}
	
	@Test
	void testVisitarComoPaqueteTuristico() {
		
		visitante.visitar(paquete);
		
		verify(registroDeVentas).cantidadDeVentasDe(paquete, periodo);
		
	}
	
	@Test
	void testVisitarComoServicioIndividual() {
	    visitante.visitar(servicio1);

	    verify(registroDeVentas).cantidadDeVentasDe(servicio1, periodo);
	}
	
	@Test
	void testProcesarIncluyeLaCantidadDeVentasDeCadaProducto() {
	    catalogo.agregarItem(servicio1);
	    catalogo.agregarItem(paquete);

	    when(registroDeVentas.cantidadDeVentasDe(servicio1, periodo))
	        .thenReturn(3);

	    when(registroDeVentas.cantidadDeVentasDe(paquete, periodo))
	        .thenReturn(5);

	    doAnswer(invocation -> {
	        Visitor visitor = invocation.getArgument(0);
	        visitor.visitar(servicio1);
	        return null;
	    }).when(servicio1).exportar(any(Visitor.class));

	    doAnswer(invocation -> {
	        Visitor visitor = invocation.getArgument(0);
	        visitor.visitar(paquete);
	        return null;
	    }).when(paquete).exportar(any(Visitor.class));

	    ReporteServiciosMasReservados reporte =
	        visitante.procesar(catalogo, periodo);

	    assertEquals(3, reporte.getItemsOrdenados().stream()
	        .filter(entry -> entry.getKey() == servicio1)
	        .findFirst().get().getValue());

	    assertEquals(5, reporte.getItemsOrdenados().stream()
	        .filter(entry -> entry.getKey() == paquete)
	        .findFirst().get().getValue());
	}

}
