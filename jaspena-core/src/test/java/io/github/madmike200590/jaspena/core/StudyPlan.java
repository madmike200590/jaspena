package io.github.madmike200590.jaspena.core;

import java.util.List;

import io.github.madmike200590.jaspena.annotations.Atom;

public class StudyPlan {

    @Atom
    private boolean      canGraduate;

    @Atom
    private int          electiveCoursesCEctsOpen;

    @Atom
    private List<String> mandatoryCourseIdsOpen;

    public boolean canGraduate() {
        return this.canGraduate;
    }

    public void setCanGraduate(boolean canGraduate) {
        this.canGraduate = canGraduate;
    }

    public int getElectiveCoursesCEctsOpen() {
        return this.electiveCoursesCEctsOpen;
    }

    public void setElectiveCoursesCEctsOpen(int electiveCoursesCEctsOpen) {
        this.electiveCoursesCEctsOpen = electiveCoursesCEctsOpen;
    }

    public boolean isCanGraduate() {
        return this.canGraduate;
    }

    public List<String> getMandatoryCourseIdsOpen() {
        return this.mandatoryCourseIdsOpen;
    }

    public void setMandatoryCourseIdsOpen(List<String> mandatoryCourseIdsOpen) {
        this.mandatoryCourseIdsOpen = mandatoryCourseIdsOpen;
    }

}
