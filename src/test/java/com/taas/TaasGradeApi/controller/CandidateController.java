package com.taas.TaasGradeApi.controller;

import com.taas.TaasGradeApi.exceptions.CandidateExceptions;
import com.taas.TaasGradeApi.model.Candidate;
import com.taas.TaasGradeApi.repository.CandidateRepository;
import com.taas.TaasGradeApi.service.AverageObject;
import com.taas.TaasGradeApi.service.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CandidateController {

    @Autowired
    private CandidateRepository Candidate_Repository;


    @GetMapping("/")
    public String hello(){
        return "Hello wolrd";
    }

    @GetMapping("/all")
    public List<Candidate> getAll(){
        return this.Candidate_Repository.findAll();
    }

    @GetMapping("/all/{id}")
    public Candidate getCandidateById(@PathVariable(value = "id") Long id){
        return this.Candidate_Repository.findById(id).orElseThrow(()-> new CandidateExceptions("Candidate not submited"));
    }

    @PostMapping("/newcandidat")
    public Candidate createcandidate(@RequestBody Candidate candidate){
        return this.Candidate_Repository.save(candidate);
    }


    @PutMapping("/candidates")
    public Candidate updatecandidate(@RequestBody Candidate candidate){
            return this.Candidate_Repository.findById(candidate
                                            .getId_candidate())
                                            .map(candidate1 ->{
                                                candidate1.setId_candidate(candidate.getId_candidate());
                                                candidate1.setFirst_name(candidate.getFirst_name());
                                                candidate1.setLast_name(candidate.getLast_name());
                                            return this.Candidate_Repository.save(candidate1);
                                            }).orElseGet(
                                                    ()->{return this.Candidate_Repository.save(candidate);} );

    }
    @DeleteMapping("/candidates")
    public ResponseEntity<Candidate> rejectCandidate(@RequestBody Candidate candidate) {
        Candidate ToReject = this.Candidate_Repository.findById(candidate.getId_candidate())
                .orElseThrow(() -> new CandidateExceptions("Candidate not found"));

        this.Candidate_Repository.delete(ToReject);
        return ResponseEntity.ok().build();
    }


}
