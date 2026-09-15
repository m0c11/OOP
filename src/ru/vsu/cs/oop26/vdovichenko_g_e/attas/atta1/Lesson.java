package ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1;

import java.time.DayOfWeek;
import java.util.List;
import static ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1.Models.*;

public sealed abstract class Lesson permits Lecture, Practice, LabWork {
    private final String subjectName;
    private final Teacher teacher;
    private final Classroom classroom;
    private final LessonNumber lessonNumber;
    private final DayOfWeek dayOfWeek;

    public Lesson(String subjectName, Teacher teacher, Classroom classroom,
                  LessonNumber lessonNumber, DayOfWeek dayOfWeek) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.classroom = classroom;
        this.lessonNumber = lessonNumber;
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() { return subjectName; }
    public Teacher getTeacher() { return teacher; }
    public Classroom getClassroom() { return classroom; }
    public LessonNumber getLessonNumber() { return lessonNumber; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }

    public abstract boolean involvesGroup(Group group);

    public int getAcademicHours() { return 2; }
}

final class Lecture extends Lesson {
    private final List<Group> groups;

    public Lecture(String subjectName, Teacher teacher, Classroom classroom,
                   LessonNumber lessonNumber, DayOfWeek dayOfWeek, List<Group> groups) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.groups = groups;
    }

    public List<Group> getGroups() { return groups; }

    @Override
    public boolean involvesGroup(Group group) { return groups.contains(group); }

    @Override
    public String toString() {
        return String.format("[ЛЕКЦИЯ] %s | %s | %s | Группы: %s",
                getSubjectName(), getTeacher(), getClassroom(), groups);
    }
}

final class Practice extends Lesson {
    private final Group group;

    public Practice(String subjectName, Teacher teacher, Classroom classroom,
                    LessonNumber lessonNumber, DayOfWeek dayOfWeek, Group group) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.group = group;
    }

    public Group getGroup() { return group; }

    @Override
    public boolean involvesGroup(Group group) { return this.group.equals(group); }

    @Override
    public String toString() {
        return String.format("[ПРАКТИКА] %s | %s | %s | %s",
                getSubjectName(), getTeacher(), getClassroom(), group);
    }
}

final class LabWork extends Lesson {
    private final Subgroup subgroup;

    public LabWork(String subjectName, Teacher teacher, Classroom classroom,
                   LessonNumber lessonNumber, DayOfWeek dayOfWeek, Subgroup subgroup) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.subgroup = subgroup;
    }

    public Subgroup getSubgroup() { return subgroup; }

    @Override
    public boolean involvesGroup(Group group) { return subgroup.getGroup().equals(group); }

    @Override
    public String toString() {
        return String.format("[ЛАБОРАТОРНАЯ] %s | %s | %s | %s",
                getSubjectName(), getTeacher(), getClassroom(), subgroup);
    }
}
