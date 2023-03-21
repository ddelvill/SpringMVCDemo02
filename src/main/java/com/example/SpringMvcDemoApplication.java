package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.entities.Estudiante.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class SpringMvcDemoApplication implements CommandLineRunner{

	@Autowired
	private FacultadService facultadService;

	@Autowired
	private EstudianteService estudianteService;

	@Autowired
	private TelefonoService telefonoService;


	public static void main(String[] args) {
		SpringApplication.run(SpringMvcDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * Agregar registros de muestra, para Facultad, Estudiante y Telefono.
		 */

		facultadService.save(Facultad.builder()
		 	.nombre("Informatica")
			.build());

		facultadService.save(Facultad.builder()
		 	.nombre("Biología")
			.build());

		estudianteService.save(Estudiante.builder()
			.id(1)
			.nombre("Mara")
			.primerApellido("Romero")
			.segundoApellido("Díaz")
			.fechaAlta(LocalDate.of(2021, Month.SEPTEMBER, 2))
			.fechaNacimiento(LocalDate.of(1999, Month.MAY, 12))
			.genero(Genero.MUJER)
			.beca(2300.50)
			.facultad(facultadService.findById(1))
			.build());

		estudianteService.save(Estudiante.builder()
			.id(2)
			.nombre("Belén")
			.primerApellido("Chocano")
			.segundoApellido("Collado")
			.fechaAlta(LocalDate.of(2010, Month.APRIL, 12))
			.fechaNacimiento(LocalDate.of(1997, Month.JANUARY, 8))
			.genero(Genero.MUJER)
			.beca(6500.90)
			.facultad(facultadService.findById(2))
			.build());

		telefonoService.save(Telefono.builder()
		.id(1)
		.numero("968674532")
		.estudiante(estudianteService.findById(1))
		.build());

		telefonoService.save(Telefono.builder()
		.id(2)
		.numero("623456789")
		.estudiante(estudianteService.findById(2))
		.build());

		telefonoService.save(Telefono.builder()
		.id(3)
		.numero("623456989")
		.estudiante(estudianteService.findById(2))
		.build());



	}

}
