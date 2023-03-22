package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";
     }

     /*
      * Metodo que recibe los datos procedentes de los controles del formulario
      */

      @PostMapping("/altaEstudiante")
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
}
