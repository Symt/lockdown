import React, { Component } from 'react';
import axios from 'axios';
import Event from './Event.jsx';

function tableGen(json) {
  const rows = [];
  if (json === null) {
    return rows;
  }
  for (let i = 0; i < json.length; i += 1) {
    rows.push(
      <tr key={i}>
        <td>{json[i].title}</td>
        <td><Event eventAction={`{download_file|/tmp/|${json[i].link}|${json[i].link.split('/')[json[i].link.split('/').length - 1]}}`} eventTitle={json[i].application_name} /></td>
      </tr>,
    );
  }
  return rows;
}

export default class Applications extends Component {
  constructor(props) {
    super(props);
    this.state = {
      json: null,
      error: null,
    };
  }

  componentDidMount() {
    const instance = axios.create({ timeout: 5000 });
    instance.get('http://99.169.62.62:5000/').then((json) => {
      this.setState({ json });
    }).catch((error) => {
      this.setState({ error });
    });
  }

  render() {
    const { json, error } = this.state;
    if (error == null && json != null) {
      return (
        <table className="table table-striped w-50 table-bordered allow-center horizontal-center vertical-center">
          <thead className="font-weight-bold">
            <tr>
              <td>Application</td>
              <td>Download</td>
            </tr>
          </thead>
          <tbody>
            {tableGen(json.data)}
          </tbody>
        </table>
      );
    } if (json == null && error == null) {
      return <p style={{ height: 0, width: 300 }} className="text-center allow-center vertical-center horizontal-center">Loading application list</p>;
    }
    return (
      <p style={{ height: 0, width: 300 }} className="text-center allow-center vertical-center horizontal-center">{`Error: ${error.message}`}</p>
    );
  }
}
