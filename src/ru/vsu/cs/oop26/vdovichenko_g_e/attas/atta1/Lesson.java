package ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Базовый абстрактный класс для всех типов учебных занятий.
 * Использует sealed-иерархию для ограничения наследования.
 *
 * @author Вдовиченко Г. Е.
 */
public sealed abstract class Lesson permits Lecture, Practice, LabWork {
    private final String subjectName;
    private final Models.Teacher teacher;
    private final Models.Classroom classroom;
    private final Models.LessonNumber lessonNumber;
    private final DayOfWeek dayOfWeek;

    /**
     * Инициализация общих полей для любого занятия.
     */
    public Lesson(String subjectName, Models.Teacher teacher, Models.Classroom classroom,
                  Models.LessonNumber lessonNumber, DayOfWeek dayOfWeek) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.classroom = classroom;
        this.lessonNumber = lessonNumber;
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() { return subjectName; }
    public Models.Teacher getTeacher() { return teacher; }
    public Models.Classroom getClassroom() { return classroom; }
    public Models.LessonNumber getLessonNumber() { return lessonNumber; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }

    /**
     * Проверяет, касается ли данное занятие указанной группы.
     *
     * @param group проверяемая группа
     * @return true, если группа посещает это занятие
     */
    public abstract boolean involvesGroup(Models.Group group);

    /**
     * Возвращает длительность занятия в академических часах.
     */
    public int getAcademicHours() { return 2; }
}

/**
 * Класс лекции. Лекцию могут посещать сразу несколько групп.
 */
final class Lecture extends Lesson {
    private final List<Models.Group> groups;

    public Lecture(String subjectName, Models.Teacher teacher, Models.Classroom classroom,
                   Models.LessonNumber lessonNumber, DayOfWeek dayOfWeek, List<Models.Group> groups) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.groups = groups;
    }

    public List<Models.Group> getGroups() { return groups; }

    @Override
    public boolean involvesGroup(Models.Group group) { return groups.contains(group); }

    @Override
    public String toString() {
        return String.format("[ЛЕКЦИЯ] %s | %s | %s | Группы: %s",
                getSubjectName(), getTeacher(), getClassroom(), groups);
    }
}

/**
 * Класс практического занятия. Проводится для одной конкретной группы.
 */
final class Practice extends Lesson {
    private final Models.Group group;

    public Practice(String subjectName, Models.Teacher teacher, Models.Classroom classroom,
                    Models.LessonNumber lessonNumber, DayOfWeek dayOfWeek, Models.Group group) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.group = group;
    }

    public Models.Group getGroup() { return group; }

    @Override
    public boolean involvesGroup(Models.Group group) { return this.group.equals(group); }

    @Override
    public String toString() {
        return String.format("[ПРАКТИКА] %s | %s | %s | %s",
                getSubjectName(), getTeacher(), getClassroom(), group);
    }
}

/**
 * Класс лабораторной работы. Проводится для одной подгруппы.
 */
final class LabWork extends Lesson {
    private final Models.Subgroup subgroup;

    public LabWork(String subjectName, Models.Teacher teacher, Models.Classroom classroom,
                   Models.LessonNumber lessonNumber, DayOfWeek dayOfWeek, Models.Subgroup subgroup) {
        super(subjectName, teacher, classroom, lessonNumber, dayOfWeek);
        this.subgroup = subgroup;
    }

    public Models.Subgroup getSubgroup() { return subgroup; }

    @Override
    public boolean involvesGroup(Models.Group group) { return subgroup.getGroup().equals(group); }

    @Override
    public String toString() {
        return String.format("[ЛАБОРАТОРНАЯ] %s | %s | %s | %s",
                getSubjectName(), getTeacher(), getClassroom(), subgroup);
    }
}