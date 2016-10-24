Simple REST JEE7 startup project with: 
- Maven pom.xml file 
- ejb 3.2
- jax-rs / resteasy 3.0.18.Final
- liquibase 3.4.1
- jackson
- infinispan 8.0.1.Final
- jpa 2 / hibernate 5.0.2.Final
- Json Web Token. Some REST endpoints (like logout endpoint) will require an access token. This is not the best usage for JWT because it requires to store the JWT in the backend for future blacklisting feature. If you want you can substitute with simple cookies mechanism. JWT built using jose4j 0.4.4
- User login, logout, signup features already implemented.
- Liquibase scripts for user tables creation (tested with MySql)
- Wildfly 9.0.1 full-ha example config file


Requires mysql connector module configuration in wildfly
