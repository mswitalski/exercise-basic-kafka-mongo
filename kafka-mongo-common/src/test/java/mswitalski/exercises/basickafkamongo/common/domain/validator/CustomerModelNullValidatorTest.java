package mswitalski.exercises.basickafkamongo.common.domain.validator;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerModelNullValidatorTest {

    private ModelValidator<CustomerModel> sut = new CustomerModelNullValidator();

    @Test
    void shouldPassValidationWithValidModel() {
        // given
        CustomerModel validModel = CustomerModel.builder()
                .name("Name")
                .surname("Surname")
                .email("email@local.domain")
                .address("Address 10")
                .gender("M")
                .occupation("Occupation")
                .build();

        // when
        boolean result = sut.isValid(validModel);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldFailValidationWithNullAddress() {
        // given
        CustomerModel invalidModel = CustomerModel.builder()
                .name("Name")
                .surname("Surname")
                .email("email@local.domain")
                .address(null)
                .gender("M")
                .occupation("Occupation")
                .build();

        // when
        boolean result = sut.isValid(invalidModel);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldFailValidationWithNullGender() {
        // given
        CustomerModel invalidModel = CustomerModel.builder()
                .name("Name")
                .surname("Surname")
                .email("email@local.domain")
                .address("Address 10")
                .gender(null)
                .occupation("Occupation")
                .build();

        // when
        boolean result = sut.isValid(invalidModel);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldFailValidationWithNullOccupation() {
        // given
        CustomerModel invalidModel = CustomerModel.builder()
                .name("Name")
                .surname("Surname")
                .email("email@local.domain")
                .address("Address 10")
                .gender("M")
                .occupation(null)
                .build();

        // when
        boolean result = sut.isValid(invalidModel);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldNotThrowExceptionWhenModelIsNull() {
        // when
        boolean result = sut.isValid(null);

        // then
        assertThat(result).isFalse();
    }
}