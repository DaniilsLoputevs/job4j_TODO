$(function () {
    $('#btn-add-task').click(() => {
        console.log("SCRIPT START");

        let temp = sessionStorage.getItem("user");
        console.log("temp: ", temp);
        let user;
        if (temp === null) {
            user = "guest";
        } else {
            user = temp;
        }

        console.log("user: ", user);

        console.log("AJAX START");
        console.log("desc: ", $('#new-task-desc').val());
        console.log("user: ", user);

        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: getContextPath() + '/tasks.ajax',
            dataType: 'text/json',
            data: {
                server_action: "ADD_TASK",
                desc: $('#new-task-desc').val(),
                creator: user,
            },
        }).done((data) => {
            console.log("AJAX DONE");
            // window.location.href = getContextPath();
            alert("Task added!");
        }).fail((err) => {
            alert("Error!!! - See console");
            console.log(err);
        })

    });
});