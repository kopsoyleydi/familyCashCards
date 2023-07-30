package example.cashcard.controller;


import example.cashcard.model.CashCard;
import example.cashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

	@Autowired
	private CashCardRepository cashCardRepository;

	@GetMapping("/{requestedId}")
	public ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
		CashCard cashCard = findCashCard(requestedId, principal);
		if (cashCard != null) {
			return ResponseEntity.ok(cashCard);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@PostMapping
	private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
		CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
		URI locationOfNewCashCard = ucb
				.path("cashcards/{id}")
				.buildAndExpand(savedCashCard.getId())
				.toUri();
		return ResponseEntity.created(locationOfNewCashCard).build();
	}

	@GetMapping
	public ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
		Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));
		return ResponseEntity.ok(page.getContent());
	}

	@PutMapping("/{requestedId}")
	private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
		CashCard cashCard = findCashCard(requestedId, principal);
		if (cashCard != null) {
			CashCard updatedCashCard = new CashCard(cashCard.getId(), cashCardUpdate.getAmount(), principal.getName());
			cashCardRepository.save(updatedCashCard);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	private CashCard findCashCard(Long requestedId, Principal principal) {
		return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deleteCashCard(
			@PathVariable Long id,
			Principal principal // Add Principal to the parameter list
	) {
		// Add the following 3 lines:
		if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
			cashCardRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}