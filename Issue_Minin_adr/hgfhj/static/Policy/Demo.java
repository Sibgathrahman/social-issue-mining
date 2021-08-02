import java.applet.Applet;
import java.awt.List;
import java.awt.Color;

public class Demo extends Applet
      {
        
        public void init()
           {

              List l=new List(8,true);
              l.add("Desktop");
              l.add("Laptop");
              l.add("Tablet");
              l.add("Phone");
              l.add("Kindle");
              l.add("Screen");
              l.add("System");
              add(l);
              setBackground(Color.blue);

              add(l);
              l.remove(3);
              setBackground(Color.blue);

            }
        }