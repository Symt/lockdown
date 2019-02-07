import React, {Component} from 'react';
import $ from 'jquery';
import Event from './Event.js';

export default class Commands extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <div><Event eventTitle="dummy command" eventAction="{execute_command|say 'this command is supposed to be fake, why did you run it'}" /></div>
  }
}