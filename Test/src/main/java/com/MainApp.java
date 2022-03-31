package com;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        Configuration configuration=new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory= configuration.buildSessionFactory();

        do {
            System.out.println("1. INSERT\n2. FETCH\n3. DELETE\n4. Fetch By Price\n5. EXIT");
            int ch=Integer.parseInt(br.readLine());
            switch(ch){
                case 1:
                    Session session= sessionFactory.openSession();
                    Transaction transaction= session.beginTransaction();
                    System.out.println("Enter Library Details ");
                    Library library=new Library();
                    library.setL_id(Integer.parseInt(br.readLine()));
                    library.setL_name(br.readLine());
                    System.out.println("Enter no. of Books ");
                    int no_of_book=Integer.parseInt(br.readLine());
                    List<Books> booksList=new ArrayList<>();
                    for(int i=0;i<no_of_book;i++){
                        System.out.println("Enter Book id, name, publisher, price");
                        Books book=new Books();
                        book.setB_id(Integer.parseInt(br.readLine()));
                        book.setName(br.readLine());
                        book.setPublisher(br.readLine());
                        book.setPrice(Float.parseFloat(br.readLine()));
                        booksList.add(book);
                        session.save(book);
                    }
                    library.setBooks(booksList);
                    int i1= (int) session.save(library);
                    transaction.commit();
                    if(i1>0)
                        System.out.println("Successfully Inserted");
                    else
                        System.out.println("Try Again");
                    break;
                case 2:
                    Session session1=sessionFactory.openSession();
                    System.out.println("Enter Publisher's Name");
                    String publisher=br.readLine();
                    Query query=session1.createQuery("from Books where publisher=:x");
                    query.setParameter("x",publisher);
                    List<Books> booksList1;
                    booksList1= query.list();
                    for(Books b:booksList1){
                        System.out.println(b);
                    }


                    break;
                case 3:
                    Session session2=sessionFactory.openSession();
                    Transaction transaction1=session2.beginTransaction();
                    System.out.println("Enter Library Id to delete ");
                    int lid= Integer.parseInt(br.readLine());
                    Query query1=session2.createQuery("delete from Library where l_id=:x");
                    query1.setParameter("x",lid);
                    int i= query1.executeUpdate();
                    transaction1.commit();
                    if(i>0)
                        System.out.println("Success");
                    else
                        System.out.println("Try Again");
                    break;
                case 4:
                    Session session3= sessionFactory.openSession();
                    Criteria c= session3.createCriteria(Books.class);
                    System.out.println("Enter Price");
                    float price= Float.parseFloat(br.readLine());
                    c.add(Restrictions.gt("price",price));
                    List<Books> booksList2;
                    booksList2=c.list();
                    for(Books b:booksList2){
                        System.out.println(b);
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong Entry");
            }
        }while(true);
    }
}
