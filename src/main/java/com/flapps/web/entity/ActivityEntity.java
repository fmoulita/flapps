package com.flapps.web.entity;

import com.flapps.web.data.ActivityTypeEnum;

import javax.persistence.*;

abstract class ActivityEntity {

    @Entity
    @Table(name = "asp_tms_activity")
    public class Activity extends AbstractFlappsEntity {
        @Column(name = "active")
        private boolean active;

        @Column(name = "name")
        private String name;

        @Column(name = "note")
        private String note;

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "project")
        private ActivityTypeEnum project;

        @Column(name = "system")
        private boolean system;

        @Column(name = "worked_time")
        private boolean workedTime;

        @Column(name = "rate")
        private double rate;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public ActivityTypeEnum getProject() {
            return project;
        }

        public void setProject(ActivityTypeEnum project) {
            this.project = project;
        }

        public boolean isSystem() {
            return system;
        }

        public void setSystem(boolean system) {
            this.system = system;
        }

        public boolean isWorkedTime() {
            return workedTime;
        }

        public void setWorkedTime(boolean workedTime) {
            this.workedTime = workedTime;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
