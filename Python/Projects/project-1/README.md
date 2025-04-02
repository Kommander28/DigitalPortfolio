# project-1

For this project, you will install the **fuzzywuzzy** package in PyCharm and import the module into your project. This module uses the [Levenshtein distance](https://en.wikipedia.org/wiki/Levenshtein_distance) between strings to say how similar they are (in text, not in meaning). The relevant function for this assignment is ratio(), which you can use like this:
```
from fuzzywuzzy import fuzz
similarity = fuzz.ratio('elephant', 'element')
```
The more similar two strings are, the higher their ratio will be. If two strings are identical, their ratio will be 100.

Write a function named **string_distance** that takes two arguments: a list of strings and a target string. It should return a dictionary, where the keys are the strings from the list and the associated value for each one is its similarity to the target string.

Here's a simple example of how your code could be used:
```
my_list = ['red','yellow','blue']
result = string_distance(my_list, 'green')
```

The file must be named: **string_distance.py**
