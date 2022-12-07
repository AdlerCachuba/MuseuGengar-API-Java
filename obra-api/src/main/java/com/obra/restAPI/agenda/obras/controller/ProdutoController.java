package com.obra.restAPI.agenda.obras.controller;


import com.obra.restAPI.agenda.obras.model.Produto;
import com.obra.restAPI.agenda.obras.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    @RequestMapping(value = "/produtos", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Produto> getProdutos(){
        return produtoRepository.findAll();
    }

    @RequestMapping(value = "/produtos/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Produto> getProdutosById(@PathVariable(value = "id")long id){
        Optional<Produto> contato = produtoRepository.findById(id);
        return contato.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/produtos", method =  RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Produto saveProdutos(@Valid @RequestBody Produto Produto){
        return  produtoRepository.save(Produto);
    }

    @RequestMapping(value = "/produtos/{id}", method =  RequestMethod.PUT)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Produto> updateProdutos(@PathVariable(value = "id") long id, @Valid @RequestBody Produto newProduto){
        Optional<Produto> oldContato = produtoRepository.findById(id);
        if(oldContato.isPresent()){
            Produto Produto = oldContato.get();
            Produto.setNome(newProduto.getNome());
            Produto.setAtivo(newProduto.getAtivo());
            Produto.setQuantidade(newProduto.getQuantidade());
            Produto.setFoto(newProduto.getFoto());
            produtoRepository.save(Produto);
            return new ResponseEntity<Produto>(Produto, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/produtos/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        Optional<Produto> contato = produtoRepository.findById(id);
        if(contato.isPresent()){
            produtoRepository.delete(contato.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
