package br.com.hvadriano.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hvadriano.data.vo.v1.AccountVO;
import br.com.hvadriano.services.AccountServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "AccountEndpoint")
@RestController
@RequestMapping("/api/account/v1")
public class AccountController {
	
	@Autowired
	private AccountServices service;
	
	@Autowired
	private PagedResourcesAssembler<AccountVO> assembler;
	
	@ApiOperation(value = "Find all people" ) 
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		Page<AccountVO> accounts =  service.findAll(pageable);
		accounts
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(AccountController.class).findById(p.getKey())).withSelfRel()
				)
			);
		
		PagedResources<?> resources = assembler.toResource(accounts);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find a specific account by your ID" )
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public AccountVO findById(@PathVariable("id") Long id) {
		AccountVO accountVO = service.findById(id);
		accountVO.add(linkTo(methodOn(AccountController.class).findById(id)).withSelfRel());
		return accountVO;
	}	
	
	@ApiOperation(value = "Create a new account") 
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public AccountVO create(@RequestBody AccountVO account) {
		AccountVO accountVO = service.create(account);
		accountVO.add(linkTo(methodOn(AccountController.class).findById(accountVO.getKey())).withSelfRel());
		return accountVO;
	}
	
	@ApiOperation(value = "Update a specific account")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public AccountVO update(@RequestBody AccountVO account) {
		AccountVO accountVO = service.update(account);
		accountVO.add(linkTo(methodOn(AccountController.class).findById(accountVO.getKey())).withSelfRel());
		return accountVO;
	}	
	
	@ApiOperation(value = "Delete a specific account by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}	
	
}