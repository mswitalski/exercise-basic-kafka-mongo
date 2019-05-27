package mswitalski.exercises.basickafkamongo.kafkaloader.domain.validator;

/**
 * Interface defining behavior for model validators.
 *
 * @param <T> model class
 */
public interface ModelValidator<T> {

    boolean isValid(T model);
}
