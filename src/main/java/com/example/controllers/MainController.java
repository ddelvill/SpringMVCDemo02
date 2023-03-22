package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@Controller
@RequestMapping("/")


public class MainController {
    // Los metodos que estan aquí dentro son los Handlers Mapping

    private static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private TelefonoService telefonoService;

    /*
     * El medoto siguiente devulve un listado de estudiantes
     */
    @GetMapping("/listar")
    public ModelAndView listar() {

        List<Estudiante> estudiantes = estudianteService.findAll();
        
        ModelAndView mav = new ModelAndView("views/listarEstudiantes");
        mav.addObject("estudiantes", estudiantes);
        return mav;

    }

    /*
     * Muestra el formulario de alta de estudiante
     */

     @GetMapping("/frmAltaEstudiante")
     public String formularioAltaEstudiante(Model model) {

        List<Facultad> facultades = facultadService.findAll();

        Estudiante estudiante = new Estudiante();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";
     }

     /*
      * Metodo que recibe los datos procedentes de los controles del formulario
      */

      @PostMapping("/altaModificacionEstudiante")
      public String altaEstudiante(@ModelAttribute Estudiante estudiante,
          @RequestParam(name="numerosTelefonos") String telefonosRecibidos) {
          
        LOG.info("Telefonos recibidos: " + telefonosRecibidos);   

        
        List<String> listadoNumerosTelefonos = null;

        if(telefonosRecibidos != null) {

          String[] arrayTelefonos = telefonosRecibidos.split(";");

          listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);

        }




        estudianteService.save(estudiante);

        if(listadoNumerosTelefonos != null) {
          listadoNumerosTelefonos.stream().forEach(n -> {
            Telefono  telefonoObject = Telefono
              .builder()
              .numero(n)
              .estudiante(estudiante)
              .build();
            telefonoService.save(telefonoObject);

          });
        }
        
        return "redirect:/listar";

      }


      /** 
       * Muestra el formulario para actualizar un estudiante
       */
      @GetMapping("/frmActualizar/{id}")
      public String frmActualizarEstudiante (@PathVariable(name = "id") int idEstudiante,
                                      Model model) {

      
      Estudiante estudiante = estudianteService.findById(idEstudiante);

      List<Telefono> todosTelefonos = telefonoService.findAll();

      List<Telefono> telefonosDelEstudiante = todosTelefonos.stream()
                  .filter(t -> t.getEstudiante().getId() == idEstudiante)
                  .collect(Collectors.toList());
      String numerosDeTelefono = telefonosDelEstudiante.stream().map(t->t.getNumero()).collect(Collectors.joining(";"));

      List<Facultad> facultades = facultadService.findAll();

      model.addAttribute("estudiante", estudiante);
      model.addAttribute("telefonos", numerosDeTelefono);
      model.addAttribute("facultades", facultades);


      return "views/formularioAltaEstudiante";
       }
}
