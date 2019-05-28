package mswitalski.exercises.basickafkamongo.common.domain.validator;

/**
 * Interface defining behavior for model validators.
 *
 * @param <T> model class
 */
public interface ModelValidator<T> {

    boolean isValid(T model);
}
