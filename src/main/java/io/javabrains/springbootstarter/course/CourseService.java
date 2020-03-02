package io.javabrains.springbootstarter.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> getAllCourses(String topicId){
		List<Course> courses = new ArrayList<>();
		courseRepository.findByTopicId(topicId).forEach(courses::add);
		return courses;
	}
	
	public Course getCourse(String id, String topicId){
		if(this.getAllCourses(topicId).size()==0){
			return null;
		}
		else{
			for(Course c : this.getAllCourses(topicId)){
				if(c.getId().equals(id)){
					return c;
				}
			}
		}
		return null;
	}
	
	public void addCourse(Course course){
		courseRepository.save(course);
	}
	
	public void updateCourse(Course course){
		courseRepository.save(course);
	}

	public void deleteCourse(String id) {
		if(courseRepository.exists(id)){
			courseRepository.delete(id);
		}
	}
	
	public List<Course> getCoursesBySearchTerm(String searchTerm){
		return courseRepository.findBySearchTerm(searchTerm);
	}
}
