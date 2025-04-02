#────────────────────────────[ project-1 ]────────────────────────────#
"""
Author:         Tim Peterson
GitHub:         Kommander28
Due Date:       2025-04-02
Description:
This project demonstrates that my Python IDE is properly set up and
that basic functions can be executed without error.
"""

# ────────────────────────────[ imports ]────────────────────────────#
from fuzzywuzzy import fuzz


# ────────────────────────────[ string_distance ]────────────────────────────#
def string_distance(list_input: list = None, target_str: str = ""):
    """
    This function compares the similarity (Levenshtein distance) of strings.
    :param list list_input: A list of strings to compare to the input string **target_str**.
    :param str target_str: The string to compare to all the values in **list_input**.
    :return: Returns a dictionary containing the similarity of each word in **list_input** to the **target_str**.
    """
    return {comp_str: fuzz.ratio(comp_str, target_str) for comp_str in ([] if list_input is None else list_input)}


# ────────────────────────────[ main ]────────────────────────────#
if __name__ == '__main__':
    my_list = ['red', 'yellow', 'blue']
    result = string_distance(my_list, 'green')
    print(result)
