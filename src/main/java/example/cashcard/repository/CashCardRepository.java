package example.cashcard.repository;

import example.cashcard.model.CashCard;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {

	Page<CashCard> findByOwner(String name, PageRequest amount);

	CashCard findByIdAndOwner(Long requestedId, String name);

	boolean existsByIdAndOwner(Long id, String owner);
}
