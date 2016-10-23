Simple REST JEE7 startup project with: 
- ejb 3.2
- jax-rs / resteasy
- liquibase
- jackson 
- jpa 2 / hibernate
- Json Web Token. Some REST endpoints (like logout endpoint) will require an access token. This is not the best usage for JWT because it requires to store the JWT in the backend for future blacklisting feature. If you want you can substitute with simple cookies mechanism.
- User login, logout, signup features already implemented.
- Liquibase scripts for user tables creation (tested with MySql)
- Wildfly 9.0.1 full-ha example config file


Requires mysql connector module configuration in wildfly
