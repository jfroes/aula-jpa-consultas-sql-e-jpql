package com.devsuperior.aula.services;

import com.devsuperior.aula.dto.PersonDTO;
import com.devsuperior.aula.dto.PersonDepartmentDTO;
import com.devsuperior.aula.entities.Department;
import com.devsuperior.aula.entities.Person;
import com.devsuperior.aula.repositories.DepartmentRepository;
import com.devsuperior.aula.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public PersonDepartmentDTO insert(PersonDepartmentDTO dto) {
      Person entity = new Person();


      entity.setName(dto.getName());
      entity.setSalary(dto.getSalary());

      //Department department = new Department();
        /*
        * Desse forma o nome do departamento aparece como null no json
        * pois new Department(); nao é um objeto gerenciado pela JPA,
        * é um objeto transient.
        * */

      Department department = departmentRepository.getReferenceById(dto.getDepartment().getId());

      /*
      * Dessa forma instanciamos um objeto gerenciado pela JPA,
      * e enquanto houver a sessão da JPA, se precisarmos de algum dado dela
      * a JPA vai no banco e busca
      * */

      department.setId(dto.getDepartment().getId());
      entity.setDepartment(department);

      entity = repository.save(entity);


      return new PersonDepartmentDTO(entity);
  }


    public PersonDTO insert(PersonDTO dto) {

        Person entity = new Person();
        entity.setName(dto.getName());
        entity.setSalary(dto.getSalary());

        Department department = departmentRepository.getReferenceById(dto.getDepartmentId());
        department.setId(dto.getDepartmentId());

        entity.setDepartment(department);

        entity = repository.save(entity);


        return new PersonDTO(entity);
    }
}
