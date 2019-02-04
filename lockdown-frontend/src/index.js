import React, { Component } from "react";
import ReactDOM from "react-dom";
import $ from "jquery";
import { } from "./bootstrap.bundle.min.js"
import bootstrap from "./bootstrap.min.css"
import style from "./main.css";
import Home from './Home.js'
import Links from './Links.js'
import Applications from './Applications.js'
import Commands from './Commands.js'
import Navigation from './Navigation'

$("document").ready(() => {
  ReactDOM.render(<Home />, document.getElementById("home"));
  ReactDOM.render(<Links />, document.getElementById("links"));
  ReactDOM.render(<Applications />, document.getElementById("applications"));
  ReactDOM.render(<Commands />, document.getElementById("commands"));
  ReactDOM.render(<Navigation />, document.getElementById("nav"))
  homestart();
});

let percent = 0;

function homestart() {
  setTimeout(function () {
      if (percent == 26) {
        $("#lockdown-center p").css("display", "block")
          percent = 0;
          $("#hr").css("display", "block")
          hrstart();
          return;
      }
      $("#lockdown-center").css("height", percent + "vw");
      percent++;
      homestart();
  }, 10);
}

function hrstart() {
  setTimeout(function () {
      if (percent == 101) {
          return;
      }
      $("#hr").css("width", percent + "%");
      percent++;
      hrstart();
  }, 10);
}