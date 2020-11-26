package br.com.hvadriano.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hvadriano.converter.DozerConverter;
import br.com.hvadriano.data.model.Account;
import br.com.hvadriano.data.vo.v1.AccountVO;
import br.com.hvadriano.exception.ResourceNotFoundException;
import br.com.hvadriano.repository.AccountRepository;

@Service
public class AccountServices {
	
	@Autowired
	AccountRepository repository;
		
	public AccountVO create(AccountVO account) {
		var entity = DozerConverter.parseObject(account, Account.class);
		var vo = DozerConverter.parseObject(repository.save(entity), AccountVO.class);
		return vo;
	}
	
	public Page<AccountVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToAccountVO);
	}	
	
	private AccountVO convertToAccountVO(Account entity) {
		return DozerConverter.parseObject(entity, AccountVO.class);
	}
	
	public AccountVO findById(Long id) {

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, AccountVO.class);
	}
		
	public AccountVO update(AccountVO account) {
		var entity = repository.findById(account.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setBalance(account.getBalance());
		entity.setEnabled(account.getEnabled());
		
		var vo = DozerConverter.parseObject(repository.save(entity), AccountVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		Account entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}
