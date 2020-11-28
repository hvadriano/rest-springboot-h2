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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hvadriano.data.vo.v1.TransactionVO;
import br.com.hvadriano.services.TransactionServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TransactionEndpoint")
@RestController
@RequestMapping("/api/transaction/v1")
public class TransactionController {
	
	@Autowired
	private TransactionServices service;
	
	@Autowired
	private PagedResourcesAssembler<TransactionVO> assembler;
	
	@ApiOperation(value = "Find all Transaction" ) 
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
		
		Page<TransactionVO> transactions =  service.findAll(pageable);
		transactions
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(TransactionController.class).findById(p.getKey())).withSelfRel()
				)
			);
		
		PagedResources<?> resources = assembler.toResource(transactions);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find a specific Transaction by your ID" )
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public TransactionVO findById(@PathVariable("id") Long id) {
		TransactionVO TransactionVO = service.findById(id);
		TransactionVO.add(linkTo(methodOn(TransactionController.class).findById(id)).withSelfRel());
		return TransactionVO;
	}	
	
	@ApiOperation(value = "Create a new Transaction") 
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public TransactionVO create(@RequestBody TransactionVO transaction) {
		TransactionVO transactionVO = service.create(transaction);
		transactionVO.add(linkTo(methodOn(TransactionController.class).findById(transactionVO.getKey())).withSelfRel());
		return transactionVO;
	}
	
//	@ApiOperation(value = "Update a specific Transaction")
//	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
//			consumes = { "application/json", "application/xml", "application/x-yaml" })
//	public TransactionVO update(@RequestBody TransactionVO transaction) {
//		TransactionVO transactionVO = service.update(transaction);
//		transactionVO.add(linkTo(methodOn(TransactionController.class).findById(transactionVO.getKey())).withSelfRel());
//		return transactionVO;
//	}	
	
	@ApiOperation(value = "Delete a specific Transaction by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}	
	
}