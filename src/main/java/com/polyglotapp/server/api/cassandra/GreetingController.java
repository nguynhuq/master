package com.polyglotapp.server.api.cassandra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.polyglotapp.server.data.model.cassandra.Greeting;
import com.polyglotapp.server.data.repository.cassandra.GreetRepository;

@RestController
public class GreetingController {
	
	@Autowired
	private GreetRepository greetRepository;
	
    @RequestMapping(value = "/greeting",method = RequestMethod.GET)
    @ResponseBody
    public List<Greeting> greeting() {
        List<Greeting> greetings = new ArrayList<>();
        greetRepository.findAll().forEach(e->greetings.add(e));
        return greetings;
    }

    @RequestMapping(value = "/greeting/{user}/",method = RequestMethod.GET)
    @ResponseBody
    public List<Greeting> greetingUserLimit(@PathVariable String user,Integer limit) {
        List<Greeting> greetings = new ArrayList<>();
        greetRepository.findByUser(user,limit).forEach(e->greetings.add(e));
        return greetings;
    }
    
    @RequestMapping(value = "/greeting",method = RequestMethod.POST)
    @ResponseBody
    public String saveGreeting(@RequestBody Greeting greeting) {
        greeting.setCreationDate(new Date());
        greetRepository.save(greeting);
        return "OK";
    }
}
