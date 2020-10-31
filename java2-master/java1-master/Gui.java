import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Gui{

    public DataBase db = new DataBase();
    /* note to myself: to set full screen first you have to get screen dimensions!
     */

    public Toolkit tk = Toolkit.getDefaultToolkit();
    int x = ((int)tk.getScreenSize().getWidth());
    int y= ((int)tk.getScreenSize().getHeight());

    public Gui()
    {

        if(db.xmlExists())
        {
            db.xmlRead();
        }

        JFrame frame = new JFrame ("Library| Timur Sorokin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //create panels
        JPanel leftpanel = new JPanel();
        JPanel rightpanel = new JPanel();
        JPanel buttons = new JPanel(new GridLayout(7, 0, 3, 0));

        BoxLayout boxlayout = new BoxLayout(leftpanel, BoxLayout.Y_AXIS);
        
        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton ("Update library");
        JButton showButton0 = new JButton("Show library(DOM)");
        JButton showButton1 = new JButton ("Show library(SAX)");

        JButton xStreamButton = new JButton ("XStream");
        JButton generateHtml = new JButton("Generate HTML");
        JButton exitButton = new JButton ("Exit");
        JTextArea textContent = new JTextArea();
        //button action listeners:
        generateHtml.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                db.generateHtml();
                JOptionPane.showMessageDialog(frame,"Task completed!");
            }
        });


        xStreamButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              db.xStreamOutput();
                JOptionPane.showMessageDialog(frame,"Task completed!");
            }
        });


        showButton0.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textContent.setText(db.showBooks());
            }
        });



 
       showButton0.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                           textContent.setText(db.showBooks());
                    }
                });

       showButton1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        textContent.setText("test");
                        db.saxRead();
                        textContent.setText(db.saxOutput);
                    }
                });


       
        addButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                           GuiAdd();
                    }
                });

updateButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                           db.xmlWrite();
                           
                    }
                });
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });





        JScrollPane scroll = new JScrollPane(textContent);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       buttons.add(addButton);
       buttons.add(updateButton);
       buttons.add(showButton0);
       buttons.add(showButton1);

       buttons.add(xStreamButton);
       buttons.add(generateHtml);
        buttons.add(exitButton);
       leftpanel.add(buttons);
       leftpanel.setLayout(boxlayout);



       rightpanel.setLayout(new BorderLayout());
       rightpanel.add(textContent);
       JScrollPane scrollright = new JScrollPane(textContent); //place the JTextArea in a scroll pane
        rightpanel.add(scrollright, BorderLayout.CENTER);
       JSplitPane s1 = new JSplitPane(SwingConstants.VERTICAL,leftpanel,rightpanel);

       frame.add(s1);
       frame.pack();
       frame.setSize(x,y);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
    }
   

   /*Add Book
    */ 
    public void GuiAdd()
    {
        JFrame frame = new JFrame ("Add Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //create panel
        JPanel panel = new JPanel();

        //create labels
     JLabel labelTitle = new JLabel("Title");
     JLabel labelAuthor = new JLabel("Author");
     JLabel labelIsbn = new JLabel("ISBN");
     JLabel labelYear = new JLabel("Year");
     JLabel labelPublisher = new JLabel("Publisher");
     JLabel labelPages = new JLabel("Pages");

     //create fields
     JTextField textTitle = new JTextField();
     JTextField textAuthor = new JTextField();
     JTextField textIsbn = new JTextField();
     JTextField textYear = new JTextField();
     JTextField textPublisher = new JTextField();
     JTextField textPages = new JTextField();

     //create button
     JButton addButton = new JButton("Add Book");

      addButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {

                        ArrayList<String> info = new ArrayList<String>();   
                        //create book object
                        String title = textTitle.getText();
                        String author = textAuthor.getText();
                        String isbn = textIsbn.getText();
                        String year = textYear.getText();
                        String publisher = textPublisher.getText();
                        String pages = textPages.getText();
                        info.add(title);
                        info.add(author);
                        info.add(isbn);
                        info.add(year);
                        info.add(publisher);
                        info.add(pages);
                    if(checkInput(info))
                    {
                        Book book = new Book(title,author,isbn,year,publisher,pages);
                        String output = String.format("New book has been added:\nTitle: "+book.title+"\nAuthor: "+book.author+"\nISBN: "+book.isbn+"\nYear: "+book.year+"\nPublisher: "+book.publisher+"\nPages: "+book.pages);
                        JOptionPane.showMessageDialog(frame,output);
                        System.out.println("Object was successfully created");
                        db.addBook(book);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame,"INVALID INPUT!");
                        System.out.println("Object was not created");
                    }

                    }
                });


     //layout
     BoxLayout boxlayout = new BoxLayout(panel,BoxLayout.Y_AXIS);
     
     panel.add(labelTitle);
     panel.add(textTitle);
     panel.add(labelAuthor);
     panel.add(textAuthor);
     panel.add(labelIsbn);
     panel.add(textIsbn);
     panel.add(labelYear);
     panel.add(textYear);
     panel.add(labelPublisher);
     panel.add(textPublisher);
     panel.add(labelPages);
     panel.add(textPages);
     panel.add(addButton);
     panel.setLayout(boxlayout);


     frame.add(panel);
     frame.pack();
     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
    }


    public boolean checkInput(ArrayList <String> list)
    {
        for(String s : list)
        {
            if(s.isEmpty())
            {
                return false;
            }
        }
        return true;
    }
}


