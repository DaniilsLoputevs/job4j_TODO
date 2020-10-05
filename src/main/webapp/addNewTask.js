$(function () {
    $('#add-task-btn').click(() => {
        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: getContextPath() + '/tasks.ajax',
            body: {
                desc: $('#task-desc').val()
            },
        }).done((data) => {
            window.location.href = getContextPath();
            alert("Задача добавлена!");
        }).fail((err) => {
            alert("Error!!! - See console");
            console.log(err);
        })

    });
});