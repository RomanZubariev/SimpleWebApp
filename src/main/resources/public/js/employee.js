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
      $('#firstName').val('');
      $('#lastName').val('');
      $('#departmentId').val('');
      $('#jobTitle').val('');
      $('#gender').val('');
      $('#dateOfBirth').val('');
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
function getTab(){
  $('#output').html('');
  document.getElementById('id').disabled = false;
  document.getElementById('firstName').disabled = true;
  document.getElementById('lastName').disabled = true;
  document.getElementById('departmentId').disabled = true;
  document.getElementById('jobTitle').disabled = true;
  document.getElementById('gender').disabled = true;
  document.getElementById('dateOfBirth').disabled = true;
}
function postTab(){
  $('#output').html('');
  document.getElementById('id').disabled = true;
  $('#id').val('null');
  document.getElementById('firstName').disabled = false;
  document.getElementById('lastName').disabled = false;
  document.getElementById('departmentId').disabled = false;
  document.getElementById('jobTitle').disabled = false;
  document.getElementById('gender').disabled = false;
  document.getElementById('dateOfBirth').disabled = false;
}
function putTab(){
  $('#output').html('');
  document.getElementById('id').disabled = false;
  document.getElementById('firstName').disabled = false;
  document.getElementById('lastName').disabled = false;
  document.getElementById('departmentId').disabled = false;
  document.getElementById('jobTitle').disabled = false;
  document.getElementById('gender').disabled = false;
  document.getElementById('dateOfBirth').disabled = false;
}
function deleteTab(){
  $('#output').html('');
  document.getElementById('id').disabled = false;
  document.getElementById('firstName').disabled = true;
  document.getElementById('lastName').disabled = true;
  document.getElementById('departmentId').disabled = true;
  document.getElementById('jobTitle').disabled = true;
  document.getElementById('gender').disabled = true;
  document.getElementById('dateOfBirth').disabled = true;
}