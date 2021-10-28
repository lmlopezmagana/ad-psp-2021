package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.repos.AlumnoRepository;
import com.salesianostriana.dam.service.base.BaseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoService extends BaseService<Alumno, Long, AlumnoRepository> {

    /**
     * Devuelve 5 alumnos, ordenados por fecha de nacimiento descendente
     * cuyo apellido sea igual al proporcionado
     */
    public List<Alumno> cincoPorApellido(String apellido) {
        return repositorio.findTop5ByApellido1OrderByFechaNacimientoDesc(apellido);
    }

    /**
     * Obtiene desde el repositorio como mucho 3 alumnos cuyo primer o segundo
     * apellido sea el proporcionado.
     * @return Una lista con el nombre completo de los alumnos como String.
     */
    @Transactional
    public List<String> nombreAlumnosContieneApellido(String apellido) {
        return repositorio
                .findTop3ByApellido1ContainsOrApellido2Contains(apellido, apellido)
                .map(a -> a.getNombre() + " " + a.getApellido1() + " " + a.getApellido2())
                .collect(Collectors.toList());
    }


    public List<Alumno> alumnosSinCurso() {
        return repositorio.encuentraAlumnoSinCurso();
    }



    public List<Alumno> alumnosDeUnCursoNacidosDespuesDe(String fecha, String nombreCurso) {

        return repositorio.alumnosNacidosDespuesDe(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd")), nombreCurso);

    }

    public List<Alumno> alumnosConSpecification(Specification<Alumno> spec) {
        return repositorio.findAll(spec);
    }

}
