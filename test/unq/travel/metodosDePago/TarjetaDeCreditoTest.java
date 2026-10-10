package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TarjetaDeCreditoTest {
	
	private TarjetaDeCreditoAPI api;
	private MetodoDePago metodo;
	
	@BeforeEach
	void setUp() {
		
		api = mock(TarjetaDeCreditoAPI.class);
		metodo = new TarjetaDeCredito(1234,"codigo","fechavencimiento");
		
	}
	
    @Test
    void testPagoSinDatosValidosLanzaExcepcion() {
    	
    	when(api.validarDatos(any(),any(),any())).thenReturn(false);
        
        assertThrows(RuntimeException.class, () -> metodo.pagar(3000f));
        
        verify(api).validarDatos(any(),any(),any());
    
    }
    
    @Test
    void testConDatosValidosRealizaPreAutorizacionYTransferenciaInmediata() {
    	
    	when(api.validarDatos(any(),any(),any())).thenReturn(true);
    	
    	metodo.pagar(3000f);
    	
    	verify(api).preAutorizacion();
    	verify(api).transferenciaInmediata();
    	
    }
	
}