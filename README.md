# MuseuGengar-API-Java

# Como criar uma API Java

Foobar is a Python library for dealing with word pluralization.

## Passo 1 - Criação do projeto

Crie o projeto no [site](https://start.spring.io/).

Adicione as dependências que achar necessário, e também: Spring Web, Spring Data JPA, MySQL Driver, Lombok.

## Passo 2 - Criar a estrutura de pastas e classes

src/main/java/nomeDaSuaAPI/ e então criar as pastas controller, model, repository.

Criar os arquivos na raiz (nomeDaSuaAPI): DataConfiguration, RestApiApplication

Criar respectivamente o controller, model, e o repository. O Repository é uma interface, as demais são classes java.

## Passo 3 - Preencher Model

@Entity na classe do Model


```java
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 //O Hibernate utilizará como estratégia a geração AUTO_INCREMENT. 
 //Já, se o banco de dados for o Postgres, o Hibernate gerará uma coluna do tipo SERIAL.
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true)
    private Integer quantidade;

```
Gera os Getters,Setters,HashCode,ToString,e Equals.

## Passo 4 - Controller

Notação na classe: @RestController

```java
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


}

```

## Passo 5 - Repository

No seu repository:
```java
import org.springframework.data.jpa.repository.JpaRepository;
import produtoApi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
```

Com isso, você criou a estrutura inicial.

## Passo 6 - DataConfiguration


Vai precisar colocar a anotação na classe: @Configuration

```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/obra-api?useTimezone=true&serverTimezone=America/Sao_Paulo");
        dataSource.setUsername("xxxx"); //user banco
        dataSource.setPassword("xxxx"); //senha banco
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        adapter.setPrepareConnection(true);
        return adapter;
    }
}
```

## Passo 7 - RestApiApplication
Será a classe a qual você vai executar.
```java
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

}
```


## Passo 8 - Controller
Adicionar os metodos Get/Getall
```java
    @RequestMapping(value = "/produto", method = RequestMethod.GET)
    public List<Produto> getProduto(){
        return produtoRepository.findAll();
    }

    @RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> getProdutoById(@PathVariable(value = "id")long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
```

## Passo 9





