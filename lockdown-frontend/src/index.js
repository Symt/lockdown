import React, { Component } from "react";
import ReactDOM from "react-dom";
import $ from "jquery";
import data from "../dummy_api/data.json"
import { } from "./bootstrap.bundle.min.js"
import bootstrap from "./bootstrap.min.css"
import style from "./main.css";

function shiftPage(page) {
  $("#home").addClass("hidden");
  $("#links").addClass("hidden");
  $("#applications").addClass("hidden");
  $("#commands").addClass("hidden");
  $("#home-anchor").removeClass("bg-primary");
  $("#links-anchor").removeClass("bg-primary");
  $("#applications-anchor").removeClass("bg-primary");
  $("#commands-anchor").removeClass("bg-primary");
  $("#home-anchor").addClass("bg-dark");
  $("#links-anchor").addClass("bg-dark");
  $("#applications-anchor").addClass("bg-dark");
  $("#commands-anchor").addClass("bg-dark");
  $("#" + page).removeClass("bg-dark");
  $("#" + page).addClass("bg-primary");
  $("#" + page.replace("-anchor", "")).removeClass("hidden");
}

function tableGen(json) {
  var rows = [];
  for (var i = 0; i < json.available_downloads.length; i++) {
    rows.push(
      <tr key={i}><td>{json.available_downloads[i]["title"]}</td><td><a href={"download_file|/tmp/|"+json.available_downloads[i]['link']+"|"+json.available_downloads[i]['application_name']}>{json.available_downloads[i]['application_name']}</a></td></tr>
    )
  }
  return rows;
}

$("document").ready(() => {
  ReactDOM.render(<Home />, document.getElementById("home"));
  ReactDOM.render(<Links />, document.getElementById("links"));
  ReactDOM.render(<Applications />, document.getElementById("applications"));
  ReactDOM.render(<Commands />, document.getElementById("commands"));
  ReactDOM.render(<Navigation />, document.getElementById("nav"))
});


class Applications extends Component {
  constructor(props) {
    super(props);
    this.state = {
      json: data
    };
  }

  render() {
    return (
<table className="table table-striped w-50 center table-bordered">
  <thead className="font-weight-bold">
    <tr>
      <td>Application</td>
      <td>Download</td>
    </tr>
  </thead>
  <tbody>
    {tableGen(this.state.json)}
  </tbody>
</table>
    );
  }
}
class Links extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <div>Links</div>
  }
}

class Home extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <div>Home</div>
  }
}
class Commands extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <div>Commands</div>
  }
}

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.clickHandler = this.clickHandler.bind(this);
  }

  clickHandler(e) {
    shiftPage(e.target.id);
  }

  render() {
    return (
  <div className="navbar navbar-dark bg-dark text-white text-center padding-remove">
    <a id="home-anchor" className="bg-primary makeshift-button w-25 padding-addition border-right" onClick={this.clickHandler}>Lockdown</a>
    <a id="links-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left border-right" onClick={this.clickHandler}>Links</a>
    <a id="applications-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left border-right" onClick={this.clickHandler}>Applications</a>
    <a id="commands-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left" onClick={this.clickHandler}>Commands</a>
  </div>
    )
  }
}