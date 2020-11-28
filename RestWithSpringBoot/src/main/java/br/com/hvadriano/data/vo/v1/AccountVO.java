package br.com.hvadriano.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.hvadriano.data.model.Transaction;
import br.com.hvadriano.data.model.User;

@JsonPropertyOrder({ "id", "balance", "createDate", "enabled", "user", "listTransaction" })
public class AccountVO extends ResourceSupport implements Serializable{

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	
	@Mapping("user")
	@JsonProperty("user")
	private UserVO userVO;
	
	private List<Transaction> listTransaction;
	
	private Double balance;
	private Date createDate;
	private Boolean enabled;
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public UserVO getUserVO() {
		return userVO;
	}
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public List<Transaction> getListTransaction() {
		return listTransaction;
	}
	public void setListTransaction(List<Transaction> listTransaction) {
		this.listTransaction = listTransaction;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((listTransaction == null) ? 0 : listTransaction.hashCode());
		result = prime * result + ((userVO == null) ? 0 : userVO.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountVO other = (AccountVO) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (listTransaction == null) {
			if (other.listTransaction != null)
				return false;
		} else if (!listTransaction.equals(other.listTransaction))
			return false;
		if (userVO == null) {
			if (other.userVO != null)
				return false;
		} else if (!userVO.equals(other.userVO))
			return false;
		return true;
	}
	
	
}