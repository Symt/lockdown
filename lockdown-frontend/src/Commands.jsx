import React from 'react';
import Event from './Event.jsx';

export default function Commands() {
  return <div><Event eventTitle="dummy command" eventAction="{execute_command|say 'this command is supposed to be fake, why did you run it'}" /></div>;
}
