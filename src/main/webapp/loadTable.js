$(document).ready(() => {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: getContextPath() + '/tasks.ajax',
        data: {
            server_action: "GET_TABLE"
        }
    }).done((data) => {
        // console.log("data");
        // console.log(data);
        for (let i = 0; i < data.length; i++) {
            // prepare data
            let task = data[i];
            let rsl = ``;
            let checkValue = "";
            let categoryName = "";

            if (task.done === true) {
                checkValue = "checked";
            }
            // console.log("data N" + i, data[i]);
            // console.log("checkValue", checkValue);
            for (let j = 0; j < task.category.length; j++) {
                categoryName += task.category[j].name + ", ";
            }
            categoryName = categoryName.substring(0, categoryName.length - 2);
            // prepare form
            rsl += `
                <tr id="line">
                <td>${task.id}</td>
                <td>${task.description} </td>
                <td>${categoryName} </td>
                <td>${task.creator} </td>
                <td>${task.created} </td>
                <td><input id="check-id${task.id}" type="checkbox" class="check" name="table-line" ${checkValue}></td>
                </tr>
                `;
            // set form
            $('#tableBody tr:last').after(rsl);
        }
    }).fail((err) => {
        alert("Error!!! - See console");
        console.log(err)
    })
});

function getContextPath() {
    return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}