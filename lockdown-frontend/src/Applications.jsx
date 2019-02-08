import React, { Component } from 'react';
import data from '../dummy_api/data.json';
import Event from './Event.jsx';

function tableGen(json) {
  const rows = [];
  for (let i = 0; i < json.available_downloads.length; i += 1) {
    rows.push(
      <tr key={i}>
        <td>{json.available_downloads[i].title}</td>
        <td><Event eventAction={`{download_file|/tmp/|${json.available_downloads[i].link}|${json.available_downloads[i].application_name}}`} eventTitle={json.available_downloads[i].application_name} /></td>
      </tr>,
    );
  }
  return rows;
}

export default class Applications extends Component {
  constructor(props) {
    super(props);
    this.state = {
      json: data,
    };
  }

  render() {
    const { json } = this.state;
    return (
      <table className="table table-striped w-50 table-bordered allow-center horizontal-center vertical-center">
        <thead className="font-weight-bold">
          <tr>
            <td>Application</td>
            <td>Download</td>
          </tr>
        </thead>
        <tbody>
          {tableGen(json)}
        </tbody>
      </table>
    );
  }
}
