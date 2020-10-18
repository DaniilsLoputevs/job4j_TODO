$(function () {
    $('#btn-add-task').click(() => {
        console.log("SCRIPT START");

        let dataDesc = $('#new-task-desc').val();
        let dataUser = getUserOrGuest();
        let dataCategoriesIds = getCategoriesIds();

        console.log("AJAX START");
        console.log("desc: ", dataDesc);
        console.log("user: ", dataUser);
        console.log("categoriesIds: ", dataCategoriesIds);

        alert("Manual stop send");

        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: getContextPath() + '/tasks.ajax',
            data: {
                server_action: "ADD_TASK",
                desc: dataDesc,
                creator: dataUser,
                categoryIds: dataCategoriesIds
            },
        }).done((data) => {
            alert("Task added!");
            console.log("AJAX DONE");
            // window.location.href = getContextPath();
        }).fail((err) => {
            alert("Error!!! - See console");
            alert(err.error);
            console.log(err);
        })

    });
});

function getUserOrGuest() {
    let user = sessionStorage.getItem("user");
    if (user === null) {
        user = "guest";
    }
    return user;
}

function getCategoriesIds() {
    let idsAsString = "";
    let sel = document.getElementById("category-list");

    for (let i = 0; i < sel.options.length; i++) {
        let selectOption = sel.options[i];

        if (selectOption.selected) {
            let value = substringFrom(selectOption.value, 0, 2);
            idsAsString += value + "-";
        }
    }
    return substringFrom(idsAsString, 0, 1);
}

function substringFrom(String, start, charsSkipFromEnd) {
    return String.substring(start, String.length - charsSkipFromEnd);

}