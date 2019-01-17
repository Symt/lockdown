function start(num,paths,names) {
    add(num,paths,names);
    homestart().then(function () {
        percent = 0;
        document.getElementById("hr").style.display = "block";
        hrstart().then(function cb() {
            setTimeout(function () {
                if (percent < 101) {
                    cb();
                } else {
                    document.getElementById("title").style.display = "block";
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
        document.getElementById("home").style.display = "block";
        document.getElementById("applications").style.display = "none";
        document.getElementById("commands").style.display = "none";
        document.getElementById("settings").style.display = "none";
        document.getElementById("downloads").style.display = "none";
    } else if (location == "Applications") {
        document.getElementById("home").style.display = "none";
        document.getElementById("applications").style.display = "block";
        document.getElementById("commands").style.display = "none";
        document.getElementById("settings").style.display = "none";
        document.getElementById("downloads").style.display = "none";
    } else if (location == "Settings") {
        document.getElementById("home").style.display = "none";
        document.getElementById("applications").style.display = "none";
        document.getElementById("commands").style.display = "none";
        document.getElementById("settings").style.display = "block";
        document.getElementById("downloads").style.display = "none";
    } else if (location == "Commands") {
        document.getElementById("home").style.display = "none";
        document.getElementById("applications").style.display = "none";
        document.getElementById("commands").style.display = "block";
        document.getElementById("settings").style.display = "none";
        document.getElementById("downloads").style.display = "none";
    } else if (location == "Downloads") {
        document.getElementById("home").style.display = "none";
        document.getElementById("applications").style.display = "none";
        document.getElementById("commands").style.display = "none";
        document.getElementById("settings").style.display = "none";
        document.getElementById("downloads").style.display = "block";
    }
}

async function homestart() {
    setTimeout(function () {
        if (percent == 26) {
            return;
        }
        document.getElementById("lockdown-center").style.height = percent + "vw";
        percent++;
        homestart();
    }, 10);
}

async function hrstart() {
    setTimeout(function () {
        if (percent == 101) {
            return;
        }
        document.getElementById("hr").style.width = percent + "%";
        percent++;
        hrstart();
    }, 10);
}

function add(num,paths,names) {
    var buttonDiv = document.getElementById("downloads");
    for (var i = 1; i <= num; i++) {
        buttonDiv.innerHTML += '<button type="button" class="big-btn" href="{download_file|/Desktop/|'+paths[i-1]+'|'+names[i-1]+'}">' + names[i-1] + '</button>';
    }
}