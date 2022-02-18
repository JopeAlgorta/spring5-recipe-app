package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

import java.util.Set;

/**
 * @author jpalg
 * Created at 1/25/2022
 */
public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAll();

}
