# project-2a

**Remember that, as stated in the Code Style Guidelines, all classes in all assignments (and each of their methods) must have a docstring.**

Write a class named Taxicab that has three **private** data members: one that holds the current x-coordinate, one that holds the current y-coordinate, and one that holds the current odometer reading. An odometer simply measures the distance a car or bicycle has traveled by keeping track of how many times its wheels have turned. So if you travel one unit left, and then one unit right, you'll be back where you started, but your odometer will tell you that you've traveled 2 units. That is, it tells you how far the Taxicab has moved **not** how far it ends up from where it started.

**All three** data members should be updated as needed so that they are always current. 

The class should have an `init` method that takes two parameters and uses them to initialize the coordinates, and it should also initialize the odometer to zero.  

The class should have get methods for each data member: `get_x_coord`, `get_y_coord`, and `get_odometer`.  The class does not need any set methods.  

It should have a method called `move_x` that takes a parameter that tells how far the Taxicab should shift left or right. It should have a method called `move_y` that takes a parameter that tells how far the Taxicab should shift up or down.  

[Would it be okay to use 'x' and 'y' for the names of data members in this class? The Code Style Requirements say "Single letter names are not descriptive and should not be used except in list comprehensions or in the context of a well-known equation, such as the quadratic formula". While 'x' and 'y' are commonly used in coordinate systems, that's not quite the same thing as being part of a well-known equation. Furthermore, 'x' and 'y' are used for a lot of different things, so it would be beneficial to put in the (very minimal) effort to use less ambiguous names. It's easy for programmers to be lazy about not wanting to type a few more characters, but clarity should take priority every time - that will save a lot more time in the long run. In the case of this project, there are hints for what to name the data members. It's convention that the names of get and set methods match the names of the data members they access (with "get_" or "set_" added in front). The data member returned by get_x_coord() should be named x_coord, etc.]

For example, the Taxicab class might be used as follows:
```
cab = Taxicab(5, -8)       # creates a Taxicab object at coordinates (5, -8)
cab.move_x(3)              # moves cab 3 units "right"
cab.move_y(-4)             # moves cab 4 units "down"
cab.move_x(-1)             # moves cab 1 unit "left"
print(cab.get_odometer())  # prints the current odometer reading
# At this point the cab has traveled 3 + 4 + 1 = 8 units and is now at coordinates (7, -12)
```

The file must be named: Taxicab.py
