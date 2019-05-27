package mswitalski.exercises.basickafkamongo.kafkaloader.domain.validator;

import mswitalski.exercises.basickafkamongo.kafkaloader.domain.CustomerModel;

import java.util.Objects;

/**
 * Validator checking whether all nullable fields are not nulls.
 */
public class CustomerModelNullValidator implements ModelValidator<CustomerModel> {

    @Override
    public boolean isValid(CustomerModel model) {
        return Objects.nonNull(model.getAddress()) &&
                Objects.nonNull(model.getGender()) &&
                Objects.nonNull(model.getOccupation());
    }
}