# Como criar uma API Java

Nesse passo a passo vou explicar como criar uma API no java, fazendo um passo a passo e explicando.

## Passo 1 - Criação do projeto

Crie o projeto no [site](https://start.spring.io/).

Adicione as dependências que achar necessário, e também: Spring Web, Spring Data JPA, MySQL Driver, Lombok.

## Passo 2 - Criar a estrutura de pastas e classes

src/main/java/nomeDaSuaAPI/ e então criar as pastas controller, model, repository.

Criar os arquivos na raiz (nomeDaSuaAPI): DataConfiguration, RestApiApplication.

Criar respectivamente o controller, model, e o repository. O Repository é uma interface, as demais são classes java.

## Passo 3 - Preencher Model
    @Entity
Na classe do Model, essa notação fará com que o JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
O Hibernate utilizará como estratégia a geração AUTO_INCREMENT.
Já, se o banco de dados for o Postgres, o Hibernate gerará uma coluna do tipo SERIAL.

```java
import javax.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true)
    private Integer quantidade;
}
```
Gere os Getters e Setters,HashCode,ToString,e Equals.
## Passo 4 - Controller

    @RestController

Essa anotação combina o comportamento do @Controller e do @ResponseBody.
A anotação é responsável por retornar o objeto, e os dados do objeto são gravados diretamente na resposta HTTP como JSON ou XML.
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
    public interface NomeDoSeuModelRepositoy extends JpaRepository<Model, Long>
Definir esse extends serve para obtemos vários métodos CRUD genéricos em nosso tipo que permitem salvar o Model, excluí-los e assim por diante. Em segundo lugar, isso permitirá que a infraestrutura do repositório Spring Data JPA verifique o caminho de classe dessa interface e crie um bean Spring para ela.
```java
import org.springframework.data.jpa.repository.JpaRepository;
import produtoApi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
```

## Passo 6 - DataConfiguration

    @Configuration

A anotação @Configuration  indica que a classe possui métodos de definição @Bean, e nos permite usar anotações para injeção de dependência.

    @Bean
É uma anotação em nível de método,  retorna um objeto que spring deve registrar como um bean no contexto da aplicação.

Um bean é um objeto que é instanciado, montado e gerenciado por um contêiner Spring.

Quando um contêiner Spring IoC constrói objetos, todos os objetos são chamados de Spring beans, pois são gerenciados pelo contêiner IoC.

Com base nisso, será necessário criar manualmente um banco de dados chamado de obra-api.
Após a criação do banco, as tabelas do banco serão mapeadas conforme as anotações nos models e criadas na execução do projeto.
```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/obra-api?useTimezone=true&serverTimezone=America/Sao_Paulo"); //o obra-api é o nome do banco de dados que precisa ser criado
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

Uma única @SpringBootApplicationanotação pode ser usada para habilitar esses três recursos, ou seja:

@EnableAutoConfiguration: Ativa o mecanismo de configuração automática do Spring Boot.

@ComponentScan: Habilite @Component varredura no pacote onde o aplicativo está localizado.

@Configuration: Permite registrar beans extras no contexto ou importar classes de configuração adicionais.

A @SpringBootApplicationanotação equivale a usar @EnableAutoConfiguration, @ComponentScancom e @Configuration.

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
Adicionar os endpoints GetAll e Get por ID na sua classe Controller.

     @RequestMapping

Responsável por direcionar o caminho do endpoint. Sempre respeitando as regras da classe como url pai, e depois os métodos sendo as url's que vão na frente.
Nós também podemos definir qual método será utilizado (GET/POST/PUT/DELETE).
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

# JWT Token

Após rodar o projeto, é necessário rodar o sql abaixo para criar os cargos para os usuários:

    INSERT INTO roles(name) VALUES('ROLE_USER');
    INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
    INSERT INTO roles(name) VALUES('ROLE_ADMIN');

O Package 'login' contempla as funcionalidades:

Cadastro (POST): Todo cadastro de usuário é realizado através do endpoint:

    http://localhost:8080/api/auth/signup

Json Exemplo:

    {
       "username": "adler",
       "email": "adlermateuself@gmail.com",
       "password": "adler123"
    }


Login (POST): 

    http://localhost:8080/api/auth/signin


Json Exemplo:

    {
        "username": "adler",
        "password":"adler123"
    }

Deslogar (POST):

    http://localhost:8080/api/auth/signout

Não é necessário nenhum Json para deslogar.

Após o usuário logar, ele estará autenticado conforme seu cargo (Role), para então fazer as requisições.

Nos Controllers nós podemos limitar um endpoint conforme o seu cargo:

```java
    @RestController
    @RequestMapping("/api")
    public class ProdutoController {

        @RequestMapping(value = "/produtos", method = RequestMethod.GET)
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public List<Produto> getProdutos() {
            return produtoRepository.findAll();
        }
    }
```


