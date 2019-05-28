package mswitalski.exercises.basickafkamongo.kafkaloader.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerModel {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String gender;
    private String occupation;
}
