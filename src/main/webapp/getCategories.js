$(document).ready(() => {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: getContextPath() + '/tasks.ajax',
        data: {
            server_action: "GET_CATEGORIES",
        },
    }).done((data) => {
        let parseData = JSON.parse(data);
        let rslHtml = ``;

        parseData.forEach(category =>
            rslHtml += `<option value='${category.id}/>'>${category.name}</option>`
        );
        document.getElementById("category-list").innerHTML = rslHtml;

    }).fail((err) => {
        alert("Error!!! - See console");
        console.log(err)
    })
});