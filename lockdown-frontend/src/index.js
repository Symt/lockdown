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
});

