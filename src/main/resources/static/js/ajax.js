const option = $("<option></option>");
const select = $("<select disabled></select>").attr("name", "roleIds");
let buttonEditUser;
let buttonDeleteUser;
let allUsers;

$(document).ready(function () {

    $.ajax("/rest", {
        dataType: 'json',
        success: function (msg) {
            allUsers = msg
            msg.forEach(function (element) {
               addUserInTable(element)
            })
            clickButtonEditDelete()
        }
    })
})

function addUserInTable(element) {
    let trLocal = $("<tr></tr>")
    trLocal.attr("id", "tr_" + element.id)
    let tdId = $("<td></td>")
    tdId.attr("id", "id_" + element.id)
    tdId.text(element.id).appendTo(trLocal)
    let tdLogin = $("<td></td>")
    tdLogin.attr("id", "login_" + element.id)
    tdLogin.text(element.login).appendTo(trLocal)
    let tdPassword = $("<td></td>")
    tdPassword.attr("id", "password_" + element.id)
    tdPassword.text(element.password).appendTo(trLocal)
    let tdRole = $("<td></td>")
    tdRole.attr("id", "role_" + element.id)
    tdRole.text(element.stringRoles).appendTo(trLocal)
    let tdEditButton = $("<td></td>")
    let editButton = $("<button type='button' data-toggle='modal'></button>");
    editButton.attr("id", "btn_edit_user")
    editButton.attr("class", "btn btn-primary")
    editButton.attr("value", element.id)
    editButton.attr("name", "userEditCallModal")
    editButton.text("Edit")
    editButton.appendTo(tdEditButton)
    tdEditButton.appendTo(trLocal)
    let tdDeleteButton = $("<td></td>")
    let deleteButton = $("<button type='button' data-toggle='modal'></button>");
    deleteButton.attr("id", "btn_delete_user")
    deleteButton.attr("class", "btn btn-danger")
    deleteButton.attr("value", element.id)
    deleteButton.attr("name", "userDeleteCallModal")
    deleteButton.text("Delete")
    deleteButton.appendTo(tdDeleteButton)
    tdDeleteButton.appendTo(trLocal)
    trLocal.appendTo($("#users"))
}

function clickButtonEditDelete() {
    buttonEditUser = $("[name = userEditCallModal]")
    buttonEditUser.click(
        function () {
            user = getUser(parseInt($(this).attr("value")))
            $("#editModalUser").attr("value", user.id)
            $("#idUserUpdate").attr("value", user.id)
            $("#loginUserUpdate").attr("value", user.login)
            $("#passwordUserUpdate").attr("value", "")
            $("#modalEdit").modal("show");
        }
    )
    buttonDeleteUser = $("[name = userDeleteCallModal]")
    buttonDeleteUser.click(
        function () {
            user = getUser(parseInt($(this).attr("value")))
            $("#deleteModalUser").attr("value", user.id)
            $("#idUserDelete").attr("value", user.id)
            $("#loginUserDelete").attr("value", user.login)
            $("#passwordUserDelete").attr("value", user.password)
            $("#roleUserDelete").attr("value", user.stringRoles)
            $("#modalDelete").modal("show");
        }
    )
}

let getUser = function (id) {
    let user
    $.each(allUsers, function (index, object) {
        if (object.id === id) {
            user = object;
        }
    })
    return user
}

function editUser() {
    let user = {
        id: $("#idUserUpdate").val(),
        login: $("#loginUserUpdate").val(),
        password: $("#passwordUserUpdate").val(),
        roleIds: $("#rolesEdit").val()
    }

    $.ajax("rest/admin/update", {
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(user),
        dataType: "json",
        success: function (msg) {

            $("#login_" + msg.id).text(msg.login);
            $("#password_" + msg.id).text(msg.password);
            $("#role_" + msg.id).text(msg.stringRoles);

        }
    })
    $("#modalEdit").modal('hide');
}

function deleteUser() {
    $.ajax("rest/admin/delete/" + $("#idUserDelete").val(), {
        method: "delete",
        dataType: "text",
        success: function (msg) {
            $("#tr_" + msg).children().remove();
        }
    });
    $("#modalDelete").modal('hide');
}

function addUser() {
    let user = {
        login: $("#addLogin").val(),
        password: $("#addPassword").val(),
        roleIds: $("#addRoles").val()
    }
    $.ajax("rest/admin/adduser", {
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(user),
        dataType: "json",
        success: function (msg) {
            addUserInTable(msg)
            allUsers.push(msg)
            clickButtonEditDelete()
            $("#addLogin").val("")
            $("#addPassword").val("")
            $("#addRoles").val("")
            $("#nav-home-tab").tab('show');
        }
    })
}


