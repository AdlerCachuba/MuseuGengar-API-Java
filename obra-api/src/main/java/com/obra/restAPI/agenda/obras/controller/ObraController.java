package com.obra.restAPI.agenda.obras.controller;

import com.obra.restAPI.agenda.obras.model.Obra;
import com.obra.restAPI.agenda.obras.repository.ObraRepository;
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
public class ObraController {
    private final ObraRepository obraRepository;

    @Autowired
    public ObraController(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }


    @RequestMapping(value = "/obras", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Obra> getObras(){
        return obraRepository.findAll();
    }

    @RequestMapping(value = "/obras/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Obra> getObrasById(@PathVariable(value = "id")long id){
        Optional<Obra> contato = obraRepository.findById(id);
        return contato.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/obras", method =  RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Obra saveObras(@Valid @RequestBody Obra obra){
        return  obraRepository.save(obra);
    }

    @RequestMapping(value = "/obras/{id}", method =  RequestMethod.PUT)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Obra> updateObras(@PathVariable(value = "id") long id, @Valid @RequestBody Obra newObra){
        Optional<Obra> oldContato = obraRepository.findById(id);
        if(oldContato.isPresent()){
            Obra obra = oldContato.get();
            obra.setNome(newObra.getNome());
            obra.setAtivo(newObra.getAtivo());
            obra.setQuantidade(newObra.getQuantidade());
            obra.setFoto(newObra.getFoto());
            obra.setSessao(newObra.getSessao());
            obraRepository.save(obra);
            return new ResponseEntity<Obra>(obra, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/obras/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        Optional<Obra> contato = obraRepository.findById(id);
        if(contato.isPresent()){
            obraRepository.delete(contato.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
