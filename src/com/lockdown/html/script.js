function start(num, paths, names) {
    add(num, paths, names);
    homestart().then(function () {
        percent = 0;
        document.getElementById("hr").style.display = "block";
        hrstart().then(function cb() {
            setTimeout(function () {
                if (percent < 101) {
                    cb();
                } else {
                    $("#title").css("display", "block");
                }
            }, 10);
        });
    });
}

function update(location, self) {
    var items = document.getElementsByClassName("navbutton");
    for (var i = 0; i < items.length; i++) {
        items[i].classList.remove("active");
    }
    self.classList.add("active");
    if (location == "Home") {
        hideAll();
        $("#home").css("display", "block");
    } else if (location == "Applications") {
        hideAll();
        $("#applications").css("display", "block");
    } else if (location == "Settings") {
        hideAll();
        $("#settings").css("display", "block");
    } else if (location == "Commands") {
        hideAll();
        $("#commands").css("display", "block");
    } else if (location == "Downloads") {
        hideAll();
        $("#downloads").css("display", "block");
    }
}

function hideAll() {
    $("#home").css("display", "none");
    $("#applications").css("display", "none");
    $("#commands").css("display", "none");
    $("#settings").css("display", "none");
    $("#downloads").css("display", "none");
}

async function homestart() {
    setTimeout(function () {
        if (percent == 26) {
            return;
        }
        $("#lockdown-center").css("height", percent + "vw");
        percent++;
        homestart();
    }, 10);
}

async function hrstart() {
    setTimeout(function () {
        if (percent == 101) {
            return;
        }
        $("#hr").css("width", percent + "%");
        percent++;
        hrstart();
    }, 10);
}

function add(num, paths, names) {
    for (var i = 1; i <= num; i++) {
        $("#downloads").html($("#downloads").html()+'<button type="button" class="big-btn" href="{download_file|/Desktop/|' + paths[i - 1] + '|' + names[i - 1] + '}">' + names[i - 1] + '</button>');
    }
}