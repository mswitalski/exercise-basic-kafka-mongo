package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.util.Properties;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JdbcConnectorTest {

    private final String databaseUrl = "fakeUrl";

    private DriverManagerWrapper mockedDriverManager;
    private Connection mockedConnection;
    private JdbcConnector sut;

    @BeforeEach
    void before() {
        mockedDriverManager = mock(DriverManagerWrapper.class);
        mockedConnection = mock(Connection.class);
        sut = prepareReceiverWithoutProperties();
    }

//    @Test
//    void shouldNotThrowExceptionWhenConnectedSuccessfullyWithoutProperties() throws SQLException {
//        // given
//        when(mockedDriverManager.getConnection(databaseUrl)).thenReturn(mockedConnection);
//
//        // when
//        sut.connect();
//
//        // then
//        verify(mockedConnection, times(1)).setAutoCommit(true);
//    }
//
//    @Test
//    void shouldNotThrowExceptionWhenConnectedSuccessfullyWithProperties() throws SQLException {
//        // given
//        Properties properties = new Properties();
//        properties.put("some-property", "some-value");
//        JdbcConnector sut = new JdbcConnector(databaseUrl, properties, mockedDriverManager);
//        when(mockedDriverManager.getConnection(databaseUrl, properties)).thenReturn(mockedConnection);
//
//        // when
//        sut.connect();
//
//        // then
//        verify(mockedConnection, times(1)).setAutoCommit(true);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenConnectionFailed() throws SQLException {
//        // given
//        when(mockedDriverManager.getConnection(databaseUrl)).thenThrow(new SQLException());
//
//        // then
//        assertThatThrownBy(sut::connect).isInstanceOf(ReceiverException.class);
//    }
//
//    @Test
//    void shouldNotThrowExceptionWhenDisconnectedSuccessfully() throws SQLException {
//        // given
//        when(mockedDriverManager.getConnection(databaseUrl)).thenReturn(mockedConnection);
//        when(mockedConnection.getAutoCommit()).thenReturn(false);
//
//        // when
//        sut.connect();
//        sut.disconnect();
//
//        // then
//        verify(mockedConnection, times(1)).close();
//    }
//
//    @Test
//    void shouldThrowExceptionWhenDisconnectingFailed() throws SQLException {
//        // given
//        when(mockedDriverManager.getConnection(databaseUrl)).thenReturn(mockedConnection);
//        doThrow(new SQLException()).when(mockedConnection).close();
//
//        // then
//        sut.connect();
//        assertThatThrownBy(sut::disconnect).isInstanceOf(ReceiverException.class);
//    }

    private JdbcConnector prepareReceiverWithoutProperties() {
        Properties emptyProperties = new Properties();
        return new JdbcConnector(databaseUrl, emptyProperties, mockedDriverManager);
    }
}