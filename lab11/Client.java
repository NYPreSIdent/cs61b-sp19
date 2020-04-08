public class Client {
    public static void main(String[] args) {
        member xiePerson = new member();
        xiePerson.printMember(xiePerson);

        member b = new member(xiePerson);
        b.printMember(b);
    }
}
