### 실습하면서 알게된 점

로그
```log
INFO: HHH10001005: using driver [org.h2.Driver] at URL [jdbc:h2:tcp://localhost/~/test]
4월 07, 2021 11:39:18 오전 org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
INFO: HHH10001001: Connection properties: {user=sa, password=****}
4월 07, 2021 11:39:18 오전 org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
INFO: HHH10001003: Autocommit mode: false
4월 07, 2021 11:39:18 오전 org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PooledConnections <init>
INFO: HHH000115: Hibernate connection pool size: 20 (min=1)
4월 07, 2021 11:39:18 오전 org.hibernate.dialect.Dialect <init>
INFO: HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
Hibernate: 
    
    drop table Member if exists
Hibernate: 
    
    create table Member (
       id bigint generated by default as identity,
        name varchar(255) not null,
        primary key (id)
    )
4월 07, 2021 11:39:18 오전 org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@3591009c] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
4월 07, 2021 11:39:18 오전 org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@6928f576] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
4월 07, 2021 11:39:18 오전 org.hibernate.tool.schema.internal.SchemaCreatorImpl applyImportSources
INFO: HHH000476: Executing import script 'org.hibernate.tool.schema.internal.exec.ScriptSourceInputNonExistentImpl@9cd25ff'
=== BEFORE ===
4월 07, 2021 11:39:18 오전 org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl stop
INFO: HHH10001008: Cleaning up connection pool [jdbc:h2:tcp://localhost/~/test]
```
엔티티
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```
@Id 애노테이션에 (strategy = GenerationType.IDENTITY)를 붙이고

실제 JPA에서 member.setId에 값을 넣고있었음 그래서 테이블이 생성은 되는데 Insert가 안됨

왜 안 되지 하고 보다가 GenerationType.IDENTITY 자체가 AUTO_INCREMENT 형식인데

setId를 계속 해주니 안되는 거였다.. 자꾸 쓸데없이 h2 버전이랑 hibernate 버전 문제인지 알고 계속 삽질했음. 반성하자.

=================================================