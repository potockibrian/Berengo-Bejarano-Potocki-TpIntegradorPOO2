package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TransferenciaBancariaTest {
	
	private TransferenciaBancariaAPI api;
	private TransferenciaBancaria metodo;
	
	@BeforeEach
	void setUp() {
		
		api = mock(TransferenciaBancariaAPI.class);
		metodo = new TransferenciaBancaria(
				api, "cbu", "alias", false);
		
	}
	
    @Test
    void testPagoSinDatosValidosLanzaExcepcion() {
    	
    	when(api.validarDatos(any(),any()))
    		.thenReturn(false);
        
        assertThrows(
        		IllegalArgumentException.class, 
        		() -> metodo.pagar(3000f));
        
        verify(api).validarDatos(any(),any());
        verify(api, never()).ejecutarTransferenciaInmediata();
        verify(api, never()).programarTransferencia();
        verify(api, never()).generarYRegistrarComprobante(any());
    }
	
    @Test
    void testPagoConTransferenciaInmediata() {
    	when(api.validarDatos(any(), any()))
    		.thenReturn(true);
    	when(api.ejecutarTransferenciaInmediata())
    		.thenReturn("OP-456");
    	
    	metodo.pagar(3000f);
    	
    	verify(api).validarDatos(any(), any());
    	verify(api).ejecutarTransferenciaInmediata();
    	verify(api, never()).programarTransferencia();
    	verify(api).generarYRegistrarComprobante("OP-456");
    	
    	assertEquals("OP-456", metodo.getCodigoTransaccion());
    }
    
    @Test
    void testPagoConTransferenciaProgramada() {
    	when(api.validarDatos(any(), any()))
    		.thenReturn(true);
    	when(api.programarTransferencia())
    		.thenReturn("OP-789");
    	
    	TransferenciaBancaria transferencia =
    			new TransferenciaBancaria(
    					api, "cbu", "alias", true);
    
    	transferencia.pagar(3000f);
    	
    	verify(api).validarDatos(any(), any());
    	verify(api).programarTransferencia();
    	verify(api, never()).ejecutarTransferenciaInmediata();
    	verify(api).generarYRegistrarComprobante("OP-789");
    	
    	assertEquals("OP-789", transferencia.getCodigoTransaccion());
    }
}