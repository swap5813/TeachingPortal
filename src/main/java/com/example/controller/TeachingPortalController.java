package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Teacher;
import com.example.repository.TeacherRepository;

@RestController
@RequestMapping("/api/v1")
public class TeachingPortalController {

	@Autowired
	private TeacherRepository teacherRepository;
	

	@GetMapping("/teachers")
	public List<Teacher> getAllTeachers(){
		return this.teacherRepository.findAll();
	}
	

	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable(value = "id") Long teacherId) 
			throws ResourceNotFoundException{
		Teacher teacher = this.teacherRepository.findById(teacherId).
				orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
		return ResponseEntity.ok().body(teacher);
		
	}
	
	
	@PostMapping("/teachers")
	public Teacher createEmployee(@RequestBody Teacher teacher) {
		return this.teacherRepository.save(teacher);
	}
	
	

	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teacher> updateEmployee(@PathVariable(value = "id") Long teacherId,
			 @RequestBody Teacher teacherDetails) throws ResourceNotFoundException {
		Teacher teacher = this.teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + teacherId));

		teacher.setLastName(teacherDetails.getLastName());
		teacher.setFirstName(teacherDetails.getFirstName());
		teacher.setEmailID(teacherDetails.getEmailID());
		teacher.setCity(teacherDetails.getCity());
		teacher.setAvailability(teacherDetails.isAvailability());
		teacher.setContactNumber(teacherDetails.getContactNumber());
		teacher.setExpertise(teacherDetails.getExpertise());
		
		final Teacher updatedEmployee = this.teacherRepository.save(teacher);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	

	@DeleteMapping("/teachers/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long teacherId)
			throws ResourceNotFoundException {
		Teacher teacher = this.teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + teacherId));

		this.teacherRepository.delete(teacher);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
