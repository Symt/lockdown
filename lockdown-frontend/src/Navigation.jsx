/* eslint class-methods-use-this: 0 */

import React, { Component } from 'react';
import $ from 'jquery';

export default class Navigation extends Component {
  constructor(props) {
    super(props);
    this.clickHandler = this.clickHandler.bind(this);
  }

  shiftPage(page) {
    $('#home').addClass('hidden');
    $('#links').addClass('hidden');
    $('#applications').addClass('hidden');
    $('#commands').addClass('hidden');
    $('#home-anchor').removeClass('bg-primary');
    $('#links-anchor').removeClass('bg-primary');
    $('#applications-anchor').removeClass('bg-primary');
    $('#commands-anchor').removeClass('bg-primary');
    $('#home-anchor').addClass('bg-dark');
    $('#links-anchor').addClass('bg-dark');
    $('#applications-anchor').addClass('bg-dark');
    $('#commands-anchor').addClass('bg-dark');
    $(`#${page}`).removeClass('bg-dark');
    $(`#${page}`).addClass('bg-primary');
    $(`#${page.replace('-anchor', '')}`).removeClass('hidden');
  }

  clickHandler(e) {
    this.shiftPage(e.target.id);
  }

  /* eslint jsx-a11y/click-events-have-key-events: 0 */
  /* eslint jsx-a11y/no-static-element-interactions: 0 */
  /* eslint jsx-a11y/anchor-is-valid: 0 */
  render() {
    return (
      <div className="navbar navbar-dark bg-dark text-white text-center padding-remove">
        <a id="home-anchor" className="bg-primary makeshift-button w-25 padding-addition border-right" onClick={this.clickHandler}>Lockdown</a>
        <a id="links-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left border-right" onClick={this.clickHandler}>Links</a>
        <a id="applications-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left border-right" onClick={this.clickHandler}>Applications</a>
        <a id="commands-anchor" className="bg-dark makeshift-button w-25 padding-addition border-left" onClick={this.clickHandler}>Commands</a>
      </div>
    );
  }
}
