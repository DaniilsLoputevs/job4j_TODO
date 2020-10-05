$(function () {
    $('#btn-add-task').click(() => {
        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: getContextPath() + '/tasks.ajax',
            dataType: 'text/json',
            data: {
                server_action: "ADD_TASK",
                desc: $('#task-desc').val()
            },
        }).done((data) => {
            window.location.href = getContextPath();
            alert("Task added!");
        }).fail((err) => {
            alert("Error!!! - See console");
            console.log(err);
        })

    });
});