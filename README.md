#  Desafio Thomas Greg – Cliente API

Aplicação **Spring Boot 2.7.18 + JSF (PrimeFaces 10)** para cadastro de clientes e seus logradouros, publicada como **JAR**.  
Foquei em demonstrar:

* CRUD completo via **REST** consumido pelo **front JSF**  
* Upload de logotipo em *blob*  
* Stored Procedures flexíveis usando **EntityManager**  
* Organização em **Services / DTOs** para facilitar futuras regras de negócio

---

## 🚀 Como rodar localmente

### 1. Pré‑requisitos

| Requisito | Versão |
|-----------|--------|
| **JDK**   | 8 (1.8) |
| **Maven** | 3.6+   |
| **Banco** | SQL Server |

Edite `src/main/resources/application.properties` com o seu host/porta/credenciais:

properties
`spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=clientes;encrypt=false
spring.datasource.username=sa
spring.datasource.password=123456`

1. Clonar o repositório
git clone https://github.com/DenilsonSilvaMonteiroFilho/DesafioThomasGreg.git
cd DesafioThomasGreg
2. Build
mvn clean install
3. Rodar
java -jar target/clienteapi-0.0.1-SNAPSHOT.jar

----

src
 └─ main
    ├─ java/com/.../clienteapi
    │   ├─ controllers   → REST endpoints (Auth, Cliente, Logradouro)
    │   ├─ services      → Regras de negócio; usa EntityManager p/ procedures
    │   ├─ dto           → Entrada/saída da API
    │   ├─ autorizacao   → Enum Role
    │   └─ entities      → Cliente, Logradouro
    └─ resources
        ├─ application.properties
        └─ META-INF/resources → páginas JSF + PrimeFaces

| Tema                          | Decisão                                                                                                                               |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Procedures dinâmicas**      | Usei **`EntityManager`** porque preciso montar consultas SQL/Stored‑Procedures com parâmetros opcionais.                              |
| **Serviço para `Logradouro`** | Criei `LogradouroService` separado para facilitar validações futuras e regras de domínio.                                             |
| **Filtro de logradouros**     | Planejei expor procedure de filtro por cliente, mas ficou pendente – grande parte do tempo foi gasto integrando **JSF + PrimeFaces**. |
| **Segurança**                 | Sem Spring Security neste MVP – autenticação é manual via sessão JSF.                                                                 |
