import React from 'react';
import Event from './Event.jsx';

export default function Commands() {
  return <Event eventTitle="dummy command" eventAction="{execute_command|mkdir /tmp/no-more-manager/}" />;
}
