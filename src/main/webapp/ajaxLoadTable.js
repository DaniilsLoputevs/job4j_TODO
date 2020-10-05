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

        for (let i = 0; i < seatList.length; i++) {
            let seat = seatList[i];
            let checkValue = "";
            let rsl = ``;

            if (seat.done === true) {
                checkValue = "checked";
            }

            rsl += `
                <tr id="line">
                <td>${seat.id}</td>
                <td>${seat.description} </td>
                <td>${seat.created} </td>
                <td><input id="check-id${seat.id}" type="checkbox" class="check" name="table-line" ${checkValue}></td>
                </tr>
                `;
            $('#tableBody tr:last').after(rsl);
            rsl = '';
            checkValue = "";
        }
    }).fail((err) => {
        alert("Error!!! - See console");
        console.log(err)
    })
});

function getContextPath() {
    return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}