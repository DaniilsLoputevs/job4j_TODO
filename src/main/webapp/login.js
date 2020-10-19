// Authorization
$(function () {
    $('#btn-auth-submit').click(() => {
        const email = $('#auth-email').val();
        const password = $('#auth-password').val();

        if (email !== "" && password !== "") {

            $.ajax({
                type: 'GET',
                crossdomain: true,
                url: getContextPath() + '/login.ajax',
                dataType: 'text',
                data: {
                    server_action: "AUTH_USER",
                    email: email,
                    password: password,
                },
            }).done((data) => {
                let parseData = JSON.parse(data);

                if (parseData.user === "user Not Founded.") {
                    alert(parseData.user);
                } else if (parseData.user === "incorrect Password.") {
                    alert(parseData.user);
                } else {
                    document.getElementById("link-login").innerText = parseData.user;
                    sessionStorage.setItem("user", parseData.user);
                    alert("Authorization success!");
                }
            }).fail((err) => {
                alert("Error!!! - See console");
                console.log(err);
            })

        } else {
            alert("fill all fields!");
        }

    });
});

// Registration
$(function () {
    $('#btn-reg-submit').click(() => {
        const name = $('#reg-name').val();
        const password = $('#reg-password').val();
        const email = $('#reg-email').val();

        if (name !== "" && password !== "" && email !== "") {

            $.ajax({
                type: 'POST',
                crossdomain: true,
                url: getContextPath() + '/login.ajax',
                dataType: 'text',
                data: {
                    server_action: "REG_USER",
                    name: name,
                    password: password,
                    email: email,
                },
            }).done((data) => {
                window.location.href = getContextPath();
                alert("Register success!");
            }).fail((err) => {
                alert("Error!!! - See console");
                console.log(err);
            })

        } else {
            alert("fill all fields!");
        }

    });
});

// Identify user (Login(guest) || userName)
$(document).ready(() => {
    let loginHrefName = sessionStorage.getItem("user");
    if (loginHrefName == null) {
        loginHrefName = "Login";
    }
    document.getElementById("link-login").innerText = loginHrefName;
});