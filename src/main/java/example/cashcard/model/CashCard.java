package example.cashcard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cashCard")
@Getter
@Setter
public class CashCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	private String owner;

	public CashCard(long l, double v) {

	}
	public CashCard() {
	}

	// Конструктор с вашими аргументами (если он уже есть)
	public CashCard(Long id, double amount) {
		this.id = id;
		this.amount = amount;
	}

	public CashCard(long id, Double amount, String name) {
		this.id = id;
		this.amount = amount;
		this.owner = name;
	}

	public long getId() {
		return id;
	}

	public Double getAmount(){
		return amount;
	}

}
