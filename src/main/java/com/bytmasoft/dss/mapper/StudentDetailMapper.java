package com.bytmasoft.dss.mapper;

import com.bytmasoft.dss.dto.StudentDto;
import com.bytmasoft.dss.entity.Course;
import com.bytmasoft.dss.entity.Student;
import com.bytmasoft.dss.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentDetailMapper {

StudentDetailMapper INSTANCE  = Mappers.getMapper(StudentDetailMapper.class);


//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "phone", target = "phone")
//    @Mapping(source = "address", target = "address")
//    @Mapping(source = "courses", target = "courses")
//    StudentDto studentToStudentDetailDTO(Student student);

    // Map from List of Teacher entities to StudentDetailDTO teachers field
//    List<Teacher> teachersToDTO(List<Teacher> teachers);

//    List<Course> coursesToDTO(List<Course> courses);
  /*  @Mapping(source = "student.name", target = "studentName")
    @Mapping(source = "teacher.name", target = "teacherName")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(source = "address.line", target = "addressLine")
    StudentDetailDTO toStudentDetailDTO(Student student, Teacher teacher, Course course, Address address);*/



}
