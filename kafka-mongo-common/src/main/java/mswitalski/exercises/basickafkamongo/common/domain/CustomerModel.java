package mswitalski.exercises.basickafkamongo.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Example model class that will be received from SQL database and transferred
 * to MongoDB through Kafka. This model was built under assumption that database
 * would not allow any other fields than 'address', 'gender' and 'occupation'
 * to hold null values.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    private String name;
    private String surname;
    private String email;
    private String address;
    private String gender;
    private String occupation;
}
