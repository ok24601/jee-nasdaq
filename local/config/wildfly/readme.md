# Data source

<datasources>
    <datasource jndi-name="java:/jdbc/nasdaq/ds" pool-name="ds" enabled="true" use-java-context="true">
        <connection-url>jdbc:mysql://localhost:3306/nasdaq</connection-url>
        <driver-class>com.mysql.jdbc.Driver</driver-class>
        <driver>mysql-driver</driver>
        <pool>
            <min-pool-size>2</min-pool-size>
            <max-pool-size>20</max-pool-size>
            <prefill>true</prefill>
        </pool>
        <security>
            <user-name>root</user-name>
            <password>pass</password>
        </security>
    </datasource>
    <drivers>
        <driver name="mysql-driver" module="mysql.jdbc"/>
    </drivers>
</datasources>
