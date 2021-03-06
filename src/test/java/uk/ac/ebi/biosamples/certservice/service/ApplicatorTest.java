package uk.ac.ebi.biosamples.certservice.service;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.biosamples.certservice.Application;
import uk.ac.ebi.biosamples.certservice.model.*;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, properties = {"job.autorun.enabled=false"})
public class ApplicatorTest {

    @Autowired
    private Applicator applicator;

    @Test
    public void given_valid_plan_result_apply_curations() throws Exception {
        applyCuration("json/ncbi-SAMN03894263.json", "json/ncbi-SAMN03894263-curated.json");
    }

    private void applyCuration(String source, String expectedResult) throws IOException {
        String data = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(source), "UTF8");
        String curatedData = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(expectedResult), "UTF8");
        Sample sample = new Sample("test", data);
        Sample curatedSample = new Sample("test", curatedData);
        Curation curation = new Curation("INSDC status", "public");
        Plan plan = new Plan("ncbi-0.0.1", "biosamples-0.0.1", Collections.singletonList(curation));
        PlanResult planResult = new PlanResult(sample, plan);
        planResult.addCurationResult(new CurationResult(curation.getCharacteristic(), "live", curation.getValue()));
        assertEquals(curatedSample.getDocument().trim(), applicator.apply(planResult).getDocument().trim());
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_null_planResult_throw_exception() throws IOException {
        applicator.apply(null);
    }
}
