
package projectt;

import java.time.LocalDate;


public class ClassP extends Program{
   
    private String daysWeek;
    
    public ClassP(String lesson,String subject,double ltime,double ftime,String note,String color,int UserID){
        this.lesson=lesson;
        this.subject=subject;
        this.ltime=ltime;
        this.ftime=ftime;
        this.note=note;
        this.color=color;
        this.UserID=UserID;
    }
    public ClassP(){
        
    }


    public String getDaysWeek(){
        return daysWeek;
    }

    public void setDaysWeek(String daysWeek){
        this.daysWeek = daysWeek;
    }
    
}
