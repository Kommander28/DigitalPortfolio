# ────────────────────────────[ project-2a ]──────────────────────────── #
"""
Author:         Tim Peterson
GitHub:         Kommander28
Due Date:       2025-04-09
Description:
This class file contains the **Taxicab** class - a class that makes
**Taxicab** objects with different attributes such as current x and y
coordinates (**x_coord**, **y_coord**) of the **Taxicab** and the
odometer reading (**self.odometer**).
"""


# ────────────────────────────[ Taxicab ]──────────────────────────── #
class Taxicab:
    """
    This class is used to create **Taxicab** objects.
    :var float x_coord: The current x-coordinate of this **Taxicab**.
    :var float y_coord: The current y-coordinate of this **Taxicab**.
    :var float odometer: The odometer mileage of this **Taxicab**.
    """

    # ──────────────[ init ]────────────── #
    def __init__(self, x_initial: float = 0.0, y_initial: float = 0.0):
        """
        :param float x_initial: The initial **x_coord** value of this Taxicab object.
        :param float y_initial: The initial **y_coord** value of this Taxicab object.
        """
        self.x_coord = x_initial
        self.y_coord = y_initial
        self.odometer = 0.0

    # ──────────────[ get_x_coord ]────────────── #
    def get_x_coord(self):
        """
        This method returns the current **x_coord** value of this **Taxicab**.
        :return: Returns the **x_coord** of this **Taxicab**.
        """
        return self.x_coord

    # ──────────────[ get_y_coord ]────────────── #
    def get_y_coord(self):
        """
        This method returns the current **y_coord** value of this **Taxicab**.
        :return: Returns the **y_coord** of this **Taxicab**.
        """
        return self.y_coord

    # ──────────────[ get_odometer ]────────────── #
    def get_odometer(self):
        """
        This method returns the current **odometer** value of this **Taxicab**.
        :return: Returns the **odometer** of this **Taxicab**.
        """
        return self.odometer

    # ──────────────[ move_x ]────────────── #
    def move_x(self, x_distance: float = 0.0):
        """
        This method moves this **Taxicab** by a certain distance **x_distance** in the x-direction (left/right).
        :param float x_distance: The amount to move this **Taxicab**'s **x_coord** by. Use negative values for "backwards" (leftwards) movement.
        """
        self.odometer += abs(x_distance)
        self.x_coord += x_distance

    # ──────────────[ move_y ]────────────── #
    def move_y(self, y_distance: float = 0.0):
        """
        This method moves this **Taxicab** by a certain distance **y_distance** in the y-direction (up/down).
        :param float y_distance: The amount to move this **Taxicab**'s **y_coord** by. Use negative values for "backwards" (downwards) movement.
        """
        self.odometer += abs(y_distance)
        self.y_coord += y_distance

    # ──────────────[ move_xy ]────────────── #
    def move_xy(self, x_distance: float = 0.0, y_distance: float = 0.0):
        """
        This method moves this **Taxicab** by a certain distance in both the x and y directions, directly.
        :param float x_distance: The amount to move this **Taxicab**'s **x_coord** by. Use negative values for "backwards" (leftwards) movement.
        :param float y_distance: The amount to move this **Taxicab**'s **y_coord** by. Use negative values for "backwards" (downwards) movement.
        """
        self.odometer += (x_distance ** 2 + y_distance ** 2) ** (1 / 2)
        self.x_coord += x_distance
        self.y_coord += y_distance


# ────────────────────────────[ main ]──────────────────────────── #
if __name__ == '__main__':
    cab = Taxicab(5, -8)
    cab.move_x(3)
    cab.move_y(-4)
    cab.move_x(-1)
    print(cab.get_odometer())
    print(f"[{cab.get_x_coord()},{cab.get_y_coord()}]")
