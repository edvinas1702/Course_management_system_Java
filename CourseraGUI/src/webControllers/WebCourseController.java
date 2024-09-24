package webControllers;

import com.google.gson.Gson;
import model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

@Controller
@RequestMapping(value = "/courses")
public class WebCourseController {

    //READ
    @RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCourses(@RequestParam("courseIs") int courseIs) {
        ArrayList<Course> allCourses = new ArrayList<>();
        Gson parser = new Gson();
        try {
            allCourses = DbOperations.getAllCoursesFromDb(courseIs);
        } catch (Exception e){
            e.printStackTrace();
        }
        return parser.toJson(allCourses);
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/courses/getAllCourses?courseIs=1
    }

    //Read by course name
    //http://localhost:8080/CourseraGUI_JDBC_war_exploded/courses/getCourse/fizika
    @RequestMapping(value = "/getCourse/{name}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCourse(@PathVariable("name") String name) {
        Gson parser = new Gson();

        try {
            return parser.toJson(DbOperations.getCourseByName(name));
        } catch (Exception e){
            e.printStackTrace();
            return "Error during selection";
        }

    }

    //INSERT
    @RequestMapping(value = "/insertCourse", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertCourse(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String name = data.getProperty("name");
        LocalDate startDate = LocalDate.parse(data.getProperty("start"));
        LocalDate endDate = LocalDate.parse(data.getProperty("end"));
        int adminId = Integer.parseInt(data.getProperty("adminId"));
        int sysId = Integer.parseInt(data.getProperty("courseIs"));
        Double price = Double.parseDouble(data.getProperty("price"));
        try {
            DbOperations.insertRecordCourse(name, startDate, endDate, adminId, price, sysId);

            return parser.toJson(DbOperations.getCourseByName(name));
        } catch (Exception e){
            return "There were errors during insert operation";
        }
//        {
//            "id":5,
//                "name": "webCourse3",
//                "start": "2021-01-01",
//                "end": "2021-01-15",
//                "adminId": 1,
//                "courseIs": 1,
//                "price": 10
//        }

    }

    //UPDATE
    @RequestMapping(value = "/updateCourse", method = RequestMethod.PUT)
    //http://localhost:8080/CourseraGUI_JDBC_war_exploded/courses/updateCourse
    //{
    // "id":5,
    // "name": "webCourse3"
    // "start": "2021-01-01",
    // "end": "2021-01-15",
    // "price": 99.99
    //}
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCourse(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        int courseId = Integer.parseInt(data.getProperty("id"));

        String name = data.getProperty("name");
        Double price = Double.parseDouble(data.getProperty("price"));

        try {
            DbOperations.updateDbRecord(courseId, "name", name);
            if (!data.getProperty("start").equals("")) DbOperations.updateDbRecord(courseId, "start_date", LocalDate.parse(data.getProperty("start")));
            if (!data.getProperty("end").equals("")) DbOperations.updateDbRecord(courseId, "end_date", LocalDate.parse(data.getProperty("end")));

            DbOperations.updateDbRecord(courseId, "course_price", price);

            return parser.toJson(DbOperations.getCourseByName(name));
        } catch (Exception e){
            return "There were errors during insert operation";
        }

    }

    //Delete

    //Delete course by name
    @RequestMapping(value = "/delCourseName/{name}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteCourseName(@PathVariable("name") String name) {

        try {
            DbOperations.deleteDbRecord(name);
            return "Record deleted";
        } catch (Exception e){
            return "There were errors during delete operation";
        }
    }

    //Delete course by id
    @RequestMapping(value = "/delCourseId/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    //http://localhost:8080/CourseraGUI_JDBC_war_exploded/users/delUserId/9
    public String deleteCourseId(@PathVariable("id") int id) {
        try {
            DbOperations.deleteDbRecord(id);
            return "Record deleted";
        } catch (Exception e){
            return "There were errors during insert operation";
        }
    }


}
