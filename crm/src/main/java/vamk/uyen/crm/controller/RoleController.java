package vamk.uyen.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@GetMapping("")
	public List<Role> listAll() {
        return (List<Role>) service.getRoles();
    }
	
	@GetMapping("/{id}")
	public Role getRole(@PathVariable Integer id) {
		return service.getRole(id);
	}
	
	@PostMapping("")
	public Role save(@RequestBody Role role) {
		return service.saveRole(role);
	}
	
    @PutMapping("/{id}")
    public Role update(@PathVariable Integer id, @RequestBody Role role) {
    	role.setId(id);
        return service.saveRole(role);
    }
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteRole(id);
	}
}
