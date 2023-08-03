package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.*;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.repository.projection.BaseClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.SpecialistItemView;
import kg.musabaev.onlinetutorback.repository.projection.StudentItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

	ResponseEntity<RegisterUserResponse> registerSpecialist(RegisterSpecialistRequest dto);

	ResponseEntity<RegisterUserResponse> registerStudent(RegisterStudentRequest dto);

	ResponseEntity<SpecialistItemView> getSpecialistById(long id);

	ResponseEntity<StudentItemView> getStudentById(long id);

	ResponseEntity<Void> deleteSpecialistById(long id);

	ResponseEntity<Void> deleteStudentById(long id);

	ResponseEntity<Void> updateSpecialistById(long id, UpdateSpecialistRequest dto);

	ResponseEntity<Void> updateStudentById(long id, UpdateStudentRequest dto);

	ResponseEntity<Void> addToFinishedClassesOfStudent(long id, AddOrDeleteClassInStudentList dto);

	ResponseEntity<Void> addToInProcessClassesOfStudent(long id, AddOrDeleteClassInStudentList dto);

	ResponseEntity<Void> deleteFromFinishedClassesOfStudent(long id, AddOrDeleteClassInStudentList dto);

	ResponseEntity<Void> deleteFromInProcessClassesOfStudent(long id, AddOrDeleteClassInStudentList dto);

	ResponseEntity<Page<BaseClassItemView>> getFinishedClassesOfStudent(long id, Pageable pageable);

	ResponseEntity<Page<BaseClassItemView>> getInProcessClassesOfStudent(long id, Pageable pageable);

	ResponseEntity<Void> rateSpecialist(long studentId, long specialistId, RateSpecialist dto);
}
