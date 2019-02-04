import React, {Component} from 'react';
import $ from 'jquery';
export default class Home extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <hr id="hr" />
        <div className="unselectable" id="lockdown-center">
            <p>Lockdown</p>
        </div>
      </div>
    )
  }
}