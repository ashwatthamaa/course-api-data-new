package io.javabrains.springbootstarter.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<Course, String> {
	
	public List<Course> findByTopicId(String topicId);
	
	@Query("SELECT c from Course c WHERE " +
			"LOWER(c.id) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(c.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	public List<Course> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
