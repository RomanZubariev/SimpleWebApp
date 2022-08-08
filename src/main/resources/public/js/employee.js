function getEmployee (){
  var employeeId = $('#id').val();
  var address = 'http://localhost:8080/simplewebapp/employee/'+employeeId;
  $.ajax({
    url: address,
    type: 'GET',
    success: function(data){
      $('#firstName').val(data.firstName);
      $('#lastName').val(data.lastName);
      $('#departmentId').val(data.departmentId);
      $('#jobTitle').val(data.jobTitle);
      $('#gender').val(data.gender);
      $('#dateOfBirth').val(data.dateOfBirth);
      $('#output').html("Success");
    },
    error: function(xhr) {
      $('#output').html(xhr.status+" " +xhr.responseText);
    }
  });
}
function postEmployee (){
  var address = 'http://localhost:8080/simplewebapp/employee'
  var jsondata = JSON.stringify({
   "employeeId": null,
   "firstName":$('#firstName').val(),
   "lastName":$('#lastName').val(),
   "departmentId":$('#departmentId').val(),
   "jobTitle":$('#jobTitle').val(),
   "gender":$('#gender').val(),
   "dateOfBirth":$('#dateOfBirth').val()
  });
  $.ajax({
    url: address,
    type: 'POST',
    data : jsondata,
    contentType : 'application/json',
    success: function(result){
      $('#output').html("Success! New employee id = "+result.employeeId);
    },
    error: function(xhr) {
      $('#output').html(xhr.status+" " +xhr.responseText);
    }
  });
}

function putEmployee (){
  var address = 'http://localhost:8080/simplewebapp/employee';
  var jsondata = JSON.stringify({
   "employeeId": $('#id').val(),
   "firstName":$('#firstName').val(),
   "lastName":$('#lastName').val(),
   "departmentId":$('#departmentId').val(),
   "jobTitle":$('#jobTitle').val(),
   "gender":$('#gender').val(),
   "dateOfBirth":$('#dateOfBirth').val()
  });
  $.ajax({
    url: address,
    type: 'PUT',
    data: jsondata,
    contentType : 'application/json',
    success: function(data){
      $('#output').html("Success: employee with id = " + data.employeeId + " is modified.");
    },
    error: function(xhr) {
      $('#output').html(xhr.status+" " +xhr.responseText);
    }
  });
}
function deleteEmployee (){
  var employeeId = $('#id').val();
  var address = 'http://localhost:8080/simplewebapp/employee/'+employeeId;
    $.ajax({
      url: address,
      type: 'DELETE',
      success: function(data){
        $('#output').html("Success: employee with id = " + employeeId + " is deleted.");
      },
      error: function(xhr) {
        $('#output').html(xhr.status+" " +xhr.responseText);
      }
    });
}