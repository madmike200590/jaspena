package io.github.madmike200590.jaspena.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.github.madmike200590.jaspena.asp.AnswerSet;
import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.mapper.IAnswerSetToObjectMapper;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.Predicate;

public class AnswerSetToObjectMapperFactoryTest {

    private AnswerSetToObjectMapperFactory factory = new AnswerSetToObjectMapperFactory();

    @Test
    public void smokeTest() throws AspMappingException {
        IAnswerSetToObjectMapper<StudyPlan> studyPlanMapper = this.factory.createMapperFor(StudyPlan.class);

        Map<Predicate, Set<Atom>> atoms = new HashMap<>();
        atoms.put(new Predicate("can_graduate", 0), Collections.emptySet());

        Set<Atom> electiveOpen = new HashSet<>();
        Atom electiveCectsAtom = Atom.newGroundInstance("elective_cects_open", "1234");
        electiveOpen.add(electiveCectsAtom);
        atoms.put(electiveCectsAtom.getPredicate(), electiveOpen);

        Predicate mandatoryOpenPred = new Predicate("mandatory_cid_open", 1);
        Set<Atom> mandatoryOpen = new HashSet<>();
        mandatoryOpen.add(Atom.newGroundInstance(mandatoryOpenPred, "cid1"));
        mandatoryOpen.add(Atom.newGroundInstance(mandatoryOpenPred, "cid2"));
        mandatoryOpen.add(Atom.newGroundInstance(mandatoryOpenPred, "cid3"));
        atoms.put(mandatoryOpenPred, mandatoryOpen);
        AnswerSet answerSet = new AnswerSet(atoms);

        StudyPlan plan = studyPlanMapper.mapFromAnswerSet(answerSet);
        Assert.assertEquals(true, plan.canGraduate());
        Assert.assertEquals(1234, plan.getElectiveCoursesCEctsOpen());
        Assert.assertEquals(3, plan.getMandatoryCourseIdsOpen().size());
    }

}
