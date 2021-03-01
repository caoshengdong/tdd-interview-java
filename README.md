# 【招聘】Readme
# 欢迎来到编程笔试

在这个笔试题目里，你将着手编写一个实现一些简易功能的Excel。

本笔试题目为测试驱动，即：全部笔试题由20个测试案例组成，其对应文件位于/test/src目录下。其中，spreadsheetTest1.java包含5个，spreadsheetTest2.java包含10个，spreadsheetTest3.java包含5个，为了通过上述这20个测试案例，你需要逐步完善位于/model/src/目录下的spreadsheet.java文件的代码。


- 请从第一个测试文件的第一个测试用例开始，逐步增添、重构excel的函数功能。测试案例的难度是从简到难。
- 如果你记不住某个内置函数库的用法，你可以上网进行查找，但是不可以和其他人进行协商。

## 用到的技术
- Java
- JUnit

## 关于该Excel对象的基本功能介绍
该Excel只有一行。列数为A到Z，AA到ZZ等。没有储值的列数默认存储值为空字符串。

- get(String column), 该函数的目的是获取在column列的值，内容为等式的话需要计算并返回其结果。举例，若在"A1"列存储的值为"=7+3"，sheet.get("A1")应返回"10"。
- getLiteral(String column), 该函数的目的是获取在column列的字符串值，内容为等式的话不需要计算，直接返回字符串。举例，若在"A1"列存储的值为"=7+3"，sheet.getLiteral("A1")应返回"=7+3"。
- put(String column, String value), 该函数的目的是在column列存储value的值。如果该列已经被占用，则替换为当前值。