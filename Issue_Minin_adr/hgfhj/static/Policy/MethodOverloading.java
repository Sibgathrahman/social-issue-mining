class Overload

   {

     double area(float l,float w,float h)
          {

            return l*w*h;
          
           }

     double area(float l)

          {

            return l*l*l;

          }
 
     double area(float r,float h)

          {

              return 3.1416*r*r*h;

           }

     }

public class MethodOverloading

      {

         public static void main(String args[])

               {
                  Overload o=new Overload();
                  double rectangleBox=o.area(5,8,9);
                  System.out.println("area of rectangle box is"+rectangleBox);
                  System.out.println("");
                  double cube=o.area(5);
                  System.out.println("area of cube is"+cube);
                  System.out.println("");
                  double cylinder=o.area(6,12);
                  System.out.println("area of cylinder is"+cylinder);

               }

          }