package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class CustomerRepositoryTest {

    private DataReceiver<CustomerModel> sut;
    private ResultSet mockedResultSet = Mockito.mock(ResultSet.class);

    @BeforeEach
    void initSut() throws SQLException {
        Statement mockedStatement = Mockito.mock(Statement.class);
        when(mockedStatement.executeQuery(ArgumentMatchers.anyString())).thenReturn(mockedResultSet);

        Connection mockedConnection = Mockito.mock(Connection.class);
        when(mockedConnection.createStatement()).thenReturn(mockedStatement);

        JdbcConnectionProvider provider = spy(new JdbcConnectionProvider("TestUrl", new Properties()));
        doReturn(mockedConnection).when(provider).provide();

        sut = new CustomerRepository(provider);
    }

    @Test
    void shouldReturnEmptyStreamWhenGotNoRecords() throws SQLException {
        // given
        when(mockedResultSet.next()).thenReturn(false);

        // when
        Stream<CustomerModel> resultStream = sut.getAll();

        // then
        assertThat(resultStream.count()).isEqualTo(0L);
    }

    @Test
    void shouldReturnStreamWithOneCustomerWhenGotOneRecord() throws SQLException {
        // given
        CustomerModel desiredModel = CustomerModel.builder()
                .name("TestName")
                .surname("TestSurname")
                .email("T")
                .address("TestAddress")
                .gender("TestGender")
                .occupation("TestOccupation")
                .build();
        when(mockedResultSet.next()).thenReturn(true, false);
        when(mockedResultSet.getString("name")).thenReturn(desiredModel.getName());
        when(mockedResultSet.getString("surname")).thenReturn(desiredModel.getSurname());
        when(mockedResultSet.getString("email")).thenReturn(desiredModel.getEmail());
        when(mockedResultSet.getString("address")).thenReturn(desiredModel.getAddress());
        when(mockedResultSet.getString("gender")).thenReturn(desiredModel.getGender());
        when(mockedResultSet.getString("occupation")).thenReturn(desiredModel.getOccupation());

        // when
        Set<CustomerModel> resultSet = sut.getAll().collect(Collectors.toSet());

        // then
        assertThat(resultSet.size()).isEqualTo(1);
        assertThat(resultSet.contains(desiredModel)).isTrue();
    }

    @Test
    void shouldReturnStreamWithTwoCustomersWhenGotTwoRecords() throws SQLException {
        // given
        CustomerModel firstDesiredModel = CustomerModel.builder()
                .name("TestName1")
                .surname("TestSurname1")
                .email("T")
                .address("TestAddress1")
                .gender("TestGender1")
                .occupation("TestOccupation1")
                .build();
        CustomerModel otherDesiredModel = CustomerModel.builder()
                .name("TestName2")
                .surname("TestSurname2")
                .email("T")
                .address("TestAddress2")
                .gender("TestGender2")
                .occupation("TestOccupation2")
                .build();
        when(mockedResultSet.next()).thenReturn(true, true, false);
        when(mockedResultSet.getString("name"))
                .thenReturn(firstDesiredModel.getName(), otherDesiredModel.getName());
        when(mockedResultSet.getString("surname"))
                .thenReturn(firstDesiredModel.getSurname(), otherDesiredModel.getSurname());
        when(mockedResultSet.getString("email"))
                .thenReturn(firstDesiredModel.getEmail(), otherDesiredModel.getEmail());
        when(mockedResultSet.getString("address"))
                .thenReturn(firstDesiredModel.getAddress(), otherDesiredModel.getAddress());
        when(mockedResultSet.getString("gender"))
                .thenReturn(firstDesiredModel.getGender(), otherDesiredModel.getGender());
        when(mockedResultSet.getString("occupation"))
                .thenReturn(firstDesiredModel.getOccupation(), otherDesiredModel.getOccupation());

        // when
        Set<CustomerModel> resultSet = sut.getAll().collect(Collectors.toSet());

        // then
        assertThat(resultSet.size()).isEqualTo(2);
        assertThat(resultSet.contains(firstDesiredModel)).isTrue();
        assertThat(resultSet.contains(otherDesiredModel)).isTrue();
    }


    @Test
    void shouldThrowExceptionWhenDatabaseAccessErrorOccurred() throws SQLException {
        // given
        SQLException causeException = new SQLException("TestReason");
        when(mockedResultSet.next()).thenThrow(causeException);

        // then
        assertThatExceptionOfType(ReceiverException.class)
                .isThrownBy(() -> sut.getAll())
                .withCause(causeException);
    }

}