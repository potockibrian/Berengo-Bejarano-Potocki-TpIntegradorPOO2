package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TransferenciaBancariaTest {
	
	private TransferenciaBancariaAPI api;
	private MetodoDePago metodo;
	
	@BeforeEach
	void setUp() {
		
		api = mock(TransferenciaBancariaAPI.class);
		metodo = new TransferenciaBancaria("cbu","alias");
		
	}
	
    @Test
    void testPagoSinDatosValidosLanzaExcepcion() {
    	
    	when(api.validarDatos(any(),any())).thenReturn(false);
        
        assertThrows(RuntimeException.class, () -> metodo.pagar(3000f));
        
        verify(api).validarDatos(any(),any());
    
    }
    
    @Test
    void testPagoConDatosValidosEjecutaLaTransaccionYNotificaElResultado() {
    	
    	when(api.validarDatos(any(),any())).thenReturn(true);
        
        metodo.pagar(3000f);
        
        verify(api).ejecutarTransaccion();
        verify(api).notificar();
    
    }
	
}