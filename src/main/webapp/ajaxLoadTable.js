$(document).ready(() => {
    /* without this var - script will not work, DON'T touch!
     * without transferring ${data[]} to ${arr[]} it will not work.
     */
    const seatList = [];
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: getContextPath() + '/tasks.ajax',
    }).done((data) => {
        for (let i = 0; i < data.length; i++) {
            seatList.push(data[i]);
        }
        let rsl = ``;
        let doneValue = "";
        for (let i = 0; i < seatList.length; i++) {
            let seat = seatList[i];

            if (seat.done === true) {
                doneValue = "checked";
            }

            let lineIndex = "line";

            rsl += `
                <tr id="${lineIndex}">
                <td>${seat.id}</td>
                <td>${seat.description} </td>
                <td>${seat.created} </td>
                <td><input type="checkbox" class="check" name="table-line" ${doneValue}></td>
                </tr>
                `;
            $('#tableBody tr:last').after(rsl);
            rsl = '';
        }
    }).fail((err) => {
        alert("Error!!! - See console");
        console.log(err)
    })
});

function getContextPath() {
    return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}