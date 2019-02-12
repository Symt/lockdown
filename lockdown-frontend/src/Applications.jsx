import React, { Component } from 'react';
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
        <td><Event eventAction={`{download_file|/tmp/|${json[i].link}|${json[i].application_name}}`} eventTitle={json[i].application_name} /></td>
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
    };
  }

  componentDidMount() {
    fetch('http://99.169.62.62:5000/').then(response => response.json()).then((json) => {
      this.setState(Object.assign({}, { json }));
    });
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
