package ru.ifmo.se.termwork.service.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDTO;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class StudentMapper {

    @Autowired public StudentMapper(){

    }

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "patronymic", target = "patronymic"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "serialNumber", target = "serialNumber"),
    })
    public abstract Student studentDtoToStudent(StudentDTO studentDTO);
}
