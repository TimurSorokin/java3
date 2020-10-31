import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.io.*;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;


public class DataBase
{
    public ArrayList<Book> library;
    {
        library= new ArrayList<Book>();
    }
    public void addBook(Book book)
    {
        library.add(book);
    }

    public String showBooks()
    {
        String output ="";
        int counter=1;
        for(Book show: library)
        {
            output+="\n"+counter+")"+"\nTitle: "+show.title+"\nAuthor: "+show.author+"\nISBN: "+show.isbn+"\nYear: "+show.year+"\nPublisher: "+show.publisher+"\nPages: "+show.pages+"\n============\n";
            counter++;
        }
        return output;
    }
 public boolean xmlExists()
 {
     String getPath = System.getProperty("user.home");
     try{
         File save = new File("library.xml");
         if(save.createNewFile())
         {
             System.out.println("Created xml file");
             return true;
         }else{
             System.out.println("File already exists");
             return true;
         }
     }catch (IOException e)
     {
         System.out.println("Something went wrong");
         return false;
     }
 }


 public void  xmlRead()
 {
     String title;
     String author;
     String isbn;
     String year;
     String publisher;
     String pages;
     try{
         File inputFile = new File("library.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("book");
         
         for(int temp = 0; temp<nList.getLength();temp++)
         {
             Node nNode = nList.item(temp);
            
             if (nNode.getNodeType() == Node.ELEMENT_NODE)
             {
                 Element eElement = (Element) nNode;
           title = eElement.getElementsByTagName("title").item(0).getTextContent();
           author = eElement.getElementsByTagName("author").item(0).getTextContent();
           isbn = eElement.getElementsByTagName("isbn").item(0).getTextContent();
            year  = eElement.getElementsByTagName("year").item(0).getTextContent();
            publisher = eElement.getElementsByTagName("publisher").item(0).getTextContent();
           pages = eElement.getElementsByTagName("pages").item(0).getTextContent();

      Book book = new Book(title,author,isbn,year,publisher,pages);     
                 addBook(book);
             }
         }
     }catch (Exception e){
         e.printStackTrace();
     }
 }

 public void xmlWrite()
 {
     try{
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc =dBuilder.newDocument();

         Element eRoot = doc.createElement("library");
         doc.appendChild(eRoot);
    
for(Book book: library)
{

 Element eBook = doc.createElement("book");
      Attr aId = doc.createAttribute("id");


    eRoot.appendChild(eBook);    
     aId.setValue(Integer.toString(library.size()+1));
    eBook.setAttributeNode(aId);
   
    Element eTitle = doc.createElement("title");
  eTitle.appendChild(doc.createTextNode(book.title));
         eBook.appendChild(eTitle);
        
         Element eAuthor = doc.createElement("author");
       eAuthor.appendChild(doc.createTextNode(book.author));
          eBook.appendChild(eAuthor);

          Element eIsbn = doc.createElement("isbn");
          eIsbn.appendChild(doc.createTextNode(book.isbn));
              eBook.appendChild(eIsbn);

              Element eYear = doc.createElement("year");
              eYear.appendChild(doc.createTextNode(book.year));
              eBook.appendChild(eYear);


              Element ePublisher = doc.createElement("publisher");
              ePublisher.appendChild(doc.createTextNode(book.publisher));
              eBook.appendChild(ePublisher);

              Element ePages = doc.createElement("pages");
              ePages.appendChild(doc.createTextNode(book.pages));
              eBook.appendChild(ePages);
}

              TransformerFactory tFactory = TransformerFactory.newInstance();
              Transformer transformer = tFactory.newTransformer();
              DOMSource source = new DOMSource(doc);
              StreamResult result = new StreamResult(new File("library.xml"));
              transformer.transform(source, result);
              System.out.println("Task completed");
     }catch(Exception e)
     {
        e.printStackTrace();
     }
 }

public String saxOutput="";

public String getSaxOutput(String input)
{
    saxOutput+=input;
return saxOutput;
}

public void  saxRead()
{
  String output=null;

    try
    {

        SAXParserFactory fact = SAXParserFactory.newInstance();
        SAXParser saxParser = fact.newSAXParser();

        DefaultHandler handle = new DefaultHandler(){
            boolean title = false;
            boolean author = false;
            boolean isbn = false;
            boolean year = false;
            boolean publisher = false;
            boolean pages = false;

            public void startElement(String uri,String localName, String qName, Attributes attributes) throws SAXException{
                //System.out.println("Start Element: " +qName);
                if(qName.equals("title")) title = true;
            if(qName.equals("author")) author = true;
            if(qName.equals("isbn")) isbn = true;
            if(qName.equals("year")) year = true;
            if(qName.equals("publisher")) publisher = true;
            if(qName.equals("pages")) pages = true;

            }
            public void endElement(String uri, String localName, String qName)
            {
                //System.out.println("Start Element: " + qName);
            }

            public void characters(char[] ch , int start, int length) throws SAXException
            {
                if(title)
                {
                    getSaxOutput("\n====\nTitle: " + new String(ch, start,length));

                  // System.out.println("========\ntitle" + new String(ch, start,length));
                    title = false;
                }
                if (author)
                {
                    getSaxOutput("\nAuthor: " + new String(ch, start,length));
                    //System.out.println("author" + new String(ch, start,length));
                    author = false;
                }
                if (isbn)
                {
                    getSaxOutput("\nISBN: " + new String(ch, start,length));
                    //System.out.println("isbn" + new String(ch, start, length));
                    isbn = false;
                }
                if(year)
                {
                    getSaxOutput("\nYear: " + new String(ch,start,length));

                    //System.out.println("year"+ new String(ch, start,length));
                    year = false;
                }
                if(publisher)
                {
                    getSaxOutput("\nPublisher: " + new String(ch, start,length));
                   // System.out.println("Publisher" + new String(ch, start,length));
                    publisher = false;
                }
                if(pages)
                {
                    getSaxOutput("\nPages: " + new String(ch,start,length));

                    //System.out.println("pages" + new String(ch, start,length));
                    pages = false;
                }
            
            }
        };
        saxParser.parse("library.xml", handle);

}catch(Exception ex) {
        ex.printStackTrace();
    }



}
public void xStreamOutput()
{
    XStream xs = new XStream();
String xml = "";
    for(Book show: library)
    {
        xml += xs.toXML(show);
    }

    System.out.println(xml);

    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream("myfilename.xml");
        fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
        byte[] bytes = xml.getBytes("UTF-8");
        fos.write(bytes);

    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        if(fos!=null) {
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

    public void generateHtml()
    {
        FileOutputStream os= null;
        try {
            os = new FileOutputStream("pagina.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Source xml=new StreamSource("library.xml");

        Source xsl= new StreamSource("test.xsl");

        Result result=new StreamResult(os);
        try{
            Transformer transformer= TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xml, result);
        }catch(Exception e)
        {System.err.println("ERROR: "+e);}

    }

}
