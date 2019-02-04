import React, {Component} from 'react';
import $ from 'jquery';
import data from "../dummy_api/data.json"

function tableGen(json) {
    var rows = [];
    for (var i = 0; i < json.available_downloads.length; i++) {
      rows.push(
        <tr key={i}><td>{json.available_downloads[i]["title"]}</td><td><a href={"download_file|/tmp/|"+json.available_downloads[i]['link']+"|"+json.available_downloads[i]['application_name']}>{json.available_downloads[i]['application_name']}</a></td></tr>
      )
    }
    return rows;
  }

export default class Applications extends Component {
    constructor(props) {
      super(props);
      this.state = {
        json: data
      };
    }
  
    render() {
      return (
  <table className="table table-striped w-50 table-bordered allow-center horizontal-center vertical-center">
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