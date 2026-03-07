package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyectoPunto4.MailerStub;


class MailerStubTest {

	private MailerStub mailer;

	@BeforeEach
	void setUp() {
		mailer = new MailerStub();
	}

	@Test
	void testEnviarCorreoDevuelveTrue() {
		boolean resultado = mailer.enviarCorreo("destino@test.com", "Mensaje de prueba");
		assertTrue(resultado);
	}

	@Test
	void testEnviarCorreoConDireccionVaciaDevuelveTrue() {
		boolean resultado = mailer.enviarCorreo("", "Mensaje");
		assertTrue(resultado);
	}

	@Test
	void testEnviarCorreoConMensajeVacioDevuelveTrue() {
		boolean resultado = mailer.enviarCorreo("a@b.com", "");
		assertTrue(resultado);
	}
}
