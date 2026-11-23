package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtributoSinValorExceptionTest {

	@Test
	void testAtributoSinValorException() {

		try {
			
			throw new AtributoSinValorException("Error");
			
		}
		catch (AtributoSinValorException e){
			
			assertEquals("Error", e.getMessage());
			
		}
		
		
	}

}
