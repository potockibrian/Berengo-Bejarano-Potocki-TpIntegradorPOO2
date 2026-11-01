package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TarjetaDeCreditoTest {
	
	private TarjetaDeCreditoAPI api;
	private MetodoDePago metodo;
	
	@BeforeEach
	void setUp() {
		
		api = mock(TarjetaDeCreditoAPI.class);
		metodo = new TarjetaDeCredito(
				api, 1234,"codigo","fechavencimiento");
		
	}
	
    @Test
    void testPagoSinDatosValidosLanzaExcepcion() {
    	
    	when(api.validarDatos(anyInt(),any(),any()))
    		.thenReturn(false);
        
        assertThrows(
        		IllegalArgumentException.class, 
        		() -> metodo.pagar(3000f));
        
        verify(api).validarDatos(anyInt(),any(),any());
        verify(api,never()).preAutorizacion();
        verify(api, never()).transferenciaInmediata();
    
    }
    
    @Test
    void testConDatosValidosRealizaPreAutorizacionYTransferenciaInmediata() {
    	
    	when(api.validarDatos(anyInt(),any(),any()))
    		.thenReturn(true);
    	when(api.transferenciaInmediata())
    		.thenReturn("OP-123");
    	
    	metodo.pagar(3000f);
    	
    	verify(api).validarDatos(anyInt(), any(), any());
    	verify(api).preAutorizacion();
    	verify(api).transferenciaInmediata();
    	verify(api).generarYRegistrarVoucher("OP-123");
    	
    	assertEquals("OP-123", metodo.getCodigoTransaccion());	
    }	
}