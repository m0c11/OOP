package ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1;

import java.util.ArrayList;
import java.util.List;

/**
 * Пользовательское исключение для обработки конфликтов в расписании.
 */
class ScheduleConflictException extends Exception {
    public ScheduleConflictException(String message) {
        super(message);
    }
}

/**
 * Главный менеджер для управления расписанием.
 * Сохраняет список пар, проверяет накладки по аудиториям, преподавателям и группам.
 *
 * @author Вдовиченко Г. Е.
 */
public class Manager {
    private final List<Lesson> lessons = new ArrayList<>();

    /**
     * Добавляет занятие в список.
     * Проверяет, не заняты ли на этот день и пару аудитория, преподаватель или студенты.
     *
     * @param newLesson добавляемая пара
     * @throws ScheduleConflictException если найден конфликт со старыми занятиями
     */
    public void addLesson(Lesson newLesson) throws ScheduleConflictException {
        for (Lesson existing : lessons) {
            if (existing.getDayOfWeek() == newLesson.getDayOfWeek() &&
                    existing.getLessonNumber() == newLesson.getLessonNumber()) {

                if (existing.getClassroom().equals(newLesson.getClassroom())) {
                    throw new ScheduleConflictException(String.format(
                            "Аудитория %s уже занята! Накладка занятий '%s' и '%s' (%s, %s)",
                            newLesson.getClassroom().getNumber(),
                            existing.getSubjectName(), newLesson.getSubjectName(),
                            newLesson.getDayOfWeek(), newLesson.getLessonNumber()
                    ));
                }

                if (existing.getTeacher().equals(newLesson.getTeacher())) {
                    throw new ScheduleConflictException(String.format(
                            "Преподаватель %s уже ведет другое занятие! (%s, %s)",
                            newLesson.getTeacher().getFullName(),
                            newLesson.getDayOfWeek(), newLesson.getLessonNumber()
                    ));
                }

                if (hasGroupIntersection(existing, newLesson)) {
                    throw new ScheduleConflictException(String.format(
                            "Накладка у студентов! Группа уже занята (%s, %s)",
                            newLesson.getDayOfWeek(), newLesson.getLessonNumber()
                    ));
                }
            }
        }
        lessons.add(newLesson);
    }

    /**
     * Подсчитывает суммарное число академических часов преподавателя.
     */
    public int calculateTeacherHours(Models.Teacher teacher) {
        int totalHours = 0;
        for (Lesson lesson : lessons) {
            if (lesson.getTeacher().equals(teacher)) {
                totalHours += lesson.getAcademicHours();
            }
        }
        return totalHours;
    }

    /**
     * Выводит расписание для выбранной группы.
     */
    public void printScheduleByGroup(Models.Group group) {
        System.out.println("--- Расписание для группы: " + group.getName() + " ---");
        for (Lesson lesson : lessons) {
            if (lesson.involvesGroup(group)) {
                System.out.println(lesson.getDayOfWeek() + " | " + lesson.getLessonNumber() + " | " + lesson);
            }
        }
        System.out.println();
    }

    /**
     * Выводит расписание конкретного преподавателя.
     */
    public void printScheduleByTeacher(Models.Teacher teacher) {
        System.out.println("--- Расписание преподавателя: " + teacher.getFullName() + " ---");
        for (Lesson lesson : lessons) {
            if (lesson.getTeacher().equals(teacher)) {
                System.out.println(lesson.getDayOfWeek() + " | " + lesson.getLessonNumber() + " | " + lesson);
            }
        }
        System.out.println();
    }

    /**
     * Выводит расписание занятий в указанной аудитории.
     */
    public void printScheduleByClassroom(Models.Classroom classroom) {
        System.out.println("--- Расписание аудитории: " + classroom.getNumber() + " ---");
        for (Lesson lesson : lessons) {
            if (lesson.getClassroom().equals(classroom)) {
                System.out.println(lesson.getDayOfWeek() + " | " + lesson.getLessonNumber() + " | " + lesson);
            }
        }
        System.out.println();
    }

    /**
     * Проверяет, пересекаются ли группы у двух разных пар.
     */
    private boolean hasGroupIntersection(Lesson l1, Lesson l2) {
        List<Models.Group> groups1 = getGroupsFromLesson(l1);
        List<Models.Group> groups2 = getGroupsFromLesson(l2);

        for (Models.Group g1 : groups1) {
            if (groups2.contains(g1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Достает список всех групп, задействованных в занятии.
     */
    private List<Models.Group> getGroupsFromLesson(Lesson lesson) {
        if (lesson instanceof Lecture lecture) {
            return lecture.getGroups();
        } else if (lesson instanceof Practice practice) {
            return List.of(practice.getGroup());
        } else if (lesson instanceof LabWork labWork) {
            return List.of(labWork.getSubgroup().getGroup());
        }
        return List.of();
    }
}