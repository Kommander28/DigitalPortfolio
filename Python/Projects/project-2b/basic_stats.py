# ────────────────────────────[ project-2b ]──────────────────────────── #
"""
Author:         Tim Peterson
GitHub:         Kommander28
Due Date:       2025-04-09
Description:
This program contains the **Student** class which stores the name and
grade data of students. This file also has the *basic_stats** function,
which takes a list of **Student** objects and calculates the mean,
median, and mode of the **Student**'s grades.
"""
# ────────────────────────────[ imports ]──────────────────────────── #
import statistics as sts


# ────────────────────────────[ Student ]──────────────────────────── #
class Student:
    """
    This class is used to create **Student** objects that keep track of the names and grades of students.
    """

    # ──────────────[ init ]────────────── #
    def __init__(self, student_name: str = "", student_grade: float = 0.0):
        """
        :param str student_name: The name of this **Student**.
        :param float student_grade: The grade of this **Student**.
        """
        self.student_name = student_name
        self.student_grade = student_grade

    # ──────────────[ get_name ]────────────── #
    def get_name(self) -> str:
        """
        This method returns the value of **student_name** of this **Student**.
        :return: Returns the name of this **Student**.
        """
        return self.student_name

    # ──────────────[ get_grade ]────────────── #
    def get_grade(self) -> float:
        """
        This method returns the value of **student_grade** of this **Student**.
        :return: Returns the grade of this **Student**.
        """
        return self.student_grade


# ────────────────────────────[ basic_stats ]──────────────────────────── #
def basic_stats(list_of_students: list[Student] = None) -> tuple[float, float, float]:
    """
    This method calculates the mean, median, and mode for the grades of a list of **Student**s.
    :param list[Student] list_of_students: This is the list of **Student** objects to calculate the values of.
    :return: Returns a tuple in the order of mean, median, then mode.
    """
    students_grades = [student.get_grade() for student in ([] if list_of_students is None else list_of_students)]
    return sts.mean(students_grades), sts.median(students_grades), sts.mode(students_grades)
