package br.com.hvadriano.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hvadriano.converter.DozerConverter;
import br.com.hvadriano.data.model.Transaction;
import br.com.hvadriano.data.vo.v1.TransactionVO;
import br.com.hvadriano.exception.ResourceNotFoundException;
import br.com.hvadriano.repository.TransactionRepository;
import br.com.hvadriano.repository.TransactionRepository;

@Service
public class TransactionServices {
	
	@Autowired
	TransactionRepository repository;
		
	public TransactionVO create(TransactionVO transaction) {
		var entity = DozerConverter.parseObject(transaction, Transaction.class);
		var vo = DozerConverter.parseObject(repository.save(entity), TransactionVO.class);
		return vo;
	}
	
	public Page<TransactionVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToTransactionVO);
	}	
	
	private TransactionVO convertToTransactionVO(Transaction entity) {
		return DozerConverter.parseObject(entity, TransactionVO.class);
	}
	
	public TransactionVO findById(Long id) {

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, TransactionVO.class);
	}
	
	public void delete(Long id) {
		Transaction entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}
