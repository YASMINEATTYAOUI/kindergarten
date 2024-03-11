package com.kindergarten.kindergarten.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/streamkeys")
class StreamKeysController {

    @Autowired
    StreamKeysRepo repository;

    @GetMapping
    public ResponseEntity<List<StreamKeys>> getAll() {
        try {
            List<StreamKeys> items = new ArrayList<StreamKeys>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{pemail}")
    public ResponseEntity<StreamKeys> getById(@PathVariable("pemail") String pemail) {
        Optional<StreamKeys> existingItemOptional = repository.findById(pemail);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{pemail}/{ide}")
    public ResponseEntity<StreamKeys> getByEmailParentAndIdEnfant(@PathVariable("pemail") String pemail,
            @PathVariable("ide") Integer ide) {
        StreamKeys sk = repository.findByEmailParentAndIdEnfant(pemail, ide);

        if (sk != null) {
            return new ResponseEntity<>(sk, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{pemail}/{ide}/{keyp}/{keye}")
    public ResponseEntity<StreamKeys> saveKeyStreaming(@PathVariable("pemail") String pemail,
            @PathVariable("ide") Integer ide, @PathVariable("keyp") String keyp, @PathVariable("keye") String keye) {

        StreamKeys sk = repository.findById(pemail).get();
        if (sk != null) {
            if (ide >= 0) {
                sk.setIdEnfant(ide);
            }
            if (!keyp.equals("__"))
                sk.setKeyParent(keyp);
            if (!keye.equals("__"))
                sk.setKeyEnfant(keye);
            repository.save(sk);
            return new ResponseEntity<>(sk, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<StreamKeys> create(@RequestBody StreamKeys item) {
        try {
            StreamKeys savedItem = repository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{pemail}")
    public ResponseEntity<StreamKeys> update(@PathVariable("pemail") String pemail, @RequestBody StreamKeys item) {
        Optional<StreamKeys> existingItemOptional = repository.findById(pemail);
        if (existingItemOptional.isPresent()) {
            StreamKeys existingItem = existingItemOptional.get();
            repository.save(existingItem);
            return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{pemail}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("pemail") String pemail) {
        try {
            repository.deleteById(pemail);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
