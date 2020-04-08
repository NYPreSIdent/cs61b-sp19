import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 设计一个用于人事管理的“人员”。由于考虑到通用性，这里只抽象出所有类型人员都具有的属性：编号、姓名、出生日期、身份证号等。
 * 其中“出生日期”声明为一个“日期”类内嵌对象。用成员函数实现对人员信息的录入和显示。要求包括：构造函数和析构函数（Java不需要析构函数）
 * 复制构造函数、内联成员（Java使用final关键字）、带默认形参值的成员函数（Java不需要带默认形参值）、类的组合。
 * 针对以上描述，使用C++和Java分别进行编程实现。
 */

public class member {
    private String number;
    private String ID;
    private String name;
    private Date birth;

    private static class Date {
        private String year;
        private String month;
        private String day;

        public Date(String year, String month, String day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public String getYear() {
            return this.year;
        }

        public String getMonth() {
            return this.month;
        }

        public String getDay() {
            return this.day;
        }

        public void ShowInfo() {
            System.out.print("year: " + getYear() + " ");
            System.out.print("Month: " + getMonth() + " ");
            System.out.println("Day: " + getDay());
        }
    }

    public member() {
        this.number = null;
    }

    public member(member other) {
        this.number = other.number;
        this.ID = other.ID;
        this.name = other.name;
        this.birth = other.birth;
    }

    final public void printMember(member other) {
        if (other == null) {
            return;
        }
        if (this.number == null) {
            System.out.println("Enter the information, please.");
            inputInformation();
        }
        System.out.println("name: " + other.name);
        System.out.println("ID: " + other.ID);
        System.out.println("number: " + other.number);
        birth.ShowInfo();
    }

    final public void inputInformation() {
        System.out.println("Enter the name.");
        this.name = inputInformationHelper();

        System.out.println("Enter the number.");
        this.number = inputInformationHelper();

        System.out.println("Enter the ID");
        this.ID = inputInformationHelper();

        System.out.println("Enter the year.");
        String year = inputInformationHelper();
        System.out.println("Enter the month.");
        String month = inputInformationHelper();
        System.out.println("Enter the day.");
        String day = inputInformationHelper();

        this.birth = new Date(year, month, day);
    }

    private String inputInformationHelper() {
        Scanner car = new Scanner(System.in);

        String result = car.nextLine();

        if (result == null) {
            throw new NoSuchElementException(
                    "What you wanna do? kiss your daddy home, dude.");
        }

        return result;
    }
}
