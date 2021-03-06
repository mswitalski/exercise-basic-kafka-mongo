package mswitalski.exercises.basickafkamongo.common.domain.validator;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;

import java.util.Objects;

/**
 * Validator checking whether all <b>nullable</b> fields are not nulls in CustomerModel.
 */
public class CustomerModelNullValidator implements ModelValidator<CustomerModel> {

    @Override
    public boolean isValid(CustomerModel model) {
        return Objects.nonNull(model) &&
            Objects.nonNull(model.getAddress()) &&
            Objects.nonNull(model.getGender()) &&
            Objects.nonNull(model.getOccupation());
    }
}
