package io.github.madmike200590.jaspena.core;

import io.github.madmike200590.jaspena.annotations.Atom;

public class StudyPlan {

    @Atom(name = "can_graduate")
    private boolean canGraduate;

    @Atom(name = "elective_cects_open")
    private int     electiveCoursesCEctsOpen;

    // @Atom
    // private List<String> mandatoryCourseIdsOpen;

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

    // public List<String> getMandatoryCourseIdsOpen() {
    // return this.mandatoryCourseIdsOpen;
    // }
    //
    // public void setMandatoryCourseIdsOpen(List<String>
    // mandatoryCourseIdsOpen) {
    // this.mandatoryCourseIdsOpen = mandatoryCourseIdsOpen;
    // }

}
