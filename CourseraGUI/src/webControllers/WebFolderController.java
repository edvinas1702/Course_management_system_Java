package webControllers;

import com.google.gson.Gson;
import model.Course;
import model.Folder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

@Controller
@RequestMapping(value = "/folders")
public class WebFolderController {

    //READ
    @RequestMapping(value = "/getAllFolders", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllFolders(@RequestParam("courseId") int courseId) {
        ArrayList<Folder> allFolders = new ArrayList<>();
        Gson parser = new Gson();
        try {
            allFolders = DbOperations.getAllFoldersFromDb(courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parser.toJson(allFolders);
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/folders/getAllFolders?courseId=2
        //[{"id":2,"name":"kursai","dateModified":"Apr 19, 2021"}]
    }

    @RequestMapping(value = "/getFolder/{folder_name}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCourse(@PathVariable("folder_name") String folder_name) {
        Gson parser = new Gson();

        try {
            return parser.toJson(DbOperations.getFolderByName(folder_name));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during selection";
        }
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/folders/getFolder/pamokos
        //{"id":1,"name":"pamokos","dateModified":"Apr 19, 2021"}
    }


    //INSERT
    @RequestMapping(value = "/insertFolder", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertFolder(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String folder_name = data.getProperty("folder_name");
        LocalDate date_modified = LocalDate.parse(data.getProperty("date_modified"));
        int course_id = Integer.parseInt(data.getProperty("course_id"));

        try {
            DbOperations.insertFolder(folder_name, date_modified, course_id);

            return parser.toJson(DbOperations.getCourseByName(folder_name));
        } catch (Exception e) {
            return "There were errors during insert operation";
        }
    }
    //http://localhost:8080/CourseraGUI_JDBC_war_exploded/folders/insertFolder
    //    {
    //        "id":3,
    //            "folder_name": "webCourse",
    //            "date_modified": "2021-02-01",
    //            "course_id": 3
    //    }
    //}


    //UPDATE
    @RequestMapping(value = "/updateFolder", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody

    public String updateFolder(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);


        int folderId = Integer.parseInt(data.getProperty("id"));
        String folder_name = data.getProperty("folder_name");
        LocalDate date_modified = LocalDate.parse(data.getProperty("date_modified"));
        String course_id = data.getProperty("course_id");

        try {

            DbOperations.updateFolder(folderId, "folder_name", folder_name);
            DbOperations.updateFolder(folderId, "date_modified", date_modified );
            DbOperations.updateFolder(folderId, "course_id", course_id );

            return parser.toJson(DbOperations.getFolderByName(folder_name));
        } catch (Exception e){
            return "There were errors during update operation";
        }
     //http://localhost:8080/CourseraGUI_JDBC_war_exploded/folders/updateFolder
//        {
//            "id":2,
//                "folder_name": "kursai1",
//                "date_modified": "2021-04-20",
//                "course_id": 2
//        }
    }


    //DELETE
    @RequestMapping(value = "/delFolderId/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteFolderId(@PathVariable("id") int id) {
        try {
            DbOperations.deleteFolder(id);
            return "Record deleted";
        } catch (Exception e){
            return "There were errors during delete operation";
        }
    }


}

