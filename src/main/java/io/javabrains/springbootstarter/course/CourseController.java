package io.javabrains.springbootstarter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.springbootstarter.topic.Topic;
import io.javabrains.springbootstarter.topic.TopicService;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/topics/{id}/courses")
	public List<Course> getAllCourses(@PathVariable String id){
		return courseService.getAllCourses(id);
	}
	
	@RequestMapping("/topics/{topicId}/courses/{id}")
	public Course getCourse(@PathVariable String id, @PathVariable String topicId){
		return courseService.getCourse(id, topicId);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/topics/{topicId}/courses")
	public void addCourse(@RequestBody Course course, @PathVariable String topicId){
		if(topicService.getTopic(topicId)!=null){
			course.setTopic(topicService.getTopic(topicId));
			courseService.addCourse(course);
		}
		else{
			Topic topic = new Topic(topicId,"","");
			topicService.addTopic(topic);
			course.setTopic(topic);
			courseService.addCourse(course);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{topicId}/courses/{id}")
	public void updateCourse(@RequestBody Course course, @PathVariable String topicId, @PathVariable String id){
		if(topicService.getTopic(topicId)!=null){
			course.setTopic(topicService.getTopic(topicId));
			courseService.updateCourse(course);
		}
		else{
			Topic topic = new Topic(topicId,"","");
			topicService.addTopic(topic);
			course.setTopic(topic);
			courseService.updateCourse(course);
		}
	}
		
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{topicId}/courses/{id}")
	public void deleteCourse(@PathVariable String id){
		courseService.deleteCourse(id);
	}
	
	@RequestMapping("courses/{searchTerm}")
	public List<Course> getCoursesBySearchTerm(@PathVariable String searchTerm){
		return courseService.getCoursesBySearchTerm(searchTerm);
	}
	
}
