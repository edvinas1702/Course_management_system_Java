package webControllers;

import com.google.gson.Gson;
import model.Administrator;
import model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

@Controller
@RequestMapping(value = "/users")
public class WebUserController {

  //READ

  @RequestMapping(value = "/getAllAdmins", method = RequestMethod.GET)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public String getAllAdmins(@RequestParam("courseIs") int courseIs) {
      ArrayList<Administrator> allAdmins = new ArrayList<>();
      Gson parser = new Gson();
      try {
          allAdmins = DbOperations.getAllAdminsFromDb(courseIs);
      } catch (Exception e){
          e.printStackTrace();
      }
      return parser.toJson(allAdmins);
      //http://localhost:8080/CourseraGUI_JDBC_war_exploded/users/getAllAdmins?courseIs=1

  }


    @RequestMapping(value = "/getUser/{login}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCourse(@PathVariable("login") String name) {
        Gson parser = new Gson();

        try {
            return parser.toJson(DbOperations.getUserByName(name));
        //http://localhost:8080/CourseraGUI_JDBC_war_exploded/users/getUser/admin
        } catch (Exception e){
            e.printStackTrace();
            return "Error during selection";
        }

    }


  //Authorization
  @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public String loginEmployee(@RequestBody String request) {
      Gson parser = new Gson();
      Properties data = parser.fromJson(request, Properties.class);
      String loginName = data.getProperty("login");
      String password = data.getProperty("psw");
      int courseIs = Integer.parseInt(data.getProperty("courseIs"));
      Administrator administrator = null;
      try{
          administrator = DbOperations.getAdmin(loginName, password, courseIs);
      } catch (Exception e){
          return "Error";
      }

      if (administrator == null){
          return "Wrong credentials";
      }
      return Integer.toString(administrator.getId());
  }

  //INSERT
  @RequestMapping(value = "/insertAdministrator", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public String insertAdministrator(@RequestBody String request) {
      Gson parser = new Gson();
      Properties data = parser.fromJson(request, Properties.class);

      String login = data.getProperty("login");
      String psw = data.getProperty("psw");
      String email = data.getProperty("email");
      String phoneNum = data.getProperty("phoneNum");
      int courseIs = Integer.parseInt(data.getProperty("courseIs"));
      try {
          DbOperations.insertRecordAdmin(login, psw, email, phoneNum, courseIs);
          return parser.toJson(DbOperations.getUserByName(login));
      } catch (Exception e){
          return "There were errors during insert operation";
      }

  }

  //UPDATE
  @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
//  {
//      "id": 9,
//          "login":"admin3",
//          "psw":"12345",
//          "email": "email2"
//  }
//http://localhost:8080/CourseraGUI_JDBC_war_exploded/users/updateUser

  public String updateUser(@RequestBody String request) {
      Gson parser = new Gson();
      Properties data = parser.fromJson(request, Properties.class);


      int userId = Integer.parseInt(data.getProperty("id"));
      String updateUserLogin = data.getProperty("login");
      String updateUserPsw = data.getProperty("psw");
      String updateUserEmail = data.getProperty("email");

      try {

          DbOperations.updateUserDbRecord(userId, "login", updateUserLogin);
          DbOperations.updateUserDbRecord(userId, "psw", updateUserPsw );
          DbOperations.updateUserDbRecord(userId, "email", updateUserEmail );

          return parser.toJson(DbOperations.getUserByName(updateUserLogin));
      } catch (Exception e){
          return "There were errors during update operation";
      }

  }

  //DELETE
  //Delete course by id

    @RequestMapping(value = "/delUserId/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public String deleteUserId(@PathVariable("id") int id) {
      try {
          DbOperations.deleteUser(id);
          return "Record deleted";
      } catch (Exception e){
          return "There were errors during delete operation";
      }
  }
}
