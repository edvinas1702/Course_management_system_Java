package webControllers;

import com.google.gson.Gson;
import model.Course;
import model.File;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

@Controller
@RequestMapping(value = "/files")
public class WebFileController {

    //READ
    @RequestMapping(value = "/getAllFiles", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllFiles(@RequestParam("folder_id") int folder_id) {
        ArrayList<File> allFiles = new ArrayList<>();
        Gson parser = new Gson();
        try {
            allFiles = DbOperations.getAllFilesFromDb(folder_id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return parser.toJson(allFiles);
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/files/getAllFiles?folder_id=1
        //[{"id":1,"name":"testas","dateAdded":"Apr 19, 2021","linkToFile":"folder/testas"}]
    }

    @RequestMapping(value = "/getFile/{name}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFile(@PathVariable("name") String name) {
        Gson parser = new Gson();

        try {
            return parser.toJson(DbOperations.getFileByName(name));
        } catch (Exception e){
            e.printStackTrace();
            return "Error during selection";
        }
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/files/getFile/testas
        //{"id":1,"name":"testas","dateAdded":"Apr 19, 2021","linkToFile":"folder/testas"}
    }
    //INSERT

    @RequestMapping(value = "/insertFile", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertFile(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String name = data.getProperty("name");
        LocalDate date_added = LocalDate.parse(data.getProperty("date_added"));
        String file_path = data.getProperty("file_path");
        int folder_id = Integer.parseInt(data.getProperty("folder_id"));

        try {
            DbOperations.insertFile(name, date_added, file_path, folder_id);

            return parser.toJson(DbOperations.getFileByName(name));
        } catch (Exception e) {
            return "There were errors during insert operation";
        }
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/files/insertFile
//        {
//            "id":2,
//                "name": "testas1",
//                "date_added": "2021-04-20",
//                "file_path": "folder/testas",
//                "folder_id": 2
//        }
    }

    //UPDATE
    @RequestMapping(value = "/updateFile", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody

    public String updateFile(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);


        int fileId = Integer.parseInt(data.getProperty("id"));
        String name = data.getProperty("name");
        LocalDate date_added = LocalDate.parse(data.getProperty("date_added"));
        String file_path = data.getProperty("file_path");
        int folder_id = Integer.parseInt(data.getProperty("folder_id"));

        try {

            DbOperations.updateFile(fileId, "name", name);
            DbOperations.updateFile(fileId, "date_added", date_added );
            DbOperations.updateFile(fileId, "file_path", file_path );
            DbOperations.updateFile(fileId, "folder_id", folder_id );

            return parser.toJson(DbOperations.getFolderByName(name));
        } catch (Exception e){
            return "There were errors during update operation";
        }
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/files/updateFile
//        {
//            "id":2,
//                "name": "testas3",
//                "date_added": "2021-04-20",
//                "file_path": "folder/testas",
//                "folder_id": 2
//        }
    }


    //DELETE
    @RequestMapping(value = "/delFileId/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteFileId(@PathVariable("id") int id) {
        try {
            DbOperations.deleteFile(id);
            return "Record deleted";
        } catch (Exception e){
            return "There were errors during delete operation";
        }
    }
    //http://localhost:8080/CourseraGUI_JDBC_war_exploded/files/delFileId/2


}
