import groovy.util.GroovyTestCase;

class Book extends GroovyTestCase{
    //----
    def title;
    def author;
    def number;

    def String toString() {
        return "Book:${number}:${title} :${author}";
    }
}
    def a = new GroovyTestCase()
    println a;

def b1 = new Book(title:'z',author:'zhao',number:'11');
    println b1;
