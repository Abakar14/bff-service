package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailCreateDTO;
import com.bytmasoft.dss.dto.StudentDetailDTO;
import com.bytmasoft.dss.service.BFFServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bff")
public class BFFControllerImpl implements BFFControllerApi {

    private final BFFServiceImpl bffService;


    @Override
    public ResponseEntity<StudentDetailDTO> getStudentDetails(Long studentId, String authorizationHeader) {
        return ResponseEntity.ok(bffService.getStudentDetail(studentId, authorizationHeader));
    }

    @Override
    public ResponseEntity<StudentDetailDTO> addStudentWithPicture(StudentDetailCreateDTO studentCreateDto, MultipartFile picture) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<StudentDetailDTO> addPictureToStudent(Long studentId, MultipartFile file) throws IOException {
        return null;
    }
}
