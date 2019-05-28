package mswitalski.exercises.basickafkamongo.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String gender;
    private String occupation;
}
