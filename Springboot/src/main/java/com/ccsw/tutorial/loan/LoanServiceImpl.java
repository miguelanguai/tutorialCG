package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

/**
 * @author mguaitav
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {

        return this.loanRepository.findById(id).orElse(null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> find(LoanSearchDto dto) {

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", dto.getClientId()));
        LoanSpecification init_dateSpec = new LoanSpecification(new SearchCriteria("init_date", ">=", dto.getDate()));
        LoanSpecification end_dateSpec = new LoanSpecification(new SearchCriteria("end_date", "<=", dto.getDate()));

        Specification<Loan> spec = Specification.where(gameSpec).and(clientSpec).and(init_dateSpec).and(end_dateSpec);
        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws Exception {

        Loan loan = new Loan();
        List<Loan> loans = (List<Loan>) this.loanRepository.findAll();

        dateStartIsEarlierThanDateEnd(dto);
        isLoanLongerThan14Days(dto);

        for (int i = 0; i < loans.size(); i++) {
            LocalDate init_date = loans.get(i).getInitDate();
            LocalDate end_date = loans.get(i).getEndDate();

            if (dto.getGame().getId() == loans.get(i).getGame().getId()) {
                // si el id del juego es igual que el id de la Lista
                if (gameIsOnLoan(loans, dto, i, init_date, end_date)) {
                    throw new Exception("Game already on loan");
                }
            }

            if (dto.getClient().getId() == loans.get(i).getClient().getId()) {
                // si el id del cliente es igual que el id de la Lista
                if (clientHasActiveLoan(loans, dto, i, init_date, end_date)) {
                    throw new Exception("Client with an active loan");
                }
            }
        }

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * Métido para verificar que la fecha de comienzo del préstamo es menor que la
     * fecha de fin de préstamo
     * 
     * @param dto
     * @throws Exception
     */
    public void dateStartIsEarlierThanDateEnd(LoanDto dto) throws Exception {
        if (dto.getInitDate().isAfter(dto.getEndDate())) {
            throw new Exception("init date can't be after end date");
        }
    }

    /**
     * Método para verificar que el préstamo es menor que 14 días
     * 
     * @param dto
     * @throws Exception
     */
    public void isLoanLongerThan14Days(LoanDto dto) throws Exception {
        if (ChronoUnit.DAYS.between(dto.getInitDate(), dto.getEndDate()) > 14)
            throw new Exception("loan is longer than 14 days");
    }

    /**
     * Método para verificar que el juego está prestado
     * 
     * @param loans
     * @param dto
     * @param index
     * @param init_date
     * @param end_date
     * @return true: si {@link Game} tiene un {@link Loan} activo
     */
    public boolean gameIsOnLoan(List<Loan> loans, LoanDto dto, int index, LocalDate init_date, LocalDate end_date) {
        boolean isOnLoan = false;

        if (init_date.isEqual(dto.getInitDate()) || end_date.isEqual(dto.getEndDate())
        // si la fecha de inicio del indice es igual a la del parametro o la fecha de
        // fin es igual a la del parametro

                || (init_date.isBefore(dto.getEndDate()) && end_date.isAfter(dto.getInitDate()))) {
            // o la fecha de inicio del indice va antes que la fecha de fin del parametro
            // ADEMAS de que la fecha de final del indice vaya antes que la fecha de inicio
            // del parametro
            isOnLoan = true;
        }
        return isOnLoan;
    }

    /**
     * Método para verificar que el cliente tiene algún préstamo activo en este
     * momento
     * 
     * @param loans
     * @param dto
     * @param index
     * @param init_date
     * @param end_date
     * @return true: si {@link Client} tiene un {@link Loan} activo
     */
    public boolean clientHasActiveLoan(List<Loan> loans, LoanDto dto, int index, LocalDate init_date,
            LocalDate end_date) {
        boolean hasActiveLoan = false;

        if (init_date.isEqual(dto.getInitDate()) || end_date.isEqual(dto.getEndDate())
        // si la fecha de inicio del indice es igual a la del parametro o la fecha de
        // fin es igual a la del parametro

                || (init_date.isBefore(dto.getEndDate()) && end_date.isAfter(dto.getInitDate()))) {
            // o la fecha de inicio del indice va antes que la fecha de fin del parametro
            // ADEMAS de que la fecha de final del indice vaya antes que la fecha de inicio
            // del parametro
            hasActiveLoan = true;
        }
        return hasActiveLoan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {

        return (List<Loan>) this.loanRepository.findAll();
    }
}
