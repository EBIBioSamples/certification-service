package uk.ac.ebi.biosamples.certpipeline.service;

import org.everit.json.schema.ValidationException;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biosamples.certpipeline.model.Checklist;
import uk.ac.ebi.biosamples.certpipeline.model.ChecklistMatches;
import uk.ac.ebi.biosamples.certpipeline.model.Sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Interrogator {

    private ConfigLoader configLoader;
    private Validator validator;

    public Interrogator(ConfigLoader configLoader, Validator validator) {
        this.validator = validator;
        this.configLoader = configLoader;
    }

    public ChecklistMatches interrogate(Sample sample) {
        List<Checklist> matches = new ArrayList<>();
        for (Checklist checklist : configLoader.config.getChecklists()) {
            try {
                validator.validate(checklist.getFileName(), sample.getDocument());
                matches.add(checklist);
            } catch (IOException ioe) {

            } catch(ValidationException ve){
                ve.printStackTrace();
            }
        }
        return new ChecklistMatches(sample, matches);
    }
}