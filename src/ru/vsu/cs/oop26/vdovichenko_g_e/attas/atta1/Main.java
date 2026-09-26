package ru.vsu.cs.oop26.vdovichenko_g_e.attas.atta1;

import java.time.DayOfWeek;
import java.util.List;

public class Main {
    public static void main(String[] args ) throws ScheduleConflictException{
        Manager manager = new Manager();
        Models.Teacher teacher = new Models.Teacher("Щербаков М.В.");
        Models.Teacher teacher1 = new Models.Teacher("АВАфвыфаьфа");
        Models.Classroom room = new Models.Classroom("385");
        Models.Classroom room1 = new Models.Classroom("386");
        Models.Group group = new Models.Group("11 группа");
        Models.Group group1 = new Models.Group("12 группа");

        Lesson lecture = new Lecture("Матанализ", teacher, room,
                Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group));
        manager.addLessonSafe(lecture);
        System.out.println("Добавлена: " + lecture.getSubjectName());

        Models.Subgroup subgroup = new Models.Subgroup(group, 1);
        Lesson lab = new LabWork("АЭВМ", teacher, room,
                Models.LessonNumber.SECOND, DayOfWeek.MONDAY, subgroup);
        manager.addLessonSafe(lab);
        System.out.println("Добавлена: " + lab.getSubjectName());

        System.out.println("\n");

        Lesson lecture3 = new Lecture("Матанализ", teacher, room,
                Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1));
        manager.addLessonSafe(lecture3);
        System.out.println("Добавлена: " + lecture.getSubjectName());

        System.out.println("\n");

        Lesson lesson1 = new Lecture("sadaad", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1));
        manager.addLessonSafe(lesson1);

        System.out.println("\n--- Итоговое расписание для группы 11 ---");
        manager.printScheduleByGroup(group1);

        System.out.println("Нагрузка преподавателя: " + manager.calculateTeacherHours(teacher) + " ч.");
    }
}
