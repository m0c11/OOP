package ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1;

import java.time.LocalTime;
import java.util.Objects;


public class Models {

    public static class Teacher {
        private final String fullName;

        public Teacher(String fullName) { this.fullName = fullName; }
        public String getFullName() { return fullName; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Teacher teacher = (Teacher) o;
            return Objects.equals(fullName, teacher.fullName);
        }

        @Override
        public int hashCode() { return Objects.hash(fullName); }

        @Override
        public String toString() { return fullName; }
    }

    public static class Classroom {
        private final String number;

        public Classroom(String number) { this.number = number; }
        public String getNumber() { return number; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Classroom classroom = (Classroom) o;
            return Objects.equals(number, classroom.number);
        }

        @Override
        public int hashCode() { return Objects.hash(number); }

        @Override
        public String toString() { return "ауд. " + number; }
    }

    public static class Group {
        private final String name;

        public Group(String name) { this.name = name; }
        public String getName() { return name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Group group = (Group) o;
            return Objects.equals(name, group.name);
        }

        @Override
        public int hashCode() { return Objects.hash(name); }

        @Override
        public String toString() { return "Группа " + name; }
    }

    public static class Subgroup {
        private final Group group;
        private final int subgroupNumber;

        public Subgroup(Group group, int subgroupNumber) {
            this.group = group;
            this.subgroupNumber = subgroupNumber;
        }

        public Group getGroup() { return group; }
        public int getSubgroupNumber() { return subgroupNumber; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Subgroup subgroup = (Subgroup) o;
            return subgroupNumber == subgroup.subgroupNumber && Objects.equals(group, subgroup.group);
        }

        @Override
        public int hashCode() { return Objects.hash(group, subgroupNumber); }

        @Override
        public String toString() { return group.getName() + " (подгруппа " + subgroupNumber + ")"; }
    }

    public enum LessonNumber {
        FIRST(1, LocalTime.of(8, 0), LocalTime.of(9, 35)),
        SECOND(2, LocalTime.of(9, 45), LocalTime.of(11, 20)),
        THIRD(3, LocalTime.of(11, 30), LocalTime.of(13, 5)),
        FOURTH(4, LocalTime.of(13, 25), LocalTime.of(15, 0)),
        FIFTH(5, LocalTime.of(15, 10), LocalTime.of(16, 45)),
        SIXTH(6, LocalTime.of(16, 55), LocalTime.of(18, 30)),
        SEVENTH(7, LocalTime.of(18, 40), LocalTime.of(20, 0)),
        EIGHTH(8, LocalTime.of(20, 10), LocalTime.of(21, 30));

        private final int number;
        private final LocalTime startTime;
        private final LocalTime endTime;

        LessonNumber(int number, LocalTime startTime, LocalTime endTime) {
            this.number = number;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getNumber() { return number; }
        public LocalTime getStartTime() { return startTime; }
        public LocalTime getEndTime() { return endTime; }

        @Override
        public String toString() { return number + "-я пара (" + startTime + " - " + endTime + ")"; }
    }
}
