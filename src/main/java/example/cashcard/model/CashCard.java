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

	public CashCard(long l, double v) {

	}
	public CashCard() {
	}

	// Конструктор с вашими аргументами (если он уже есть)
	public CashCard(Long id, double amount) {
		this.id = id;
		this.amount = amount;
	}
	public long getId() {
		return id;
	}

	public Double getAmount(){
		return amount;
	}

}
