package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassListView;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassListView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ClassService {
	ResponseEntity<NewClassResponse> createGroupClass(NewGroupClassRequest dto);

	ResponseEntity<NewClassResponse> createIndividualClass(NewIndividualClassRequest dto);

	ResponseEntity<Void> updateGroupClass(Long id, UpdateGroupClassRequest dto);

	ResponseEntity<Void> updateIndividualClass(Long id, UpdateIndividualClassRequest dto);

	ResponseEntity<Page<GroupClassListView>> getAllGroupClasses(Pageable pageable);

	ResponseEntity<Page<IndividualClassListView>> getAllIndividualClasses(Pageable pageable);
}
