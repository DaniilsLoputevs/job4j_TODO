$(function () {
    $('#btn-upd-table').click(() => {
        const table = document.getElementById("table");
        let tasks = [];

        // skip table head && first EMPTY line !!!
        for (let i = 2, row; row = table.rows[i]; i++) {
            let cells = row.cells;

            let dataId = cells[0].innerHTML;
            let dataDesc = cells[1].innerHTML;
            let dataCreated = cells[2].innerHTML;

            let template = `check-id${dataId}`;
            let dataDone = document.getElementById(template).checked;

            let jsonTask = JSON.stringify({
                id: dataId,
                description: dataDesc,
                created: dataCreated,
                done: dataDone
            });
            tasks.push(jsonTask);
        }
        tasks = "[" + tasks + "]";

        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: getContextPath() + '/tasks.ajax',
            dataType: 'text',
            data: {
                server_action: "UPD_TABLE",
                tasks: tasks
            },
        }).done((data) => {
            window.location.href = getContextPath();
            alert("table updated!");
        }).fail((err) => {
            alert("Error!!! - See console");
            console.log(err);
        })

    });
});