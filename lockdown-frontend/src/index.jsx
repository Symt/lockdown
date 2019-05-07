import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';
import { } from './bootstrap.bundle.min';
import './bootstrap.min.css';
import './main.css';
import Home from './Home.jsx';
import Links from './Links.jsx';
import Applications from './Applications.jsx';
import Commands from './Commands.jsx';
import Navigation from './Navigation.jsx';

/* global document */

$(document).ready(() => {
  ReactDOM.render(<Home />, document.getElementById('home'));
  ReactDOM.render(<Links />, document.getElementById('links'));
  ReactDOM.render(<Applications />, document.getElementById('applications'));
  ReactDOM.render(<Commands />, document.getElementById('commands'));
  ReactDOM.render(<Navigation />, document.getElementById('nav'));
});

let percent = 0;

function hrstart() {
  setTimeout(() => {
    if (percent === 101) {
      return;
    }
    $('#hr').css('width', `${percent}%`);
    percent += 1;
    hrstart();
  }, 10);
}


function homestart() {
  setTimeout(() => {
    if (percent === 26) {
      $('#lockdown-center p').css('display', 'block');
      percent = 0;
      $('#hr').css('display', 'block');
      hrstart();
      return;
    }
    $('#lockdown-center').css('height', `${percent}vw`);
    percent += 1;
    homestart();
  }, 10);
}

$('document').ready(() => {
  setTimeout(() => { $('#lockdown-center').css('display', 'block'); homestart(); }, 1000);
});
