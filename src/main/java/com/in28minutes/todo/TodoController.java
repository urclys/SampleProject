package com.in28minutes.todo;


import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Init;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.method.annotation.SessionAttributesHandler;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));
	}
	
	
	@RequestMapping(value="/list-todos",method=RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		model.addAttribute("name",retrieveLoggedInUser());
		model.addAttribute("todos", service.retrieveTodos(retrieveLoggedInUser()));
		return "list-todos";
	}


	private String retrieveLoggedInUser() {
		Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			return((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
	
	@RequestMapping(value="/add-todo",method=RequestMethod.GET)
	public String showAddTodo(ModelMap model) {
		model.addAttribute("todo",new Todo(0, retrieveLoggedInUser(), "", new Date(),
				false));
		return "todo";
	}
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model,@Valid Todo todo,BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		service.addTodo((String) model.get("name"), todo.getDesc(), todo.getTargetDate(), false);
		model.clear();
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method=RequestMethod.GET)
	public String DeleteTodo(ModelMap model,@RequestParam int id) {
		service.deleteTodo(id);
		model.clear();
		return "redirect:/list-todos";
		
	}
	@RequestMapping(value="/update-todo",method=RequestMethod.GET)
	public String ShowUpdateTodo(ModelMap model,@RequestParam int id) {
		model.addAttribute("todo",service.retrieveTodoById(id));
		return "todo";
	}
	@RequestMapping(value="/update-todo",method=RequestMethod.POST)
	public String UpdateTodo(ModelMap model,@Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUser((String) model.get("name"));
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
}
